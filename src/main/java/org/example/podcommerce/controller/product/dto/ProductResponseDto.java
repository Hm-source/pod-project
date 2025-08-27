package org.example.podcommerce.controller.product.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.podcommerce.repository.image.entity.ProductImage;
import org.example.podcommerce.repository.product.entity.Product;
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponseDto {

    private Integer id;

    private String name;

    private String description;

    private Integer baseProductId;

    private String baseProductName;

    private Integer basePrice;

    private ReviewStatus status;

    private List<ProductImage> images;

    private LocalDateTime createdAt;

    public static ProductResponseDto from(Product entity) {
        return new ProductResponseDto(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getBaseProduct().getId(),
            entity.getBaseProduct().getName(),
            entity.getBaseProduct().getPrice(),
            entity.getStatus(),
            entity.getProductImages(),
            entity.getCreatedAt()
        );
    }

}
