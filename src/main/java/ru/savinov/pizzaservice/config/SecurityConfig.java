package ru.savinov.pizzaservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import ru.savinov.pizzaservice.services.UserService;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import static ru.savinov.pizzaservice.entities.Role.ADMIN;
import static ru.savinov.pizzaservice.entities.Role.USER;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/", "/login", "/register", "/images/**").permitAll()
                        .antMatchers("/orders", "/orders/**").hasAuthority(USER.getAuthority())
                        .antMatchers("/users", "/api/**").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults())

                .formLogin(login -> login
                        .loginPage("/login").loginProcessingUrl("/authenticateTheUser")
                        .failureUrl("/register/login-error")
                        .defaultSuccessUrl("/design")
                        .loginProcessingUrl("/authenticateTheUser"))

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))

                .csrf(csrf -> csrf
                        .ignoringAntMatchers("/h2-console/**"))

                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/design")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())));

    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            UserDetails userDetails = getOrCreateUserDetails(userRequest);
            DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());
            return (OidcUser) Proxy.newProxyInstance(SecurityConfig.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(oidcUser, args));
        };
    }

    private UserDetails getOrCreateUserDetails(OidcUserRequest userRequest) {
        String email = userRequest.getIdToken().getClaim("email");
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(email);
        } catch (Exception e) {
            userDetails = createNewUser(userRequest);
        }
        return userDetails;
    }

    private UserDetails createNewUser(OidcUserRequest userRequest) {
        return userService.create(userRequest);
    }

}