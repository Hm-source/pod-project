package org.example.podcommerce.controller.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.podcommerce.repository.user.entity.User;

@Schema(description = "사용자 정보")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {

    @Schema(description = "사용자 ID", example = "1")
    private Integer id;
    @Schema(description = "사용자명", example = "testUser")
    private String username;
    @Schema(description = "이름", example = "user")
    private String name;
    @Schema(description = "전화번호", example = "01012345678")
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
