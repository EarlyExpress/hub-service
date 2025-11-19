package com.early_express.hub_service.hubservice.domain.dto.reqeust;


import com.early_express.hub_service.hubservice.domain.entity.HubStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "허브 수정 요청")
public class HubUpdateRequest {
    String hubName;
    String address;
    HubStatus hubStatue;
}
