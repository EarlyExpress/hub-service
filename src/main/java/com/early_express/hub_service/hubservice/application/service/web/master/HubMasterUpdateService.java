package com.early_express.hub_service.hubservice.application.service.web.master;

import com.early_express.hub_service.hubservice.domain.dto.reqeust.HubUpdateRequest;
import com.early_express.hub_service.hubservice.domain.dto.response.HubUpdateResponse;
import com.early_express.hub_service.hubservice.domain.entity.HubAddressToCoords;
import com.early_express.hub_service.hubservice.domain.entity.HubEntity;
import com.early_express.hub_service.hubservice.domain.exception.HubErrorCode;
import com.early_express.hub_service.hubservice.domain.exception.HubException;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.early_express.hub_service.global.presentation.exception.GlobalErrorCode.INTERNAL_SERVER_ERROR;


@Service
@RequiredArgsConstructor
@Transactional
public class HubMasterUpdateService {

    private final HubRepository hubrepository;
    private final HubAddressToCoords addressToCoords;

    //@CachePut(cacheNames = "hub", key = "#hubId")
    @CacheEvict(value = "hub", allEntries = true)
    public HubUpdateResponse update(Long hubId, HubUpdateRequest request) {


        HubEntity hub = hubrepository.findById(hubId)
                .orElseThrow(() -> new HubException(HubErrorCode.HUB_NOT_FOUND));

        if(hub.isDeleted()){
            throw new HubException(HubErrorCode.HUB_ALREADY_DELETED);
        }
        try{
            // 주소 변경일 경우 위도,경도, centralHubId 같이 변환
            if(request.getAddress() != null){
                List<HubEntity> centralHubs = hubrepository.findByCentralHubIdIsNull();

                hub.changeLocation(request.getAddress(), addressToCoords, centralHubs);
            }

            if(request.getHubName() != null){
                hub.changeHubName(request.getHubName());
            }

            if(request.getHubStatue() != null){
                hub.changeStatus(request.getHubStatue());
            }

            return HubUpdateResponse.of(hub);
        }catch ( HubException e){
            throw e;
        }catch (Exception e){
            throw new HubException(INTERNAL_SERVER_ERROR);

        }


    }
}
