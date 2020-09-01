package com.test.changerates.service.impl;

import com.test.changerates.entity.User;
import com.test.changerates.repositories.UserRepository;
import com.test.changerates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User input) {
        if (input != null) {
            if (userRepository.findUserByEmail(input.getEmail()) != null) {
                throw new RuntimeException("User with this email is already exist");
            }
            if (userRepository.findUserByUsername(input.getUsername()) != null) {
                throw new RuntimeException("User with this login is already exist");
            }
            input.setPassword(passwordEncoder.encode(input.getPassword()));
            return userRepository.save(input);
        }
        return null;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).get();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return user;
        }
        return null;
    }
}
