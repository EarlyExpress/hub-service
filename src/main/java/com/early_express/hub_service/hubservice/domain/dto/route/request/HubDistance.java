package com.early_express.hub_service.hubservice.domain.dto.route.request;

/**
 * @param distance 시작점 → 현재 허브까지 누적 거리
 */
public record HubDistance(Long hubId, double distance) {

}
