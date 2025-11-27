package com.early_express.hub_service.hubservice.infrastructure.presentation.web.controller.master;


import com.early_express.hub_service.hubservice.application.service.web.hub.master.HubMasterCreateService;
import com.early_express.hub_service.hubservice.application.service.web.hub.master.HubMasterDeleteService;
import com.early_express.hub_service.hubservice.application.service.web.hub.master.HubMasterSelectService;
import com.early_express.hub_service.hubservice.application.service.web.hub.master.HubMasterUpdateService;
import com.early_express.hub_service.hubservice.domain.dto.hub.reqeust.HubUpdateRequest;
import com.early_express.hub_service.hubservice.domain.dto.hub.response.HubMasterSelectResponse;
import com.early_express.hub_service.hubservice.domain.dto.hub.response.HubUpdateResponse;
import com.early_express.hub_service.hubservice.infrastructure.presentation.web.request.HubCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("v1/hub/web/master")
@RequestMapping("/web/master")
@RequiredArgsConstructor
public class HubMasterController {

    private final HubMasterCreateService createService;
    private final HubMasterUpdateService updateService;
    private final HubMasterDeleteService deleteService;
    private final HubMasterSelectService selectService;

    @PostMapping("/hubs")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createHub(@RequestBody HubCreateRequest request) {
//        HubDto hub = createService.create(request);
//        return HubId.of(hub.id());
        return createService.create(request);
    }

    @PatchMapping("/hubs/{id}")
    public ResponseEntity<HubUpdateResponse> updateHub(@PathVariable Long id, @RequestBody HubUpdateRequest request) {
        HubUpdateResponse hubResponse = updateService.update(id,request);

        // return ApiResponse.
        return ResponseEntity.ok(hubResponse);
    }

    @DeleteMapping("/hubs/{id}")
    public ResponseEntity<?> deleteHub(@PathVariable Long id) {
       deleteService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hubs")
    public ResponseEntity<List<HubMasterSelectResponse>> getAdminHubs(){
        List<HubMasterSelectResponse> hubs = selectService.getAllHubs();
        return ResponseEntity.ok(hubs);
    }

    @GetMapping("/hubs/{id}")
    public ResponseEntity<HubMasterSelectResponse> getAdminHubs(@PathVariable Long id){
        HubMasterSelectResponse hubMasterSelectResponse = selectService.getOneHubs(id);
        return ResponseEntity.ok(hubMasterSelectResponse);
    }

    // ===== 전체 조회 페이징 처리 적용 ver
//    public ResponseEntity<PageResponse> getAllHubs(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "hubId") String sortBy
//    ) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
//        PageResponse response = selectService.getAllHubs(pageable);
//        return ResponseEntity.ok(response);
//    }
}

