package com.early_express.hub_service.hubservice.infrastructure.presentatation.dto;

public record HubRequest(
        String hubName,
        Long centralHubId,
        String address
) {

}
