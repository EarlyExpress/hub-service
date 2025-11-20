package com.early_express.hub_service.hubservice.domain.exception;


import com.early_express.hub_service.global.presentation.exception.ErrorCode;
import com.early_express.hub_service.global.presentation.exception.GlobalException;

/**
 * Hub 도메인 전용 예외
 * GlobalException을 상속하여 일관된 예외 처리
 */
public class HubException extends GlobalException{

    /**
     * ErrorCode만으로 예외 생성
     *
     * @param errorCode Inventory 에러 코드
     */
    public HubException(HubErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * ErrorCode와 커스텀 메시지로 예외 생성
     *
     * @param errorCode Inventory 에러 코드
     * @param message   커스텀 에러 메시지
     */
    public HubException(HubErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * ErrorCode와 원인 예외로 예외 생성
     *
     * @param errorCode Inventory 에러 코드
     * @param cause     원인 예외
     */
    public HubException(HubErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * ErrorCode, 커스텀 메시지, 원인 예외로 예외 생성
     *
     * @param errorCode Inventory 에러 코드
     * @param message   커스텀 에러 메시지
     * @param cause     원인 예외
     */
    public HubException(HubErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    /**
     * ErrorCode를 받아서 예외 생성 (다른 ErrorCode도 사용 가능)
     * 외부 서비스 에러 등을 래핑할 때 사용
     *
     * @param errorCode 임의의 ErrorCode
     */
    public HubException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * ErrorCode와 커스텀 메시지로 예외 생성 (다른 ErrorCode도 사용 가능)
     *
     * @param errorCode 임의의 ErrorCode
     * @param message   커스텀 에러 메시지
     */
    public HubException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

