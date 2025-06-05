package com.pixel.transfer.service.auth;

import com.pixel.transfer.dto.AuthRequest;
import com.pixel.transfer.dto.AuthResponse;
import com.pixel.transfer.entity.EmailData;
import com.pixel.transfer.entity.User;
import com.pixel.transfer.repository.EmailDataRepository;
import com.pixel.transfer.repository.UserRepository;
import com.pixel.transfer.service.auth.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmailDataRepository emailDataRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse authenticate(AuthRequest request) {
        EmailData emailData = emailDataRepository.findByEmail(request.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("Invalid login or password"));
        User user = userRepository.findById(emailData.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid login or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid login or password");
        }

        String token = jwtService.generateToken(user.getId());
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
