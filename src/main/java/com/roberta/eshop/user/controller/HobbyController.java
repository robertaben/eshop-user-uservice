package com.roberta.eshop.user.controller;

import com.roberta.eshop.user.model.Hobby;
import com.roberta.eshop.user.persistence.ResourceNotFoundException;
import com.roberta.eshop.user.repository.HobbyRepository;
import com.roberta.eshop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class HobbyController {
    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hobbies")
    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }

    @PostMapping("/hobbies")
    public Hobby createHobby(@RequestBody Hobby hobby) {
        return hobbyRepository.save(hobby);
    }

    @GetMapping("/hobbies/{hobbyId}")
    public Hobby getHobbyById(@PathVariable Integer hobbyId) {
        Optional<Hobby> hobby = hobbyRepository.findById(hobbyId);
        if (hobby.isEmpty())
            throw new ResourceNotFoundException("HobbyId not found " + hobbyId);
        return hobby.get();
    }

    @PutMapping("hobbies/{hobbyId}")
    public ResponseEntity<Hobby> updateHobby(@Valid @RequestBody Hobby hobbyDetails, @PathVariable Integer hobbyId) {
        Optional<Hobby> hobby = hobbyRepository.findById(hobbyId);
        if (hobby.isEmpty())
            throw new ResourceNotFoundException("HobbyId not found " + hobbyId);
        hobbyDetails.setId(hobbyId);
        final Hobby updatedHobby = hobbyRepository.save(hobbyDetails);
        return ResponseEntity.ok(updatedHobby);
    }

    @DeleteMapping("hobbies/{hobbyId}")
    public ResponseEntity<?> deleteHobby(@PathVariable Integer hobbyId) {
        return hobbyRepository.findById(hobbyId).map(hobby -> {
            hobbyRepository.delete(hobby);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Hobby with " + hobbyId + " not found"));
    }

}
