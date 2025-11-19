package com.early_express.hub_service.hubservice.application.service;

import com.early_express.hub_service.hubservice.domain.entity.HubAddressToCoords;
import com.early_express.hub_service.hubservice.domain.entity.HubEntity;
import com.early_express.hub_service.hubservice.domain.exception.HubErrorCode;
import com.early_express.hub_service.hubservice.domain.exception.HubException;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import com.early_express.hub_service.hubservice.infrastructure.presentatation.dto.HubRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class HubCreateService {

    private final HubRepository hubrepository;
    private final HubAddressToCoords addressToCoords;

    @Transactional
    public Long create(HubRequest request) {

        HubEntity hub = HubEntity.builder()
                .hubName(request.hubName())
                .address(request.address())
                .centralHubId(request.centralHubId())
//                .hubRoleCheck(roleCheck)
                .addressToCoords(addressToCoords)
                .build();

        hubrepository.save(hub);

        return hub.getHubId();
    }
}
