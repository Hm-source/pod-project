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
import org.example.podcommerce.controller.product.dto.ProductCreateRequestDto;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = {CascadeType.PERSIST,
        CascadeType.REMOVE}, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "product",
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
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

    public static Product convertTo(User user, BaseProduct baseProduct,
        ProductCreateRequestDto responseDto) {
        return new Product(
            null,
            user,
            baseProduct,
            responseDto.getName(),
            responseDto.getDescription(),
            ReviewStatus.REGISTERED,
            LocalDateTime.now(),
            LocalDateTime.now(),
            new ArrayList<>(),
            new ArrayList<>()
        );
    }

    public void updateReviewStatus(ReviewStatus newStatus) {

        ReviewStatus previousStatus = this.status;

        validateStatusChange(previousStatus, newStatus);
        this.status = newStatus;
    }

    private void validateStatusChange(ReviewStatus current, ReviewStatus target) {
        if (current.equals(target)) {
            throw new RuntimeException("같은 상태로 변경할 수 없습니다.");
        }
        switch (target) {
            case APPROVED:
            case REJECTED:
                if (current != ReviewStatus.REGISTERED) {
                    throw new RuntimeException("'" + current + "' 상태에서는 거절로 변경할 수 없습니다.");
                }
                break;

            case BANNED:
                if (current != ReviewStatus.APPROVED) {
                    throw new RuntimeException("'" + current + "' 상태에서는 금지로 변경할 수 없습니다.");
                }
                break;

            default:
                throw new RuntimeException("지원하지 않는 상태 변경입니다: " + target);
        }
    }

    public void addProductImage(ProductImage pi) {
        productImages.add(pi);
        pi.setProduct(this);
    }
}
