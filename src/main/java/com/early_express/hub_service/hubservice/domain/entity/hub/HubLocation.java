package com.early_express.hub_service.hubservice.domain.entity.hub;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubLocation {
    @Column(length=150, nullable = false)
    private String address; // 주소
    private double latitude; // 위도
    private double longitude; // 경도

    @Builder
    protected HubLocation(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * 위도/경도를 리스트로 반환
     */
    public List<Double> getCoords() {
        return List.of(this.latitude,this.longitude);
    }
}