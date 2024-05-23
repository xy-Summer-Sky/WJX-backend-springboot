package com.example.backend.service;

import com.example.backend.DAO.UserDAO;
import com.example.backend.Exceptions.UserEmailNotFoundException;
import com.example.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UserEmailNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserEmailNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}

