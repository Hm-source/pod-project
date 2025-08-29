package org.example.podcommerce.controller.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.podcommerce.repository.product.entity.ProductReviewHistory;
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductReviewHistoryResponse {

    private ReviewStatus previousStatus;

    private ReviewStatus currentStatus;

    private String reason;
    @JsonFormat(pattern = "yyyy-MM-dd HH시 mm분 ss초", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static ProductReviewHistoryResponse from(ProductReviewHistory entity) {
        return new ProductReviewHistoryResponse(
            entity.getPrevious_status(),
            entity.getCurrent_status(),
            entity.getReason(),
            entity.getCreated_at()
        );
    }
}
