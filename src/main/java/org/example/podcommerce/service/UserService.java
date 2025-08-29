package org.example.podcommerce.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.podcommerce.controller.user.dto.UserCreateRequestDto;
import org.example.podcommerce.controller.user.dto.UserResponseDto;
import org.example.podcommerce.repository.user.UserRepository;
import org.example.podcommerce.repository.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserResponseDto findById(Integer id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("유저가 데이터베이스 내 존재하지 않습니다. 유저 id : " + id));
        return UserResponseDto.from(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다 - username : " + username));
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
            .stream()
            .map(UserResponseDto::from)
            .toList();
    }

    public UserResponseDto save(UserCreateRequestDto request) {

        String encoded = passwordEncoder.encode(request.getPassword());
        User user = User.create(
            request.getUsername(),
            encoded,
            request.getName(),
            request.getPhone()
        );
        User created = userRepository.save(user);
        return UserResponseDto.from(created);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다 - username : " + username));

    }
}
