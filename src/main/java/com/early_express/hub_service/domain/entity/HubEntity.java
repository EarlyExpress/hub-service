package com.early_express.hub_service.domain.entity;

import com.early_express.hub_service.global.common.utils.UuidUtils;
import com.early_express.hub_service.global.infrastructure.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Table(name = "hub")
@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubEntity extends BaseEntity {

    @EmbeddedId
    private HubId hubid;

    private String hubName;

    @Embedded
    private HubLocation location;

    @Builder
    public HubEntity(HubId id, String hubName, String address, HubAddressToCoords addressToCoords, HubRoleCheck hubRoleCheck) {
        hubRoleCheck.masterCheck(); // 허브 등록 수정은 마스터 권한으로 한정

        this.hubid = Objects.requireNonNullElse(id, HubId.of());
        this.hubName = hubName;
        setLocation(address, addressToCoords, hubRoleCheck); // 주소 -> 좌표 변환
    }

    // 허브 주소를 위도, 경도 좌표로 설정
    private void setLocation(String address, HubAddressToCoords addressToCoords, HubRoleCheck hubRoleCheck) {
        if (!StringUtils.hasText(address) || addressToCoords == null) return;

        hubRoleCheck.masterCheck(); // 주소 등록, 수정은 마스터 권한으로 한정

        List<Double> coords = addressToCoords.convert(address);
        if (coords == null || coords.size() < 2) return;

        this.location = new HubLocation(address, Objects.requireNonNullElse(coords.get(0), 0.0), Objects.requireNonNullElse(coords.get(1), 0.0));
    }

    public void changeLocation(String address, HubAddressToCoords addressToCoords, HubRoleCheck hubRoleCheck) {
        setLocation(address, addressToCoords, hubRoleCheck);
    }
}
