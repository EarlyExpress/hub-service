package com.early_express.hub_service.hubservice.infrastructure.presentation.web.controller.route;

import com.early_express.hub_service.hubservice.application.service.web.route.HubRouteCalculatorService;
import com.early_express.hub_service.hubservice.domain.dto.route.request.HubRouteCalculationRequest;
import com.early_express.hub_service.hubservice.domain.dto.route.response.HubRouteCalculationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/hub/internal/route")
@RequiredArgsConstructor
public class HubRouteController {

    private final HubRouteCalculatorService routeCalculator;

    @PostMapping("/calculate")
    public ResponseEntity<HubRouteCalculationResponse> testRoute(@RequestBody HubRouteCalculationRequest request) {
//        HubRouteCalculationRequest req = new HubRouteCalculationRequest(
//                "ORD-123456",
//                13L,
//                "서울특별시 송파구 올림픽로 124",
//                "111동 123호"
//        );

        HubRouteCalculationResponse response = routeCalculator.calculate(request);
        return ResponseEntity.ok(response);
    }
}

