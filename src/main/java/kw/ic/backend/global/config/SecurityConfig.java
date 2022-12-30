package kw.ic.backend.global.config;

import kw.ic.backend.global.auth.filter.JwtAuthenticationFilter;
import kw.ic.backend.global.auth.handler.JwtAccessDeniedHandler;
import kw.ic.backend.global.auth.handler.JwtAuthenticationEntryPoint;
import kw.ic.backend.global.auth.util.JwtProvider;
import kw.ic.backend.global.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v2/api-docs/**","/v3/api-docs/**",
                "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api/auth/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(
                        "/api/restaurants/**",
                        "/api/menus/**"
                ).permitAll()
                .antMatchers("/api/admin").hasRole("ADMIN")
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(jwtProvider, jwtUtil));

        return http.build();
    }


}
