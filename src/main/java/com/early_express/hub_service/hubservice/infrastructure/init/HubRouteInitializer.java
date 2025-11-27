package com.early_express.hub_service.hubservice.infrastructure.init;

import com.early_express.hub_service.hubservice.domain.entity.hub.HubEntity;
import com.early_express.hub_service.hubservice.domain.entity.route.HubRouteEntity;
import com.early_express.hub_service.hubservice.domain.entity.route.DistanceCalculator;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import com.early_express.hub_service.hubservice.domain.repository.HubRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class HubRouteInitializer implements CommandLineRunner {

    private final HubRepository hubRepository;
    private final HubRouteRepository hubRouteRepository;

    @Override
    public void run(String... args) throws Exception {
        // 이미 초기화되어 있으면 종료
        if (hubRouteRepository.count() > 0) return;

        // 허브 조회
        List<HubEntity> hubs = hubRepository.findAll();
        Map<Long, HubEntity> hubMap = new HashMap<>();
        for (HubEntity hub : hubs) hubMap.put(hub.getHubId(), hub);

        // 허브 연결 설정
        Map<Long, List<Long>> connections = new HashMap<>();
        connections.put(1L, List.of(14L, 15L, 16L, 17L,10L,2L,3L));
        connections.put(2L, List.of(4L, 5L, 6L, 7L, 8L, 9L, 1L, 3L));
        connections.put(3L, List.of(10L, 11L, 12L, 13L,1L, 2L));
        connections.put(4L, List.of(2L));
        connections.put(5L, List.of(2L));
        connections.put(6L, List.of(2L));
        connections.put(7L, List.of(2L));
        connections.put(8L, List.of(2L));
        connections.put(9L, List.of(2L));
        connections.put(10L, List.of(1L, 3L));
        connections.put(11L, List.of(3L));
        connections.put(12L, List.of(3L));
        connections.put(13L, List.of(3L));
        connections.put(14L, List.of(1L));
        connections.put(15L, List.of(1L));
        connections.put(16L, List.of(1L));
        connections.put(17L, List.of(1L));

        List<HubRouteEntity> routes = new ArrayList<>();

        for (Map.Entry<Long, List<Long>> entry : connections.entrySet()) {
            Long fromId = entry.getKey();
            HubEntity fromHub = hubMap.get(fromId);

            for (Long toId : entry.getValue()) {
                HubEntity toHub = hubMap.get(toId);
                double distance = DistanceCalculator.haversine(
                        fromHub.getLocation().getCoords(),
                        toHub.getLocation().getCoords()

                );

                // 양방향 모두 추가
                routes.add(HubRouteEntity.builder()
                        .from(fromHub)
                        .to(toHub)
                        .distanceKm(distance)
                        .build());

                routes.add(HubRouteEntity.builder()
                        .from(toHub)
                        .to(fromHub)
                        .distanceKm(distance)
                        .build());
            }
        }

        hubRouteRepository.saveAll(routes);
        System.out.println("허브 연결 및 거리 정보 초기화 완료! 총 " + routes.size() + "개 경로 생성됨.");
    }
}
