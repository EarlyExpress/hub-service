package com.early_express.hub_service.hubservice.application.service.web.master;

import com.early_express.hub_service.hubservice.domain.entity.hub.HubAddressToCoords;
import com.early_express.hub_service.hubservice.domain.entity.hub.HubEntity;
import com.early_express.hub_service.hubservice.domain.exception.HubException;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import com.early_express.hub_service.hubservice.infrastructure.presentation.web.request.HubCreateRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.early_express.hub_service.global.presentation.exception.GlobalErrorCode.INTERNAL_SERVER_ERROR;
import static com.early_express.hub_service.global.presentation.exception.GlobalErrorCode.MISSING_PARAMETER;


@Service
@RequiredArgsConstructor
@Transactional
public class HubMasterCreateService {

    private final HubRepository hubrepository;
    private final HubAddressToCoords addressToCoords;

    //@CachePut(cacheNames = "hub", key = "#result")
    @CacheEvict(value = "hub", allEntries = true)
    public Long create(HubCreateRequest request) {
        if(request.hubName() == null || request.hubName().isEmpty() || request.address() == null || request.address().isEmpty()
//                ||   request.centralHubId() == null
        ){
            throw new HubException(MISSING_PARAMETER);
        }
        try{

            HubEntity hub = HubEntity.builder()
                    .hubName(request.hubName())
                    .address(request.address())
                    .centralHubId(request.centralHubId())
//                .hubRoleCheck(roleCheck)
                    .addressToCoords(addressToCoords)
                    .build();

            hubrepository.save(hub);
            return hub.getHubId();
        }catch ( HubException e){
            throw e;
        }catch (Exception e){
            throw new HubException(INTERNAL_SERVER_ERROR);

        }





    }
}
