package ru.artemaa.shaurma.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.artemaa.shaurma.User;
import ru.artemaa.shaurma.data.jpa.UserRepository;

import static java.util.Objects.isNull;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (isNull(user)) {
            throw new UsernameNotFoundException("Could not find user with username: " + username);
        }
        return user;
    }
}
