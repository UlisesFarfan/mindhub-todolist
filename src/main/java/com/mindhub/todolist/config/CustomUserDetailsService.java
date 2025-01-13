package com.mindhub.todolist.config;

import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModelEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new User(userModelEntity.getEmail(), userModelEntity.getPassword(), AuthorityUtils.createAuthorityList(userModelEntity.getRole().toString()));
    }

    private String getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User authenticatedUser)) {
            throw new IllegalStateException("User not authenticated");
        }
        return authenticatedUser.getUsername();
    }

    public void isTheUserAuth(Long id) throws UserExceptions {
        String userEmail = getAuthenticatedUser();
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Data"));
        if (!user.getEmail().equals(userEmail)) {
             throw new UserExceptions("Invalid Data");
        }
    }

}