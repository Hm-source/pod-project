package org.example.podcommerce.repository.product.entity.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReviewStatus {
    REGISTERED,
    APPROVED,
    REJECTED,
    BANNED;
}
