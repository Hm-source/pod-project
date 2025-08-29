package org.example.podcommerce.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.podcommerce.controller.product.dto.PageRequestDto;
import org.example.podcommerce.controller.product.dto.ProductCreateRequestDto;
import org.example.podcommerce.controller.product.dto.ProductDetailResponseDto;
import org.example.podcommerce.controller.product.dto.ProductResponseDto;
import org.example.podcommerce.repository.baseproduct.entity.BaseProduct;
import org.example.podcommerce.repository.image.entity.Image;
import org.example.podcommerce.repository.product.entity.Product;
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;
import org.example.podcommerce.repository.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductFacade { // 애플리케이션 서비스


    ImageService imageService;
    UserService userService;
    BaseProductService baseProductService;
    ProductService productService;

    @Transactional
    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto,
        List<MultipartFile> imageFiles) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        BaseProduct baseProduct = baseProductService.findById(requestDto.getBaseProductId());
        List<Image> images = imageService.uploadAndSaveImages(imageFiles, user);
        Product product = productService.createProduct(user, baseProduct, requestDto, images);
        productService.saveProductReviewHistory(product, ReviewStatus.REGISTERED, "최초 등록");
        return ProductResponseDto.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDetailResponseDto> getMyProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        List<Product> products = productService.findByUserId(user.getId());
        return products.stream()
            .map(ProductDetailResponseDto::from)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Page<ProductResponseDto> getApprovedProducts(PageRequestDto pageRequestDto) {
        Page<Product> products = productService.findByStatus(ReviewStatus.APPROVED, pageRequestDto);
        return products.map(ProductResponseDto::from);
    }

    @Transactional
    public ProductDetailResponseDto reviewProduct(Integer productId, ReviewStatus newStatus,
        String reason) {
        Product product = productService.findById(productId);
        product.updateReviewStatus(newStatus);
        productService.saveProductReviewHistory(product, newStatus, reason);
        return ProductDetailResponseDto.from(product);
    }
}
