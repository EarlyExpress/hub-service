package com.early_express.hub_service.hubservice.domain.dto.response;

import com.early_express.hub_service.hubservice.domain.entity.HubEntity;
import com.early_express.hub_service.hubservice.domain.entity.HubStatus;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record HubMasterSelectResponse  (
        Long id,
        String hubName,
        Long centralHubId,
        String address,
        double latitude,
        double longitude,
        HubStatus hubStatus,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy,
        boolean isDeleted


) implements Serializable {
    public static HubMasterSelectResponse of(HubEntity hub) {
        return HubMasterSelectResponse.builder()
                .id(hub.getHubId())
                .hubName(hub.getHubName())
                .centralHubId(hub.getCentralHubId())
                .address(hub.getLocation().getAddress())
                .centralHubId(hub.getCentralHubId())
                .latitude(hub.getLocation().getLatitude())
                .longitude(hub.getLocation().getLongitude())
                .hubStatus(hub.getStatus())
                .createdAt(hub.getCreatedAt())
                .createdBy(hub.getCreatedBy())
                .updatedAt(hub.getUpdatedAt())
                .updatedBy(hub.getUpdatedBy())
                .deletedAt(hub.getDeletedAt())
                .deletedBy(hub.getDeletedBy())
                .isDeleted(hub.isDeleted())
                .build();
    }
}
