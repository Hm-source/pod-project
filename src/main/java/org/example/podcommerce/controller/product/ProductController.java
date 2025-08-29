package org.example.podcommerce.controller.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.podcommerce.controller.product.dto.PageRequestDto;
import org.example.podcommerce.controller.product.dto.ProductCreateRequestDto;
import org.example.podcommerce.controller.product.dto.ProductDetailResponseDto;
import org.example.podcommerce.controller.product.dto.ProductResponseDto;
import org.example.podcommerce.controller.product.dto.ProductReviewUpdateRequestDto;
import org.example.podcommerce.service.ProductFacade;
import org.example.podcommerce.validator.ValidFile;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductController {

    ProductFacade productFacade;

    @GetMapping("")
    public ResponseEntity<List<ProductResponseDto>> getProducts(
        @Valid @ModelAttribute PageRequestDto pageRequestDto) {
        Page<ProductResponseDto> products = productFacade.getApprovedProducts(pageRequestDto);
        return ResponseEntity.ok(products.getContent());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/create")
    public ResponseEntity<ProductResponseDto> createProduct(
        @Valid @ModelAttribute ProductCreateRequestDto requestDto,
        @Valid @ValidFile @Size(min = 1, max = 5, message = "이미지는 1개이상 5개 이하여야 합니다.")
        @RequestParam("images") List<MultipartFile> images) {

        ProductResponseDto responseDto = productFacade.createProduct(requestDto, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProductDetailResponseDto>> getMyProducts() {
        List<ProductDetailResponseDto> productDtos = productFacade.getMyProducts();
        return ResponseEntity.ok(productDtos);
    }


    @Secured("ROLE_ADMIN")
    @PutMapping("/{productId}/review")
    public ResponseEntity<ProductDetailResponseDto> reviewProduct(
        @PathVariable Integer productId,
        @RequestBody ProductReviewUpdateRequestDto requestDto) {
        ProductDetailResponseDto response = productFacade.reviewProduct(productId,
            requestDto.getStatus(), requestDto.getReason());
        return ResponseEntity.ok(response);
    }

}
