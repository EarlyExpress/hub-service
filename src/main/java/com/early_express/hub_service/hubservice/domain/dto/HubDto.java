package com.early_express.hub_service.hubservice.domain.dto;

import com.early_express.hub_service.hubservice.domain.entity.HubStatus;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record HubDto(
        Long id,
        String hubName,
        Long centralHubId,
        String address,
        double latitude,
        double longitude,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        HubStatus hubStatue
) implements Serializable {}
