package org.example.podcommerce.repository.image.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.podcommerce.repository.product.entity.Product;

@Entity
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    private LocalDateTime createdAt;

    public static ProductImage create(Product product, Image image) {
        return new ProductImage(
            null,
            product,
            image,
            LocalDateTime.now()
        );
    }
}
