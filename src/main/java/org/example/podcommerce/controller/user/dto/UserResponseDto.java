package org.example.podcommerce.controller.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.podcommerce.repository.user.entity.User;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {

    private Integer id;
    private String username;
    private String name;
    private String phone;

    public static UserResponseDto from(User entity) {
        return new UserResponseDto(
            entity.getId(),
            entity.getUsername(),
            entity.getName(),
            entity.getPhone()
        );
    }
}
