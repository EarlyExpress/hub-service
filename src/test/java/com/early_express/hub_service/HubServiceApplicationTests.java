package com.early_express.hub_service;

import com.early_express.hub_service.hubservice.application.service.web.hub.master.HubMasterSelectService;
import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import com.early_express.hub_service.hubservice.infrastructure.presentation.web.request.HubCreateRequest;
import com.early_express.hub_service.hubservice.infrastructure.redis.repository.HubCacheRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import com.early_express.hub_service.hubservice.application.service.web.hub.master.HubMasterCreateService;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@DataRedisTest
class HubServiceApplicationTests {

    @Autowired
    private HubCacheRepository hubRepository;

    @Autowired
    private HubRepository repository;


    @Autowired
    private HubMasterSelectService selectService;

    @Autowired
    private HubMasterCreateService createService;

    @Autowired
    private CacheManager cacheManager;

	@Test
	void contextLoads() {
	}


    @Test
    @Transactional
    void createHub_success() {
        HubCreateRequest request = new HubCreateRequest("Hub1", 1L, "서울시 강남구");
        Long hubId = createService.create(request);

        assertNotNull(hubId);
        assertTrue(repository.findById(hubId).isPresent());
    }
    @Test
    void selectAll() {
        repository.findAll().forEach(hub -> System.out.println(hub));
    }


    @Test
    void selectOne() {
        System.out.println(hubRepository.findByHubName("first name"));
    }

//    @Test
//    void testGetAllHubs_returnsHubDtoPage() {
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("hubId").ascending());
//
//        PageResponse<HubDto> pageResponse = selectService.getAllHubs(pageable);
//
//        assertThat(pageResponse).isNotNull();
//        //assertThat(pageResponse.getContent()).isNotEmpty();
//
//        // 캐시 확인
////        Cache cache = cacheManager.getCache("hub");
////        assertThat(cache.get("0-5")).isNotNull();
//        Cache cache = cacheManager.getCache("hub");
//        assertThat(cache).isNotNull();
//
//        // 캐시에 저장된 값 가져오기
//        Cache.ValueWrapper cachedValue = cache.get("0-5");
//        assertThat(cachedValue).isNotNull();
//
//        Object value = cachedValue.get(); // 실제 캐시된 객체
//        System.out.println("캐시 내용: " + value);
//
//        // 타입 캐스팅 후 PageResponse<HubDto> 확인 가능
//        if (value instanceof PageResponse<?> cachedPage) {
//            System.out.println("캐시된 페이지 정보: " + cachedPage.getPageInfo().toString());
//            System.out.println("캐시된 컨텐츠 수: " + cachedPage.getContent().size());
//        }
//
//    }



}

