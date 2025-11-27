package com.early_express.hub_service.hubservice.domain.repository;

import com.early_express.hub_service.hubservice.domain.entity.route.HubRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRouteRepository extends JpaRepository<HubRouteEntity, Long> {

}
