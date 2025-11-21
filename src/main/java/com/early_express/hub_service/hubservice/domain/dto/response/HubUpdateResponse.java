package com.early_express.hub_service.hubservice.domain.dto.response;

import com.early_express.hub_service.hubservice.domain.entity.HubEntity;
import com.early_express.hub_service.hubservice.domain.entity.HubStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "허브 수정 요청 응답")
public class HubUpdateResponse {
    Long id;
    String hubName;
    Long centralHubId;
    String address;
    double latitude;
    double longitude;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    HubStatus hubStatue;

    public static HubUpdateResponse of(HubEntity hub) {
        return HubUpdateResponse.builder()
                .hubName(hub.getHubName())
                .address(hub.getLocation().getAddress())
                .hubStatue(hub.getStatus())
                .centralHubId(hub.getCentralHubId())
                .latitude(hub.getLocation().getLatitude())
                .longitude(hub.getLocation().getLongitude())
                .build();
    }
}
