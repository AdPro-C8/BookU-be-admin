package id.ac.ui.cs.advprog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import id.ac.ui.cs.advprog.admin.enums.UserRole;

import java.util.List;

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    private static final String BOOK_PATTERN = "/book/**";
    private static final String GET_MULTIPLE_BOOKS_PATH = "/book/get-multiple";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers(HttpMethod.GET, BOOK_PATTERN)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, GET_MULTIPLE_BOOKS_PATH)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, BOOK_PATTERN)
                        .hasRole(UserRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.DELETE, BOOK_PATTERN)
                        .hasRole(UserRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.PATCH, BOOK_PATTERN)
                        .hasRole(UserRole.ADMIN.toString())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
