package com.early_express.hub_service.hubservice.infrastructure.presentation.web.request;

public record HubRequest(
        String hubName,
        Long centralHubId,
        String address
) {

}
