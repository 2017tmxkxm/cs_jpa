package com.mysite.csJpa.user;

import com.mysite.csJpa.user.dto.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(AddUserRequest addUserRequest) {
        userRepository.save(addUserRequest.toEntity(passwordEncoder.encode(addUserRequest.getPassword())));
    }
}
