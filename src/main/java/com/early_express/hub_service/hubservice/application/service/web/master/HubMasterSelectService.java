package com.early_express.hub_service.hubservice.application.service.web.master;



import com.early_express.hub_service.hubservice.domain.dto.response.HubMasterSelectResponse;
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
public class HubMasterSelectService {

    private final HubRepository hubRepository;

    // === Redis+페이징 처리 version
//    @Cacheable(value = "hub", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
//    public PageResponse<HubDto> getAllHubs(Pageable pageable) {
//        Page<HubEntity> hubPage = hubRepository.findAll(pageable);
//        PageResponse<HubDto> response= PageUtils.toPageResponse(hubPage, HubDto::of);
//        return response;
//    }
    @Cacheable(cacheNames = "hub", key = "'allHubMasterResponse'", cacheManager = "cacheManager")
    public List<HubMasterSelectResponse> getAllHubs() {
        List<HubEntity> hubs = hubRepository.findAll();
        return hubs.stream()
                .map(HubMasterSelectResponse::of)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "hub", key = "'oneHubMasterResponse'", cacheManager = "cacheManager")
    public HubMasterSelectResponse getOneHubs(Long id) {
        HubEntity hub = hubRepository.findById(id)
                .orElseThrow(() -> new HubException(HubErrorCode.HUB_NOT_FOUND));

        return HubMasterSelectResponse.of(hub);

    }




}
