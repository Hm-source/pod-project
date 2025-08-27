package org.example.podcommerce.repository.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.podcommerce.repository.image.entity.Image;
import org.example.podcommerce.repository.product.entity.Product;

@Entity
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String name;
    private String password;
    private String phone;
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public static User create(String username, String password, String name, String phone) {
        return new User(
            null,
            username,
            name,
            password,
            phone,
            LocalDateTime.now(),
            new ArrayList<>(),
            new ArrayList<>()
        );
    }
}
