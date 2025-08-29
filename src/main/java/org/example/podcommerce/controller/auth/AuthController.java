package org.example.podcommerce.controller.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.podcommerce.controller.user.dto.UserCreateRequestDto;
import org.example.podcommerce.controller.user.dto.UserResponseDto;
import org.example.podcommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입", description = "사용자 회원가입 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")  // POST /api/auth/signup
    public ResponseEntity<UserResponseDto> signup(
        @Valid @RequestBody UserCreateRequestDto request) {
        UserResponseDto response = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
