package com.early_express.hub_service.hubservice.infrastructure.presentation.web.controller.all;

import com.early_express.hub_service.hubservice.application.service.web.all.HubAllSelectService;
import com.early_express.hub_service.hubservice.domain.dto.hub.response.HubAllResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/hub/web/all")
@RequiredArgsConstructor
public class HubAllController
{
    private final HubAllSelectService selectService;

    @GetMapping("/hubs")
    public ResponseEntity<List<HubAllResponse>> getAllHubs(){
        List<HubAllResponse> hubs = selectService.getAllHubs();
        return ResponseEntity.ok(hubs);
    }

    @GetMapping("/hubs/{id}")
    public ResponseEntity<HubAllResponse> getOneAllHubs(@PathVariable Long id){
        HubAllResponse hubAllResponse = selectService.getOneHubs(id);
        return ResponseEntity.ok(hubAllResponse);
    }
}
