package com.mindhub.todolist.config;

import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Long getAuthUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel userModel = userRepository.findByEmail(email).orElse(null);
        assert userModel != null;
        return userModel.getId();
    }

}