package com.early_express.hub_service.hubservice.infrastructure.presentation.inernal;

import com.early_express.hub_service.hubservice.application.service.internal.HubExistSelectService;
import com.early_express.hub_service.hubservice.infrastructure.presentation.dto.HubExistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hub/internal")
@RequiredArgsConstructor
public class HubExistController {

    private final HubExistSelectService existSelectService;

    @GetMapping("/validate/{id}")
    public ResponseEntity<HubExistResponse> getExistHub(@PathVariable Long id){
        boolean exists = existSelectService.getExistHub(id);
        return ResponseEntity.ok(HubExistResponse.of(id,exists));
    }
}
