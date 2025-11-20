package com.early_express.hub_service.hubservice.infrastructure.api;

import com.early_express.hub_service.hubservice.domain.entity.HubAddressToCoords;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HubAddressApi implements HubAddressToCoords {
    private final ObjectMapper objectMapper;
    @Value("${KAKAO_MAP_KEY}")
    private String apiKey;

    public List<Double> convert(String address) {


        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(url)
                .header("Authorization", "KakaoAK " + apiKey)
                .retrieve()
                .toEntity(String.class);


        if(response.getStatusCode().is2xxSuccessful()){
            try{
                JsonNode node = objectMapper.readTree(response.getBody());
                JsonNode documents = node.path("documents");
                if (documents.isArray() && documents.size() > 0) {
                    JsonNode addressNode = documents.get(0).path("address");
                    double x = addressNode.path("x").asDouble(0.0);
                    double y = addressNode.path("y").asDouble(0.0);
                    return List.of(x,y);
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }


        return List.of(0.0,0.0);

    }





}
