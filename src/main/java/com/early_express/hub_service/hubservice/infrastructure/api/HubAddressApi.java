package com.early_express.hub_service.hubservice.infrastructure.api;

import com.early_express.hub_service.hubservice.domain.entity.hub.HubAddressToCoords;
import com.early_express.hub_service.hubservice.domain.exception.HubException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

import static com.early_express.hub_service.global.presentation.exception.GlobalErrorCode.*;
import static com.early_express.hub_service.hubservice.domain.exception.HubErrorCode.*;

@Component
@RequiredArgsConstructor
public class HubAddressApi implements HubAddressToCoords {

    private final ObjectMapper objectMapper;

    @Value("${KAKAO_MAP_KEY}")
    private String apiKey;

    @Override
    public List<Double> convert(String address) {
        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

        try {
            ResponseEntity<String> response = RestClient.create()
                    .get()
                    .uri(url)
                    .header("Authorization", "KakaoAK " + apiKey)
                    .retrieve()
                    .toEntity(String.class);
            HttpStatusCode status = response.getStatusCode();

            if (status.is2xxSuccessful()) {

                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode documents = root.path("documents");

                if (!documents.isArray() || documents.isEmpty()) {
                    throw new HubException(HUB_RESPONSE_EMPTY);
                }

                JsonNode addressNode = documents.get(0).path("address");

                double x = addressNode.path("x").asDouble(Double.NaN);
                double y = addressNode.path("y").asDouble(Double.NaN);

                if (Double.isNaN(x) || Double.isNaN(y)) {
                    throw new HubException(HUB_ADDRESS_INVALID);
                }

                return List.of(x, y);
            }
            throw new HubException(EXTERNAL_API_ERROR);

        }  catch (Exception e) {
            e.printStackTrace();
            throw new HubException(INTERNAL_SERVER_ERROR);
        }
    }
}
