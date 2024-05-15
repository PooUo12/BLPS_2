package com.example.blps_2.service;

import com.example.blps_2.model.Role;
import com.example.blps_2.model.User;
import com.example.blps_2.repository.UserRepository;
import com.example.blps_2.security.JWTProvider;
import com.example.blps_2.userXML.Users;
import com.example.blps_2.userXML.XMLWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtUtil;
    private final UserRepository userRepository;;

    @Autowired
    public UserService(JWTProvider jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        XMLWorker xmlWorker = new XMLWorker();
        Users users = xmlWorker.xmlRead();
        if (users == null){
            return;
        }
        for (com.example.blps_2.userXML.User user: users.getUserList()){
            saveUser(new User(user.getId(), user.getUsername(), user.getPass(), Collections.singleton(new Role(1L, "ROLE_USER")), user.getRatings()));
        }

    }


    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public Map<String, String> login(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB == null) {
            return Map.of("message", "No such user");
        }

        if (!userFromDB.getUsername().equals(user.getUsername()) || !passwordEncoder.matches(user.getPassword(), userFromDB.getPassword())) {
            return Map.of("message", "Incorrect login or password");
        }

        var token = jwtUtil.generateToken(userFromDB);
        return Map.of("jwt-token", token);


    }

    @Transactional(readOnly = false)
    public Map<String, String> saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return Map.of("message", "Such a user already exists");
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        var token = jwtUtil.generateToken(user);
        return Map.of("jwt-token", token);
    }

    @Transactional(readOnly = false)
    public Map<String, String> saveAdmin(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return Map.of("message", "Such a user already exists");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, "ROLE_USER"));
        roles.add(new Role(2L, "ROLE_ADMIN"));

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        var token = jwtUtil.generateToken(user);
        return Map.of("jwt-token", token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with name " + username + " not found!");
        }

        return user;
    }
    @PreDestroy
    public void destroy(){
        List<com.example.blps_2.userXML.User> users = new ArrayList<>();
        for(User user: allUsers()){
            users.add(new com.example.blps_2.userXML.User(user.getId(), user.getUsername(), user.getPassword(), user.getRatings()));
            XMLWorker xmlWorker = new XMLWorker();
            xmlWorker.xmlWrite(new Users(users));
        }
    }
}

