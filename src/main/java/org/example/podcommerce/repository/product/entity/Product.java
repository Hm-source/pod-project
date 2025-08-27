package org.example.podcommerce.repository.product.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.podcommerce.repository.baseproduct.entity.BaseProduct;
import org.example.podcommerce.repository.image.entity.ProductImage;
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;
import org.example.podcommerce.repository.user.entity.User;

@Entity
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_product_id")
    private BaseProduct baseProduct;

    private String name;
    private String description;
    private ReviewStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductReviewHistory> reviewHistories = new ArrayList<>();

    public static Product create(User user, BaseProduct baseProduct, String name,
        String description) {
        return new Product(
            null,
            user,
            baseProduct,
            name,
            description,
            ReviewStatus.REGISTERED,
            LocalDateTime.now(),
            LocalDateTime.now(),
            new ArrayList<>(),
            new ArrayList<>()
        );
    }
}
