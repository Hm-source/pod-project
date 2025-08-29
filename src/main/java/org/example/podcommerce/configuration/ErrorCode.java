package org.example.podcommerce.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C-001", "내부 서버 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C-002", "유효하지 않은 입력 값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C-003", "허용되지 않은 HTTP 메서드입니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C-004", "입력 값의 타입이 유효하지 않습니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C-005", "해당 엔티티를 찾을 수 없습니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "U-002", "이미 존재하는 사용자 이름입니다."),

    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P-001", "상품을 찾을 수 없습니다."),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "P-002", "재고가 부족합니다."),

    // BaseProduct
    BASE_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "BP-001", "베이스 상품을 찾을 수 없습니다."),

    // Image
    IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "I-001", "이미지 업로드에 실패했습니다");

    
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
