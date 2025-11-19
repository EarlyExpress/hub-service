package com.early_express.hub_service.hubservice.domain.entity;

import com.early_express.hub_service.global.infrastructure.entity.BaseEntity;
import com.early_express.hub_service.hubservice.domain.dto.HubDto;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long hubId;

    @Column(length=50, nullable = false)
    private String hubName;

    private Long centralHubId;

    @Embedded
    private HubLocation location;

    @Enumerated(EnumType.STRING)
    private HubStatus status;

    @Builder
    public HubEntity(Long hubId, Long centralHubId,String hubName, String address, HubAddressToCoords addressToCoords, HubRoleCheck hubRoleCheck) {
//        hubRoleCheck.masterCheck(); // 허브 등록 수정은 마스터 권한으로 한정

//        this.hubid = Objects.requireNonNullElse(hubid, HubId.of());
        if (hubId != null) this.hubId = hubId;
        if (centralHubId != null) this.centralHubId = centralHubId;

        this.hubName = hubName;
        this.status = HubStatus.ACTIVE;
//        setLocation(address, addressToCoords, hubRoleCheck); // 주소 -> 좌표 변환
        setLocation(address, addressToCoords); // 주소 -> 좌표 변환

    }



    // 허브 주소를 위도, 경도 좌표로 설정
    private void setLocation(String address, HubAddressToCoords addressToCoords) {

//    private void setLocation(String address, HubAddressToCoords addressToCoords, HubRoleCheck hubRoleCheck) {

        if (!StringUtils.hasText(address) || addressToCoords == null)

            return;

//        hubRoleCheck.masterCheck(); // 주소 등록, 수정은 마스터 권한으로 한정

        List<Double> coords = addressToCoords.convert(address);

        if (coords == null || coords.size() < 2) return;

        this.location = new HubLocation(address, Objects.requireNonNullElse(coords.get(0), 0.0), Objects.requireNonNullElse(coords.get(1), 0.0));
    }

//    public void changeLocation(String address, HubAddressToCoords addressToCoords, List<HubEntity> centralHubs) {
////        setLocation(address, addressToCoords, hubRoleCheck);
//
//        setLocation(address, addressToCoords);
//    }
    public void changeLocation(String address, HubAddressToCoords addressToCoords, List<HubEntity> centralHubs) {

        setLocation(address, addressToCoords);

        if (location == null || centralHubs == null || centralHubs.isEmpty()) return;

        // 가장 가까운 중앙 허브 계산
        HubEntity nearestCentralHub = centralHubs.stream()
                .min((h1, h2) -> Double.compare(
                        distance(location.getLatitude(), location.getLongitude(), h1.getLocation().getLatitude(), h1.getLocation().getLongitude()),
                        distance(location.getLatitude(), location.getLongitude(), h2.getLocation().getLatitude(), h2.getLocation().getLongitude())
                ))
                .orElse(null);


        // centralHubId 갱신
        this.centralHubId = nearestCentralHub.getHubId();
    }

    // 두 좌표 간 거리 계산 (간단히 유클리드 거리)
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = lat1 - lat2;
        double dLon = lon1 - lon2;
        return Math.sqrt(dLat * dLat + dLon * dLon);
    }



    public void changeHubName(String hubName) {
        this.hubName = hubName;
    }

    public void changeStatus(HubStatus hubStatue) {
        this.status = hubStatue;
    }

    public HubDto toDto() {
        return HubDto.builder()
                .id(hubId)
                .centralHubId(centralHubId)
                .hubName(hubName)
                .address(location.getAddress())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .createdAt(getCreatedAt())
                .modifiedAt(getUpdatedAt())
                .hubStatue(status)
                .build();
    }

}
