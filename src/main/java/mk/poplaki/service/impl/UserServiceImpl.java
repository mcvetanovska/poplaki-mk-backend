package mk.poplaki.service.impl;

import mk.poplaki.config.exception.ConflictException;
import mk.poplaki.config.security.user.Role;
import mk.poplaki.domain.User;
import mk.poplaki.dto.auth.UserRequest;
import mk.poplaki.repository.UserRepository;
import mk.poplaki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserRequest data) {
        if (userRepository.existsByEmailIgnoreCase(data.getEmail().trim())) {
            throw new ConflictException("User with email " + data.getEmail() + " already exists");
        }

        User user = new User();
        user.setNickname(data.getNickname());
        user.setEmail(data.getEmail().toLowerCase().trim());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    public long getUserCount() {
        return userRepository.countByRole(Role.USER);
    }
}
