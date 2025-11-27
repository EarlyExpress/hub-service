package com.early_express.hub_service.hubservice.application.service.web.route;

import com.early_express.hub_service.hubservice.domain.dto.route.request.HubDistance;
import com.early_express.hub_service.hubservice.domain.dto.route.request.HubEdge;
import com.early_express.hub_service.hubservice.domain.entity.hub.HubEntity;
import com.early_express.hub_service.hubservice.domain.entity.route.HubRouteEntity;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import com.early_express.hub_service.hubservice.domain.repository.HubRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class DijkstraPathFinder {

    private final HubRepository hubRepository;
    private final HubRouteRepository hubRouteRepository;

    /**
     * 허브 → 허브 최단 경로
     * @param originHubId 출발 허브
     * @param destHubId   도착 허브
     */
    public List<Long> findOptimalRoute(Long originHubId, Long destHubId) {

        // 1) 허브 그래프 구성
        Map<Long, List<HubEdge>> graph = buildGraph();
        if (graph.isEmpty())
            throw new IllegalStateException("허브 연결 정보가 없습니다.");

        // origin / dest 둘 다 그래프에 존재하는지 확인
        if (!graph.containsKey(originHubId) || !graph.containsKey(destHubId)) {
            throw new IllegalStateException("허브 그래프에 출발 또는 도착 허브가 없습니다.");
        }

        // 2) 다익스트라 초기화
        Map<Long, Double> dist = new HashMap<>();
        Map<Long, Long> prev = new HashMap<>();
        PriorityQueue<HubDistance> pq =
                new PriorityQueue<>(Comparator.comparingDouble(HubDistance::distance));

        for (Long hubId : graph.keySet()) {
            dist.put(hubId, Double.MAX_VALUE);
        }

        dist.put(originHubId, 0.0);
        pq.add(new HubDistance(originHubId, 0.0));

        // 3) 다익스트라 수행
        while (!pq.isEmpty()) {
            HubDistance cur = pq.poll();
            Long nowId = cur.hubId();

            // 이미 더 짧은 경로가 있으면 스킵
            if (cur.distance() > dist.get(nowId)) continue;

            for (HubEdge edge : graph.getOrDefault(nowId, List.of())) {
                double nd = dist.get(nowId) + edge.getDistance();
                Double toDist = dist.get(edge.getToHubId());
                if (toDist == null) continue; // 그래프에 없는 허브면 스킵

                if (nd < toDist) {
                    dist.put(edge.getToHubId(), nd);
                    prev.put(edge.getToHubId(), nowId);
                    pq.add(new HubDistance(edge.getToHubId(), nd));
                }
            }
        }

        // 4) 도착 허브까지 경로가 없으면 실패
        if (dist.get(destHubId) == null || dist.get(destHubId) == Double.MAX_VALUE) {
            throw new IllegalStateException("허브 간 경로를 찾을 수 없습니다. (" +
                    originHubId + " -> " + destHubId + ")");
        }

        // 5) 역추적하여 경로 구성
        List<Long> path = new ArrayList<>();
        for (Long at = destHubId; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path); // [origin, ..., dest]

        return path;
    }

    /** 허브 그래프 구성: HubRouteEntity 기반 */
    private Map<Long, List<HubEdge>> buildGraph() {
        Map<Long, List<HubEdge>> map = new HashMap<>();

        List<HubEntity> hubs = hubRepository.findAll();
        for (HubEntity hub : hubs) {
            map.put(hub.getHubId(), new ArrayList<>());
        }

        List<HubRouteEntity> routes = hubRouteRepository.findAll();
        for (HubRouteEntity route : routes) {
            Long fromId = route.getFrom().getHubId();
            Long toId = route.getTo().getHubId();

            // 단방향 그래프 (양방향으로 넣고 싶으면 initializer에서 from/to 둘 다 insert)
            map.computeIfAbsent(fromId, k -> new ArrayList<>())
                    .add(new HubEdge(toId, route.getDistanceKm()));
        }

        return map;
    }
}
