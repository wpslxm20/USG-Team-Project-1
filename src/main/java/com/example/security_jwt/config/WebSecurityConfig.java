package com.example.security_jwt.config;

import com.example.security_jwt.global.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin().disable();
        http.httpBasic().disable();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors() // cors 커스텀 설정
                .and()

                .sessionManagement()//세션 사용 x
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll());

        return http.build();
    }

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors(AbstractHttpConfigurer::disable) // cors 커스텀 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))//세션 사용 x
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/api/customer/mypage/modify").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/login", "api/signup", "api/resissue").permitAll()
                        .requestMatchers("/api/login","/api/resissue","api/signup").permitAll()
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"));
        http
                .userDetailsService(customUserDetailsService);
        return http.build();
    }*/
}
