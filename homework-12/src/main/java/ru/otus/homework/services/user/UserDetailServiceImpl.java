package ru.otus.homework.services.user;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.otus.homework.repositories.UserRepository;

import java.util.List;

@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private static final List<GrantedAuthority> ROLE_USER = AuthorityUtils.createAuthorityList("ROLE_USER");

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val domainUser = userRepository.findUserByUsername(username);

        return new User(domainUser.getUsername(), domainUser.getPassword(), domainUser.getEnabled(), true,
                true, true, ROLE_USER);
    }
}
