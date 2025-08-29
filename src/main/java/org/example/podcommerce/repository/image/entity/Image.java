package org.example.podcommerce.repository.image.entity;

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
import org.example.podcommerce.repository.user.entity.User;

@Entity
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "image",
        orphanRemoval = true,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE
        })
    private List<ProductImage> productImages;

    private LocalDateTime createdAt;

    public static Image create(User user, String url, String name) {
        return new Image(
            null,
            url,
            name,
            user,
            new ArrayList<>(),
            LocalDateTime.now()
        );
    }
}
