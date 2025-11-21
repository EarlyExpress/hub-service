package com.early_express.hub_service.hubservice.domain.dto.response;

import com.early_express.hub_service.hubservice.domain.entity.HubEntity;
import com.early_express.hub_service.hubservice.domain.entity.HubStatus;
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
