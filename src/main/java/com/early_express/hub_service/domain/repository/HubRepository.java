package com.early_express.hub_service.infrastructure.presentation;

import com.early_express.hub_service.domain.entity.HubEntity;
import com.early_express.hub_service.domain.entity.HubId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HubRepository extends JpaRepository<HubEntity, UUID> {
}
