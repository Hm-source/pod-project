package org.example.podcommerce.controller.product.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageRequestDto {

    @Min(value = 1, message = "페이지는 1 이상이어야 합니다")
    private int page = 1;
    
    private int size = 10;

}
