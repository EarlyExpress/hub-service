package com.early_express.hub_service.hubservice.application.service.web.all;


import com.early_express.hub_service.hubservice.domain.dto.response.HubAllResponse;
import com.early_express.hub_service.hubservice.domain.entity.HubEntity;
import com.early_express.hub_service.hubservice.domain.exception.HubErrorCode;
import com.early_express.hub_service.hubservice.domain.exception.HubException;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HubAllSelectService {

    private final HubRepository hubRepository;
    private final HubRepository hubrepository;

    @Cacheable(cacheNames = "hub", key = "'allHubAllResponse'", cacheManager = "cacheManager")
    public List<HubAllResponse> getAllHubs() {
        List<HubEntity> hubs = hubRepository.findByIsDeletedIsFalse();
        return hubs.stream()
                .map(HubAllResponse::of)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "hub", key = "'oneHubAllResponse'", cacheManager = "cacheManager")
    public HubAllResponse getOneHubs(Long id) {
        HubEntity hub = hubrepository.findById(id)
                .orElseThrow(() -> new HubException(HubErrorCode.HUB_NOT_FOUND));

        if(hub.isDeleted()){
            throw new HubException(HubErrorCode.HUB_NOT_FOUND);
        }
        return HubAllResponse.of(hub);

    }
}
