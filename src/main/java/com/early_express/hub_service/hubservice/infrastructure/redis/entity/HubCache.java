package com.early_express.hub_service.hubservice.infrastructure.redis.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("hub") // hash 기반의 key-value를 구성하여 데이터 저장
@Builder
@Getter
@ToString
public class HubCache {
    @Id
    private Long hubId;
    @Indexed
    private String hubName;
    private Long centralHubId;
    private String hubStatus;
    private String address;
    private Double latitude;
    private Double longitude;
}
