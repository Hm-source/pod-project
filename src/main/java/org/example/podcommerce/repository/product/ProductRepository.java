package org.example.podcommerce.repository.product;

import java.util.List;
import org.example.podcommerce.repository.product.entity.Product;
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByUserId(Integer userId);

    Page<Product> findByStatus(ReviewStatus status, Pageable pageable);
}
