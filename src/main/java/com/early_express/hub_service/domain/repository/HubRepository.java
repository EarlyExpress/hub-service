package com.early_express.hub_service.domain.repository;

import com.early_express.hub_service.domain.entity.HubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HubRepository extends JpaRepository<HubEntity, UUID> {
}
