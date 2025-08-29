package org.example.podcommerce.repository.baseproduct.entity;

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
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_product_id")
    private BaseProduct baseProduct;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;

    private LocalDateTime createdAt;

    public static Options create(BaseProduct baseProduct, OptionGroup optionGroup, String name) {
        Options option = new Options(
            null,
            name,
            baseProduct,
            optionGroup,
            LocalDateTime.now()
        );
        optionGroup.addOption(option);
        return option;
    }

}
