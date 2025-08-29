package org.example.podcommerce.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.podcommerce.controller.product.dto.PageRequestDto;
import org.example.podcommerce.controller.product.dto.ProductCreateRequestDto;
import org.example.podcommerce.repository.baseproduct.entity.BaseProduct;
import org.example.podcommerce.repository.image.entity.Image;
import org.example.podcommerce.repository.image.entity.ProductImage;
import org.example.podcommerce.repository.product.ProductRepository;
import org.example.podcommerce.repository.product.ProductReviewHistoryRepository;
import org.example.podcommerce.repository.product.entity.Product;
import org.example.podcommerce.repository.product.entity.ProductReviewHistory;
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;
import org.example.podcommerce.repository.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductService {

    ProductReviewHistoryRepository productReviewHistoryRepository;
    ProductRepository productRepository;

    @Transactional
    public Product createProduct(User user, BaseProduct baseProduct,
        ProductCreateRequestDto requestDto, List<Image> images) {
        Product product = Product.convertTo(user, baseProduct, requestDto);
        for (Image image : images) {
            product.addProductImage(ProductImage.create(product, image));
        }
        return productRepository.save(product);
    }

    //(propagation = Propagation.REQUIRES_NEW)
    @Transactional
    public void saveProductReviewHistory(Product product, ReviewStatus status, String reason) {
        ProductReviewHistory initialHistory = ProductReviewHistory.create(product,
            product.getStatus(), status, reason);
        productReviewHistoryRepository.save(initialHistory);
    }

    @Transactional
    public List<Product> findByUserId(Integer id) {
        return productRepository.findByUserId(id);

    }


    @Transactional
    public Page<Product> findByStatus(ReviewStatus status, PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(
            pageRequestDto.getPage() - 1,
            pageRequestDto.getSize()
        );
        return productRepository.findByStatus(status, pageable);

    }

    @Transactional
    public Product findById(Integer id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("해당하는 상품이 없습니다."));

    }

}
