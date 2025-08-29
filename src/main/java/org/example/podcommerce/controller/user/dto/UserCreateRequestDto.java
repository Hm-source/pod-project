package org.example.podcommerce.controller.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자 등록 요청")
@AllArgsConstructor
@Getter
public class UserCreateRequestDto {

    @Schema(description = "사용자 아이디", example = "testUser", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "아이디는 필수입니다")
    @Size(min = 5, max = 10, message = "아이디는 5자 이상 10자 이하여야 합니다")
    private String username;
    @Schema(description = "사용자 이름", example = "user", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이름은 필수입니다")
    private String name;
    @Schema(description = "비밀번호", example = "Aaaa1234", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "비밀번호는 필수입니다")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$",
        message = "비밀번호는 8자 이상이며, 대문자와 소문자 각각 1개 이상 포함해야 합니다")
    private String password;
    @Schema(description = "전화번호는", example = "01012345678", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "전화번호는은 필수입니다")
    private String phone;

}
