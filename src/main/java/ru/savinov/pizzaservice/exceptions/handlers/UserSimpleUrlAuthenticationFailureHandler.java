package ru.savinov.pizzaservice.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
@AllArgsConstructor
public class UserSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final FlashMapManager flashMapManager;

    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) {
        FlashMap flashMap = new FlashMap();
        flashMap.put("loginError", "Invalid email or password");
        response.sendRedirect("/login");
        flashMapManager.saveOutputFlashMap(flashMap, request, response);
    }
}