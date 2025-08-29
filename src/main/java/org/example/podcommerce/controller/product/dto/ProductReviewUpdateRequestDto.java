package org.example.podcommerce.controller.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;

@Schema(description = "상품 상태 변경 요청")
@Getter
@AllArgsConstructor
public class ProductReviewUpdateRequestDto {

    @Schema(description = "상품 상태", example = "APPROVED", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "변경할 상태는 필수입니다")
    private ReviewStatus status;
    @Schema(description = "상태 변경 이유", example = "승인되지 않는 사진이 포함되어 있습니다.")
    private String reason;

}
