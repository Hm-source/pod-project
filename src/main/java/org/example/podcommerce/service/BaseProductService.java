package org.example.podcommerce.service;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.podcommerce.repository.baseproduct.BaseProductRepository;
import org.example.podcommerce.repository.baseproduct.entity.BaseProduct;
import org.example.podcommerce.repository.baseproduct.entity.OptionGroup;
import org.example.podcommerce.repository.baseproduct.entity.Options;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BaseProductService {

    BaseProductRepository baseProductRepository;

    @PostConstruct
    @Transactional
    public void init() {
        // 1. BaseProduct: 머그컵
        BaseProduct mug = BaseProduct.create("머그컵", 10000);

        OptionGroup materialGroup = OptionGroup.create(mug, "재질");
        Options ceramic = Options.create(mug, materialGroup, "세라믹");
        Options stainless = Options.create(mug, materialGroup, "스테인리스");

        OptionGroup colorGroup = OptionGroup.create(mug, "컬러");
        Options white = Options.create(mug, colorGroup, "화이트");
        Options black = Options.create(mug, colorGroup, "블랙");
        Options red = Options.create(mug, colorGroup, "레드");

        baseProductRepository.save(mug);
    }

    public BaseProduct findById(Integer id) {
        return baseProductRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("해당 상품은 존재하지 않습니다."));
    }

}
