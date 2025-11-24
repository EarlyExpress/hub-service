package com.early_express.hub_service.hubservice.application.service.internal;

import com.early_express.hub_service.hubservice.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HubExistSelectService {

    private final HubRepository hubrepository;

    public boolean getExistHub(Long id) {
        return hubrepository.existsById(id);
    }
}
