package com.early_express.hub_service.hubservice.infrastructure.data;

import com.early_express.hub_service.hubservice.infrastructure.presentatation.dto.HubRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HubData {

    public static final List<HubRequest> CENTRAL_HUBS = List.of(
            new HubRequest("경기 남부 센터", null, "경기도 이천시 덕평로 257-21"),
            new HubRequest("대전광역시 센터", null, "대전 서구 둔산로 100"),
            new HubRequest("대구광역시 센터", null, "대구 북구 태평로 161")

    );

    public static final Map<String, List<String>> GENERAL_HUB_MAPPING = new HashMap<>() {{
        put("경기 남부 센터", List.of("경기 북부 센터", "서울특별시 센터", "인천광역시 센터", "강원특별자치도 센터"));
        put("대전광역시 센터", List.of("충청남도 센터", "충청북도 센터", "세종특별자치시 센터","전북특별자치도 센터","광주광역시 센터","전라남도 센터"));
        put("대구광역시 센터", List.of("경상북도 센터", "경상남도 센터", "부산광역시 센터", "울산광역시 센터"));

    }};

    public static final Map<String, String> GENERAL_HUB_ADDRESSES = new HashMap<>() {{


        put("서울특별시 센터", "서울특별시 송파구 송파대로 55");
        put("경기 북부 센터", "경기도 고양시 덕양구 권율대로 570");
        put("부산광역시 센터","부산 동구 중앙대로 206");
        put("인천광역시 센터", "인천 남동구 정각로 29");
        put("광주광역시 센터", "광주 서구 내방로 111");
        put("울산광역시 센터", "울산 남구 중앙로 201");
        put("세종특별자치시 센터", "세종특별자치시 한누리대로 2130");
        put("강원특별자치도 센터", "강원특별자치도 춘천시 중앙로 1");
        put("충청북도 센터", "충북 청주시 상당구 상당로 82");
        put("충청남도 센터", "충남 홍성군 홍북읍 충남대로 21");
        put("전북특별자치도 센터", "전북특별자치도 전주시 완산구 효자로 225");
        put("전라남도 센터", "전남 무안군 삼향읍 오룡길 1");
        put("경상북도 센터", "경북 안동시 풍천면 도청대로 455");
        put("경상남도 센터", "경남 창원시 의창구 중앙대로 300");
    }};
}
