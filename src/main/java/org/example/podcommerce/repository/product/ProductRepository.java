package org.example.podcommerce.repository.product;

import org.example.podcommerce.repository.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
