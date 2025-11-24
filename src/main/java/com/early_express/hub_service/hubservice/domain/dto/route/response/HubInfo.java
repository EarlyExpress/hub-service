package com.early_express.hub_service.hubservice.domain.dto.route.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubInfo {
    private String hubId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
