package org.example.podcommerce.controller.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductCreateRequestDto {

    private Integer baseProductId;
    @NotBlank(message = "상품명은 필수입니다")
    @Size(max = 20, message = "상품명은 20자 이하여야 합니다")
    private String name;
    private String description;

}
