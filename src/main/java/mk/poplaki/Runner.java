package mk.poplaki;

import mk.poplaki.config.security.user.Role;
import mk.poplaki.domain.User;
import mk.poplaki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.existsByRole(Role.ADMIN)) {
            return;
        }

        User user = new User();
        user.setNickname("Admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin1234"));
        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }
}
