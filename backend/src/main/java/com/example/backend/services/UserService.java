package com.example.backend.services;

import com.example.backend.dtos.UserDto;
import com.example.backend.dtos.LoginDto;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public LoginDto verifyUser(UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.username(), userDto.password())
        );

        if (authentication.isAuthenticated()) {
            String jwt = jwtService.generateToken(userDto.username());
            return new LoginDto(userDto.username(), jwt);
        } else {
            return null;
        }
    }

    public String findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? user.getUsername() : null;
    }
}
