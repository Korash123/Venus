package com.example.Venus.service.userImplementation;

import com.example.Venus.entities.Users;
import com.example.Venus.repo.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/*
    @created 12/17/2024
    @project i am
    @author Korash Waiba

*/

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepo.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));

        // Convert list of roles to authorities
//        Collection<? extends GrantedAuthority> authorities = user.getRole().stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
//                .collect(Collectors.toList());

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
//                .authorities(authorities)
                .disabled(!user.getIsActive()) // Check if the user is active
                .build();
    }

}