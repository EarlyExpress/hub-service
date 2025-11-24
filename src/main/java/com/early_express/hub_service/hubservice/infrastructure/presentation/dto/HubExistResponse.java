package com.early_express.hub_service.hubservice.infrastructure.presentation.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record HubExistResponse(boolean exists, Long hubId) implements Serializable
{
    public static HubExistResponse of(Long id, boolean exists) {
        return HubExistResponse.builder()
                .hubId(id)
                .exists(exists)
                .build();

    }
}

