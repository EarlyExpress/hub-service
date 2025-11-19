package com.early_express.hub_service.hubservice.application.service;

import com.early_express.hub_service.hubservice.domain.dto.reqeust.HubUpdateRequest;
import com.early_express.hub_service.hubservice.domain.dto.response.HubResponse;
import com.early_express.hub_service.hubservice.domain.entity.HubAddressToCoords;
import com.early_express.hub_service.hubservice.domain.entity.HubEntity;
import com.early_express.hub_service.hubservice.domain.exception.HubErrorCode;
import com.early_express.hub_service.hubservice.domain.exception.HubException;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class HubUpdateService {

    private final HubRepository hubrepository;
    private final HubAddressToCoords addressToCoords;

    public HubResponse update(Long hubId, HubUpdateRequest request) {


        HubEntity hub = hubrepository.findById(hubId)
                .orElseThrow(() -> new HubException(HubErrorCode.HUB_NOT_FOUND));



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

        return HubResponse.of(hub);
    }
}
