package com.early_express.hub_service.hubservice.domain.dto.route.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class RouteInfo {
    private List<HubSegment> segments;
    private List<HubInfo> hubs;
}
