package com.early_express.hub_service.hubservice.domain.entity.route;


import com.early_express.hub_service.hubservice.domain.entity.hub.HubEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubRouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_hub_id")
    private HubEntity from;

    @ManyToOne
    @JoinColumn(name = "to_hub_id")
    private HubEntity to;

    private double distanceKm;

    @Builder
    public HubRouteEntity(HubEntity from, HubEntity to, double distanceKm) {
        this.from = from;
        this.to = to;
        this.distanceKm = distanceKm;
    }

}
