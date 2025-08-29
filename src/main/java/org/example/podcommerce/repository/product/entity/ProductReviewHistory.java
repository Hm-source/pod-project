package org.example.podcommerce.repository.product.entity;

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
import org.example.podcommerce.repository.product.entity.vo.ReviewStatus;

@Entity
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class ProductReviewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private ReviewStatus previous_status;
    private ReviewStatus current_status;
    private String reason;
    private LocalDateTime created_at;

    public static ProductReviewHistory create(Product product, ReviewStatus status,
        ReviewStatus newStatus, String reason) {
        return new ProductReviewHistory(
            null,
            product,
            status,
            newStatus,
            reason,
            LocalDateTime.now()
        );
    }
}
