package ru.psu.fvt.moipevm.taskmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.psu.fvt.moipevm.taskmanager.exceptions.DeleteException;
import ru.psu.fvt.moipevm.taskmanager.model.Role;
import ru.psu.fvt.moipevm.taskmanager.model.User;
import ru.psu.fvt.moipevm.taskmanager.repositories.RoleRepository;
import ru.psu.fvt.moipevm.taskmanager.repositories.UserRepository;
import ru.psu.fvt.moipevm.taskmanager.security.SecurityConfig;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements BaseService<User>, UserDetailsService {
    private final UserRepository userRepository;

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String create(User user) {
        if (userRepository.existsUserByLogin(user.getLogin())) {
            return "User already exists";
        }
        if (!user.getPassword()
                .matches("^(?=.*[A-Z]+)(?=.*\\d+)(?=.*[a-z]+)(?=.*[.,/!?]+).{8,15}$")) {
            return "Password don't valid:\n" +
                    "1 digit \n 1 special character \n 1 alphabetic \n min 8 characters";
        }
        if (user.getPassword().equals(user.getPasswordConfirm())) {
            return "Passwords don't match";
        }
        user.setRoles(Collections.singleton(new Role(0, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setLocked(false);
        userRepository.save(user);
        return "";
    }

    public void toggleUserBlock(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setLocked(!user.isLocked());
            userRepository.save(user);
        }
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User read(int id) {
        return userRepository.getOne(id);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(int id) throws DeleteException {
        if (!userRepository.existsById(id)) {
            throw new DeleteException("User not found");
        }
        userRepository.deleteById(id);
    }

    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь" + login + " не найден");
        }

        List<GrantedAuthority> grantedAuthorities;
        grantedAuthorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}

