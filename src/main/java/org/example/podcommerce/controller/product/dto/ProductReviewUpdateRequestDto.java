package org.example.podcommerce.controller.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;

@Getter
@AllArgsConstructor
public class ProductReviewUpdateRequestDto {

    @NotNull(message = "변경할 상태는 필수입니다")
    private ReviewStatus status;

    private String reason;

}
