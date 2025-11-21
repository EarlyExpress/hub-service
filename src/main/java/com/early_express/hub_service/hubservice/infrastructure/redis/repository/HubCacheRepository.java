package com.early_express.hub_service.hubservice.infrastructure.redis.repository;

import com.early_express.hub_service.hubservice.infrastructure.redis.entity.HubCache;
import org.springframework.data.repository.CrudRepository;

public interface HubCacheRepository  extends CrudRepository<HubCache,Long> {

    HubCache findByHubName(String name);
}
