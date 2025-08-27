package org.example.podcommerce.repository.product;

import org.example.podcommerce.repository.product.entity.ProductReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewHistoryRepository extends
    JpaRepository<ProductReviewHistory, Integer> {

}
