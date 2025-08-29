package org.example.podcommerce.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.podcommerce.repository.user.entity.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        String password = (String) token.getCredentials();

        User loggedUser = (User) userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, loggedUser.getPassword())) {
            throw new BadCredentialsException(loggedUser.getUsername() + " : Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(username, password,
            loggedUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) { // 적합한 provider 찾아서 진행
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
