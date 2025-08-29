package org.example.podcommerce.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "상품 관리", description = "상품 생성, 조회, 상태 수정 API")
@RestController
@RequestMapping("/api/products")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductController {

    ProductFacade productFacade;

    @Operation(
        summary = "상품 목록 조회",
        description = "등록된 모든 상품의 목록을 페이지네이션으로 조회합니다."
    )
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

    @Operation(
        summary = "내 상품 리스트 조회",
        description = "내가 등록한 상품들을 조회합니다."
    )
    @GetMapping("/my")
    public ResponseEntity<List<ProductDetailResponseDto>> getMyProducts() {
        List<ProductDetailResponseDto> productDtos = productFacade.getMyProducts();
        return ResponseEntity.ok(productDtos);
    }

    @Operation(
        summary = "상품 상태 변경 (승인, 등록, 금지, 거절)",
        description = "상품의 이미지들을 리뷰하여 상품 상태를 변경합니다."
    )
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
