package babbuddy.domain.user.application.service.impl;


import babbuddy.domain.oauth2.application.service.CreateAccessTokenAndRefreshTokenService;
import babbuddy.domain.user.application.service.UserService;

import babbuddy.domain.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;

//@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CreateAccessTokenAndRefreshTokenService createAccessTokenAndRefreshTokenService;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


}
