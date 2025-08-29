package org.example.podcommerce.service;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.podcommerce.repository.image.ProductImageRepository;
import org.example.podcommerce.repository.image.entity.Image;
import org.example.podcommerce.repository.image.entity.ProductImage;
import org.example.podcommerce.repository.product.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductImageService {

    ProductImageRepository productImageRepository;

    @Transactional
    public void linkImagesToProduct(Product product, List<Image> images) {
        List<ProductImage> productImages = new ArrayList<>();

        for (Image image : images) {
            ProductImage productImage = ProductImage.create(product, image);
            productImages.add(productImage);
        }
    }
}
