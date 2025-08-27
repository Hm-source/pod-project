package org.example.podcommerce.controller.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.podcommerce.repository.user.entity.User;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSimpleResponseDto {

    private Integer id;
    private String username;

    public static UserSimpleResponseDto from(User entity) {
        return new UserSimpleResponseDto(
            entity.getId(),
            entity.getUsername()
        );
    }
}
