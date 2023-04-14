package com.example.eshop.security.config;

import com.example.eshop.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .requestMatchers("/comment/*/*/*", "/comment/retrieveNumberOfComments/**",
                        "/categoryFields/*", "/category/**", "/product_bought/existsByProductId/**",
                        "/registration", "/user/**", "/product_pictures",
                        "/product_pictures/*", "/consumer/**",
                        "/product_field_values/**",
                        "/product",
                        "/product/*",
                        "/product/exists/**",
                        "/product/retrieveProductsNotBought/**",
                        "/product/retrieveNumberOfProductsNotBought/**",
                        "/product/retrieveNumberOfProductsBought/**",
                        "/product/retrieveNumberOfProductsUserBought/**",
                        "/product/retrieveProductsBought/**",
                        "/product/retrieveProductsUserBought/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);

        return provider;
    }
}
