package com.spring.service;

import com.spring.entity.UserDao;
import com.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao user = userRepository.findByUsername(username);
        List<SimpleGrantedAuthority> roles = null;
        if(username.equals(user.getUsername()) && user.getRole().equals("admin")){
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(user.getUsername(), user.getPassword(), roles);
        }else if(username.equals(user.getUsername()) && user.getRole().equals("user")){
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(user.getUsername(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found with username "+username);
    }
    public UserDao saveUser(UserDao userDao){
        UserDao user = new UserDao();
        user.setUsername(userDao.getUsername());
        user.setPassword(passwordEncoder.encode(userDao.getPassword()));
        user.setRole(userDao.getRole());
        return userRepository.save(user);
    }
}
