package org.example.podcommerce.controller.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductCreateRequestDto {

    //    @Size(min = 1, max = 5)
//    @NotEmpty(message = "이미지는 최소 1장 이상 업로드해야 합니다")
//    @ValidFile
//    private List<MultipartFile> images;
    private Integer baseProductId;
    @NotBlank(message = "상품명은 필수입니다")
    @Size(max = 20, message = "상품명은 20자 이하여야 합니다")
    private String name;
    private String description;

}
