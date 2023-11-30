package com.polstat.mp2k.auth;

import com.polstat.mp2k.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth
                        -> auth.requestMatchers("/login", "/register").permitAll()
                        //                                Panitia
                        .requestMatchers( "ipKelulusan/peserta/{id}").hasAnyAuthority("Panitia")
                        .requestMatchers(HttpMethod.POST, "/materiKU", "/kelas", "/ipKelulusan").hasAnyAuthority("Panitia")
                        .requestMatchers(HttpMethod.PATCH, "/materiKU/**", "/kelas/**", "/user/{id}").hasAnyAuthority("Panitia")
                        .requestMatchers(HttpMethod.PUT, "/materiKU/**", "/kelas/**").hasAnyAuthority("Panitia")
                        .requestMatchers(HttpMethod.DELETE, "/materiKU/**", "/kelas/**", "/user/{id}").hasAnyAuthority("Panitia")
                        .requestMatchers(HttpMethod.POST, "/nilai").hasAnyAuthority("Panitia")
                        .requestMatchers(HttpMethod.PATCH, "/nilai/**").hasAnyAuthority("Panitia")
                        .requestMatchers(HttpMethod.DELETE, "/nilai/**").hasAnyAuthority("Panitia")
                        .requestMatchers(HttpMethod.GET, "/nilai/{id}").hasAnyAuthority("Panitia")
                        .requestMatchers(HttpMethod.GET, "/ipKelulusan/peserta").authenticated()
                        .requestMatchers(HttpMethod.GET, "/ipKelulusan/{id}", "/ipKelulusan/peserta/{id}").hasAnyAuthority("Panitia")
                        //                                DENYALL
                        .requestMatchers(HttpMethod.DELETE, "/ipKelulusan").denyAll()
                        .requestMatchers(HttpMethod.PUT, "/ipKelulusan").denyAll()
                        .requestMatchers(HttpMethod.PATCH, "/ipKelulusan").denyAll()
                        //                                Semua role
                        .requestMatchers("/updatePassword").authenticated()
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
