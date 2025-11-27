package com.early_express.hub_service.hubservice.domain.dto.route.response;

import lombok.*;

@Getter
@Builder
public class HubSegment {
    private String fromHubId;
    private String toHubId;
    private double distance;
}