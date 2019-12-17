package com.roberta.eshop.user.controller;

import com.roberta.eshop.user.model.User;
import com.roberta.eshop.user.persistence.ResourceNotFoundException;
import com.roberta.eshop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        user.setPassword(pwEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new ResourceNotFoundException("UserId not found " + id);

        return user.get();
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new ResourceNotFoundException("UserId not found " + id);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setId(id);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}
