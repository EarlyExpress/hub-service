package com.early_express.hub_service.hubservice.domain.exception;

import com.early_express.hub_service.global.presentation.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum HubErrorCode implements ErrorCode {
    HUB_NOT_FOUND("HUB_001","허브 아이디를 찾을 수 없습니다.",404),
    HUB_ALREADY_EXISTS("HUB_002", "허브가 이미 존재합니다.", 409),
    HUB_ALREADY_DELETED("HUB_003", "이미 삭제된 허브입니다.",410),
    HUB_DB_ERROR("HUB_004","DB 저장에 실패하였습니다.",500),

    // 요청 실패 관련 (클라이언트 요청 문제)
    HUB_ADDRESS_INVALID("HUB_005", "주소 변환에 실패하였습니다.", 400),
    HUB_REQUEST_TIMEOUT("HUB_006", "외부 API 요청이 시간 내에 완료되지 않았습니다.", 408),
    HUB_REQUEST_INVALID("HUB_007", "외부 API 요청이 잘못되었습니다.", 400),
    // 응답 실패 관련 (서버 문제)
    HUB_RESPONSE_EMPTY("HUB_009", "외부 API 응답이 비어있습니다.", 204);


    private final String code;
    private final String message;
    private final int status;
}
