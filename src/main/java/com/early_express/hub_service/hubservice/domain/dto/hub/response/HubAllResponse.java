package com.early_express.hub_service.hubservice.domain.dto.hub.response;

import com.early_express.hub_service.hubservice.domain.entity.hub.HubEntity;
import com.early_express.hub_service.hubservice.domain.entity.hub.HubStatus;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record HubAllResponse(
        Long id,
        String hubName,
        Long centralHubId,
        String address,
        double latitude,
        double longitude,
        HubStatus hubStatus

)
        implements Serializable
{
    public static HubAllResponse of(HubEntity hub) {
        return HubAllResponse.builder()
                .id(hub.getHubId())
                .hubName(hub.getHubName())
                .centralHubId(hub.getCentralHubId())
                .address(hub.getLocation().getAddress())
                .centralHubId(hub.getCentralHubId())
                .latitude(hub.getLocation().getLatitude())
                .longitude(hub.getLocation().getLongitude())
                .hubStatus(hub.getStatus())
                .build();
    }
}
