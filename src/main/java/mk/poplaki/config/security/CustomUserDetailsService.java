package mk.poplaki.config.security;

import mk.poplaki.config.security.user.CustomUserDetails;
import mk.poplaki.domain.User;
import mk.poplaki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(email.trim());
        if (userOptional.isEmpty()) {
            String message = String.format("user with email %s not found", email);
            log.error(message);
            throw new UsernameNotFoundException(message);
        }

        User user = userOptional.get();

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setId(user.getId());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        userDetails.setRole(user.getRole());
        return userDetails;
    }
}
