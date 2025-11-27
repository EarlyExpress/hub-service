package com.early_express.hub_service;

import com.early_express.hub_service.hubservice.application.service.web.hub.master.HubMasterCreateService;
import com.early_express.hub_service.hubservice.domain.entity.hub.HubEntity;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import com.early_express.hub_service.hubservice.infrastructure.data.HubData;
import com.early_express.hub_service.hubservice.infrastructure.presentation.web.request.HubCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class HubServiceApplication {

    @Autowired
    private HubMasterCreateService hubMasterCreateService;

    @Autowired
    private HubRepository hubRepository;

    public static void main(String[] args) {
        SpringApplication.run(HubServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner insertHubs() {
        return args -> {

            if (hubRepository.count() > 0) return;

            System.out.println("*".repeat(50) + " 중앙 허브 등록 시작 " + "*".repeat(50));

            HubData.CENTRAL_HUBS.forEach(hubMasterCreateService::create);

            System.out.println("*".repeat(50) + " 중앙 허브 등록 완료 " + "*".repeat(50));

            Map<String, HubEntity> hubMap = new HashMap<>();
            hubRepository.findAll().forEach(h -> hubMap.put(h.getHubName(), h));

            System.out.println("*".repeat(50) + " 일반 허브 등록 시작 " + "*".repeat(50));

            for (Map.Entry<String, List<String>> entry : HubData.GENERAL_HUB_MAPPING.entrySet()) {
                HubEntity centralHub = hubMap.get(entry.getKey());

                if (centralHub == null) continue;

                Long centralHubId = centralHub.getHubId();



                for (String generalHubName : entry.getValue()) {

                    String address = HubData.GENERAL_HUB_ADDRESSES.get(generalHubName);

                    if (address == null) {
                        address = "주소 미기입";
                    }

                    HubCreateRequest request = new HubCreateRequest(generalHubName, centralHubId, address);
                    hubMasterCreateService.create(request);

                    System.out.println("등록: " + generalHubName + " (중앙 허브: " + entry.getKey() + ")");
                }
            }

            System.out.println("*".repeat(50) + " 일반 허브 등록 완료 " + "*".repeat(50));
        };
    }
}
