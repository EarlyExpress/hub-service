package com.early_express.hub_service.hubservice.domain.exception;

import com.early_express.hub_service.global.presentation.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum HubErrorCode implements ErrorCode {
    HUB_NOT_FOUND("HUB_001","허브 아이디를 찾을 수 없습니다.",404),
    HUB_ALREADY_EXISTS("HUB_002", "허브가 이미 존재합니다.", 409);

    private final String code;
    private final String message;
    private final int status;
}
