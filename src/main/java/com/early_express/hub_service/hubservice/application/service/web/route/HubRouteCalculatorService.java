package com.early_express.hub_service.hubservice.application.service.web.route;

import com.early_express.hub_service.hubservice.domain.dto.route.request.HubRouteCalculationRequest;
import com.early_express.hub_service.hubservice.domain.dto.route.response.HubRouteCalculationResponse;
import com.early_express.hub_service.hubservice.domain.dto.route.response.HubSegment;
import com.early_express.hub_service.hubservice.domain.entity.hub.HubEntity;
import com.early_express.hub_service.hubservice.domain.entity.route.DistanceCalculator;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import com.early_express.hub_service.hubservice.infrastructure.api.HubAddressApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HubRouteCalculatorService {

    private final DijkstraPathFinder pathFinder;
    private final HubRepository hubRepository;
    private final HubAddressApi hubAddressApi;

    private static final double MAX_DIRECT_DISTANCE_KM = 10.0;

    public HubRouteCalculationResponse calculate(HubRouteCalculationRequest req) {

        // 1) 출발 허브
        HubEntity originHub = hubRepository.findById(req.getOriginHubId())
                .orElseThrow(() -> new IllegalStateException("출발 허브를 찾을 수 없습니다. id=" + req.getOriginHubId()));

        // 2) 목적지 주소 → 위도/경도
        List<Double> destinationCoords = hubAddressApi.convert(req.getDestinationAddress());

        // 3) 출발 허브 → 목적지 직송 가능(10km 이내) 여부
        double distanceToDest = DistanceCalculator.haversine(
                originHub.getLocation().getCoords(),
                destinationCoords
        );

        if (distanceToDest <= MAX_DIRECT_DISTANCE_KM) {
            // 허브 경유 없이 바로 배송
            return HubRouteCalculationResponse.directDelivery(
                    req.getOrderId(),
                    originHub,
                    distanceToDest
            );
        }

        // 4) 목적지에서 가장 가까운 허브 = 도착 허브 선택 (여기가 "서울 허브"가 됨)
        List<HubEntity> allHubs = hubRepository.findAll();
        HubEntity destHub = null;
        double best = Double.MAX_VALUE;

        for (HubEntity hub : allHubs) {
            double d = DistanceCalculator.haversine(
                    hub.getLocation().getCoords(),
                    destinationCoords
            );
            if (d < best) {
                best = d;
                destHub = hub;
            }
        }

        if (destHub == null) {
            throw new IllegalStateException("도착 허브를 찾을 수 없습니다.");
        }

        // 5) 허브 → 허브 최단 경로 계산 (출발 허브 → 도착 허브)
        List<Long> routeHubIds =
                pathFinder.findOptimalRoute(originHub.getHubId(), destHub.getHubId());

        // 6) 허브 엔티티 리스트 & 구간(segment) 구성
        List<HubEntity> routeHubs = new ArrayList<>();
        for (Long hubId : routeHubIds) {
            HubEntity hub = hubRepository.findById(hubId)
                    .orElseThrow(() -> new IllegalStateException("허브를 찾을 수 없습니다. id=" + hubId));
            routeHubs.add(hub);
        }

        List<HubSegment> segments = new ArrayList<>();
        double totalDistance = 0.0;

        // 허브 간 구간 거리
        for (int i = 0; i < routeHubs.size() - 1; i++) {

            HubEntity fromHub = routeHubs.get(i);
            HubEntity toHub = routeHubs.get(i + 1);



            double dist = DistanceCalculator.haversine(
                    fromHub.getLocation().getCoords(),
                    toHub.getLocation().getCoords()
            );
            totalDistance += dist;


            segments.add(HubSegment.builder()
                    .fromHubId(String.valueOf(fromHub.getHubId()))
                    .toHubId(String.valueOf(toHub.getHubId()))
                    .distance(dist)
                    .build()
            );
        }

        // 마지막: 도착 허브 → 목적지 주소 구간
//        double lastSegmentDistance = DistanceCalculator.haversine(
//                destHub.getLocation().getCoords(),
//                destinationCoords
//        );
//        totalDistance += lastSegmentDistance;

//        segments.add(HubSegment.builder()
//                .fromHubId(String.valueOf(destHub.getHubId()))
//                .toHubId(req.getDestinationAddress())
//                .distance(totalDistance)
//                .build()
//        );

        // 7) 응답 생성 (routeHubs 마지막이 destHub 이고,
        return HubRouteCalculationResponse.withHubs(
                routeHubs,
                req.getOrderId(),
                segments,
                totalDistance
        );
    }
}
