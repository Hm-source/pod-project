package org.example.podcommerce.controller.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.podcommerce.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;

//    @PostMapping("")
//    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateRequestDto request) {
//        UserResponseDto user = userService.save(request);
//        return ResponseEntity.ok(user);
//    }

}
