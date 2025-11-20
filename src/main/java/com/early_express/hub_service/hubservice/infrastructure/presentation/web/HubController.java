package com.early_express.hub_service.hubservice.infrastructure.presentation.web;


import com.early_express.hub_service.hubservice.application.service.HubCreateService;
import com.early_express.hub_service.hubservice.application.service.HubDeleteService;
import com.early_express.hub_service.hubservice.application.service.HubUpdateService;
import com.early_express.hub_service.hubservice.domain.dto.reqeust.HubUpdateRequest;
import com.early_express.hub_service.hubservice.domain.dto.response.HubResponse;
import com.early_express.hub_service.hubservice.infrastructure.presentation.web.request.HubRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/hub/web/master")
@RequiredArgsConstructor
public class HubController {

    private final HubCreateService createService;
    private final HubUpdateService updateService;
    private final HubDeleteService deleteService;

    @PostMapping("hubs")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createHub(@RequestBody HubRequest request) {
//        HubDto hub = createService.create(request);
//        return HubId.of(hub.id());
        return createService.create(request);
    }

    @PatchMapping("hubs/{id}")
    public ResponseEntity<HubResponse> updateHub(@PathVariable Long id, @RequestBody HubUpdateRequest request) {
        HubResponse hubResponse = updateService.update(id,request);

        // return ApiResponse.
        return ResponseEntity.ok(hubResponse);
    }

    @DeleteMapping("hubs/{id}")
    public ResponseEntity<?> deleteHub(@PathVariable Long id) {
       deleteService.delete(id);

        //삭제한 사람, 삭제상태(isDelete) 변경
        return ResponseEntity.noContent().build();
    }

}

