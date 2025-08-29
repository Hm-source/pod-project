package org.example.podcommerce.controller.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.podcommerce.repository.product.entity.Product;
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDetailResponseDto {

    private Integer id;

    private String name;

    private String description;

    private Integer baseProductId;

    private String baseProductName;

    private Integer basePrice;

    private ReviewStatus status;

    private List<SimpleImageResponseDto> images;

    private List<ProductReviewHistoryResponse> history;
    @JsonFormat(pattern = "yyyy-MM-dd HH시 mm분 ss초", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static ProductDetailResponseDto from(Product entity) {
        return new ProductDetailResponseDto(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getBaseProduct().getId(),
            entity.getBaseProduct().getName(),
            entity.getBaseProduct().getPrice(),
            entity.getStatus(),
            entity.getProductImages().stream()
                .map(SimpleImageResponseDto::from)
                .toList(),
            entity.getReviewHistories().stream().map(ProductReviewHistoryResponse::from).toList(),
            entity.getCreatedAt()
        );
    }
}
