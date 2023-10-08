package ru.psu.fvt.moipevm.taskmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import ru.psu.fvt.moipevm.taskmanager.authentification.AuthProvider;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthProvider authProvider;


    @Autowired
    public SecurityConfig(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(
                        (requests) -> requests.
                                requestMatchers(
                                        antMatcher("/css/**"),
                                        antMatcher("/js/**"),
                                        antMatcher("/"),
                                        antMatcher("/pageNotFound")).
                                permitAll().
                                requestMatchers(
                                        antMatcher("/login")).
                                anonymous().
                                requestMatchers(
                                        antMatcher("/logout")).
                                authenticated().
                                requestMatchers(
                                        antMatcher("/admin"),
                                        antMatcher("/admin/deleteUser/**"),
                                        antMatcher("/admin/blockUser/**"),
                                        antMatcher("/users")).
                                hasRole("ADMIN").
                                requestMatchers(
                                        antMatcher("/boards"),
                                        antMatcher("/main"),
                                        antMatcher("/boards/create"),
                                        antMatcher("/boards/**"),
                                        antMatcher("/boards/delete/**"),
                                        antMatcher("/deleteAccount/**")).
                                hasAnyRole("ADMIN", "USER")).
                formLogin((login) ->
                        login.loginPage("/login").
                                failureUrl("/login?error=true").
                                defaultSuccessUrl("/main"))
                .logout((logout) ->
                        logout.permitAll().
                                deleteCookies().
                                logoutUrl("/logout").
                                logoutSuccessUrl("/login").
                                permitAll()).
                authenticationProvider(authProvider).
                cors(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
