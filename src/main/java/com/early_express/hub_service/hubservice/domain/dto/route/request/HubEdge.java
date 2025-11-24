package com.early_express.hub_service.hubservice.domain.entity.route;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubEdge {
    private  Long toHubId;
    private  double distance;

    public HubEdge(Long toHubId, double distance) {
        this.toHubId = toHubId;
        this.distance = distance;
    }

}

