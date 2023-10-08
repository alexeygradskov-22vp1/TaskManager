package ru.psu.fvt.moipevm.taskmanager.authentification;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.psu.fvt.moipevm.taskmanager.services.UserServiceImpl;


@Component
public class AuthProvider implements AuthenticationProvider {
    private final UserServiceImpl userService;
    Logger logger = LoggerFactory.getLogger(AuthProvider.class);
    public AuthProvider(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userService.loadUserByUsername(login);
        if (!userDetails.getPassword().equals(password)) {
            throw new BadCredentialsException("Bad password");
        }
        if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("Аккаунт заблокирован");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
