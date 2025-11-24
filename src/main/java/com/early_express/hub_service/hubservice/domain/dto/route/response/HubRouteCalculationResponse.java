package com.early_express.hub_service.hubservice.domain.dto.route.response;


import com.early_express.hub_service.hubservice.domain.entity.hub.HubEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class HubRouteCalculationResponse {

    private String orderId;
    private String originHubId;
    private String destinationHubId;
    private List<String> routeHubs;
    private boolean requiresHubDelivery;
    private double estimatedDistance;
    private String routeInfoJson;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /** 직송 */
    public static HubRouteCalculationResponse directDelivery(String orderId, HubEntity hub, double distance) {
        List<HubInfo> hubs = List.of(toHubInfo(hub));
        RouteInfo routeInfo = RouteInfo.builder()
                .segments(new ArrayList<>())
                .hubs(hubs)
                .build();

        return HubRouteCalculationResponse.builder()
                .orderId(orderId)
                .originHubId(String.valueOf(hub.getHubId()))
                .destinationHubId(String.valueOf(hub.getHubId()))
                .routeHubs(List.of(String.valueOf(hub.getHubId())))
                .requiresHubDelivery(false)
                .estimatedDistance(distance)
                .routeInfoJson(toJson(routeInfo))
                .build();
    }

    /** 경유 허브 있는 경우 */
    public static HubRouteCalculationResponse withHubs(
            List<HubEntity> hubs,
            String orderId,
            List<HubSegment> segments,
            double totalDistance
    ) {

        List<String> hubIds = hubs.stream().map(h -> String.valueOf(h.getHubId())).toList();
        RouteInfo routeInfo = RouteInfo.builder()
                .segments(segments)
                .hubs(hubs.stream().map(HubRouteCalculationResponse::toHubInfo).toList())
                .build();

        return HubRouteCalculationResponse.builder()
                .orderId(orderId)
                .originHubId(hubIds.get(0))
                .destinationHubId(hubIds.get(hubIds.size() - 1))
                .routeHubs(hubIds)
                .requiresHubDelivery(hubIds.size() > 1)
                .estimatedDistance(totalDistance)
                .routeInfoJson(toJson(routeInfo))
                .build();
    }

    /** HubEntity → HubInfo 변환 */
    private static HubInfo toHubInfo(HubEntity hub) {
        return HubInfo.builder()
                .hubId(String.valueOf(hub.getHubId()))
                .name(hub.getHubName())
                .address(hub.getLocation().getAddress())
                .latitude(hub.getLocation().getLatitude())
                .longitude(hub.getLocation().getLongitude())
                .build();
    }

    /** RouteInfo → JSON 문자열 */
    private static String toJson(RouteInfo routeInfo) {
        try {
            return objectMapper.writeValueAsString(routeInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize route info", e);
        }
    }
}
