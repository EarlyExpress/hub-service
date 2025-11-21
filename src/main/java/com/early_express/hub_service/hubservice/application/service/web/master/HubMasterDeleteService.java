package com.early_express.hub_service.hubservice.application.service.web.master;


import com.early_express.hub_service.hubservice.domain.entity.HubEntity;
import com.early_express.hub_service.hubservice.domain.exception.HubErrorCode;
import com.early_express.hub_service.hubservice.domain.exception.HubException;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import static com.early_express.hub_service.global.presentation.exception.GlobalErrorCode.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Transactional
public class HubMasterDeleteService {

    private final HubRepository hubrepository;

   @CacheEvict(cacheNames = "hub",allEntries = true)
    public void delete(Long id) {

        HubEntity hub = hubrepository.findById(id)
                .orElseThrow(() -> new HubException(HubErrorCode.HUB_NOT_FOUND));

        try{
            if(hub.isDeleted()){
                throw new HubException(HubErrorCode.HUB_ALREADY_DELETED);
            }

            hubrepository.delete(hub);
        }catch ( HubException e){
            throw e;
        }catch (Exception e){
            throw new HubException(INTERNAL_SERVER_ERROR);

        }



    }
}
