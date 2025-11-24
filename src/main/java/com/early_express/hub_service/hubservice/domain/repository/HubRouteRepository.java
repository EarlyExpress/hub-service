package com.early_express.hub_service.hubservice.domain.repository;

import com.early_express.hub_service.hubservice.domain.entity.route.HubRouteEntity;
import com.early_express.hub_service.hubservice.infrastructure.presentation.inernal.HubRouterController;
import io.micrometer.core.instrument.config.validate.Validated;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HubRouteRepository extends JpaRepository<HubRouteEntity, Long> {

}
