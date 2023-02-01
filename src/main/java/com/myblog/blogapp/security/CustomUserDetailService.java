package com.myblog.blogapp.security;

import com.myblog.blogapp.entities.Role;
import com.myblog.blogapp.entities.User;
import com.myblog.blogapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailService implements UserDetailsService {
    
    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String UsernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(UsernameOrEmail, UsernameOrEmail)
                .orElseThrow(() ->
                new UsernameNotFoundException("user not found with usernameOrEmail:" + UsernameOrEmail));

        return  new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));

    }
    private Collection<? extends GrantedAuthority>mapRolesToAuthorities(Set<Role>roles){
       return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
