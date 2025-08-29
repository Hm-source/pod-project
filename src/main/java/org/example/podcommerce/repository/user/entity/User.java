package org.example.podcommerce.repository.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.podcommerce.repository.image.entity.Image;
import org.example.podcommerce.repository.product.entity.Product;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
@Table(
    name = "user",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_username", columnNames = "username")
    }
)
public class User implements UserDetails {

    public final static SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority(
        "ROLE_USER");
    public final static SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority(
        "ROLE_ADMIN");
    public final static List<SimpleGrantedAuthority> SIMPLE_ROLES = List.of(ROLE_USER);
    public final static List<SimpleGrantedAuthority> ADMIN_ROLES = List.of(ROLE_USER, ROLE_ADMIN);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
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

    @Transient // 직렬화 대상에서 제외하고 싶은 필드에 사용하는 애너테이션
    private List<SimpleGrantedAuthority> authorities = ADMIN_ROLES;

    public static User create(String username, String password, String name, String phone) {
        return new User(
            null,
            username,
            name,
            password,
            phone,
            LocalDateTime.now(),
            new ArrayList<>(),
            new ArrayList<>(),
            ADMIN_ROLES
        );
    }
}
