package com.roberta.eshop.user.controller;

import com.roberta.eshop.user.model.User;
import com.roberta.eshop.user.persistence.ResourceNotFoundException;
import com.roberta.eshop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        user.setPassword(pwEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping
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
    public ResponseEntity<User> updateUser(@Valid @RequestBody User userDetails, @PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new ResourceNotFoundException("UserId not found " + id);
        userDetails.setPassword(new BCryptPasswordEncoder().encode(userDetails.getPassword()));
        userDetails.setId(id);
        final User updatedUser = userRepository.save(userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
}
