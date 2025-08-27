package org.example.podcommerce.repository.baseproduct.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class OptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_product_id")
    private BaseProduct baseProduct;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "optionGroup", cascade = CascadeType.REMOVE)
    private List<Option> options = new ArrayList<>();

    public static OptionGroup create(BaseProduct baseProduct, String name) {
        return new OptionGroup(
            null,
            name,
            baseProduct,
            new ArrayList<>()
        );
    }
}
