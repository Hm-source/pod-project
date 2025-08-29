package org.example.podcommerce.controller.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "상품 등록 요청")
@AllArgsConstructor
@Getter
public class ProductCreateRequestDto {

    @Schema(description = "베이스 상품 아이디", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "베이스 상품 id는 필수입니다")
    private Integer baseProductId;
    @Schema(description = "상품명", example = "나만의 머그컵", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "상품명은 필수입니다")
    @Size(max = 20, message = "상품명은 20자 이하여야 합니다")
    private String name;
    @Schema(description = "설명", example = "당신의 추억을 머그컵에 담아보세요.")
    private String description;

}
