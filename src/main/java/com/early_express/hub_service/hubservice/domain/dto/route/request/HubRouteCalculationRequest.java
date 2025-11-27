package com.early_express.hub_service.hubservice.domain.dto.route.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HubRouteCalculationRequest {
    private String orderId;
    private Long originHubId;
    private String destinationAddress;
    private String destinationAddressDetail;

}
