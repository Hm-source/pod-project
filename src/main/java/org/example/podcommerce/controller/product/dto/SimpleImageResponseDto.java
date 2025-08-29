package org.example.podcommerce.controller.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.podcommerce.repository.image.entity.ProductImage;

@AllArgsConstructor
@Getter
public class SimpleImageResponseDto {

    private String url;
    private String name;

    public static SimpleImageResponseDto from(ProductImage entity) {
        return new SimpleImageResponseDto(
            entity.getImage().getUrl(),
            entity.getImage().getName()
        );
    }
}
