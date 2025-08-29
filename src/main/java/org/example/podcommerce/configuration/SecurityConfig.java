package org.example.podcommerce.configuration;

import lombok.RequiredArgsConstructor;
import org.example.podcommerce.security.UsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsernamePasswordAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf
//            .ignoringRequestMatchers("/api/**") // 특정 엔드포인트는 CSRF 미적용
//        );
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(form -> form
            .loginPage("/login")
            .permitAll());
        http.logout(logout -> logout
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/login"));
        // basic authentication 사용해서 authorization 기본으로 보내도록
        // http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/signup", "/api/products").permitAll()
            .requestMatchers("/", "/api/**").authenticated()
            .anyRequest().permitAll()
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }
}
