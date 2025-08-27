package org.example.podcommerce.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCreateRequestDto {

    @NotBlank(message = "아이디는 필수입니다")
    @Size(min = 5, max = 10, message = "아이디는 5자 이상 10자 이하여야 합니다")
    private String username;
    private String name;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$",
        message = "비밀번호는 8자 이상이며, 대문자와 소문자 각각 1개 이상 포함해야 합니다")
    private String password;
    private String phone;

}
