package org.example.podcommerce.repository.baseproduct.entity;

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

@Entity
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class BaseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;
    private LocalDateTime createdAt;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "baseProduct", cascade = CascadeType.REMOVE)
    private List<OptionGroup> optionGroups = new ArrayList<>();
}