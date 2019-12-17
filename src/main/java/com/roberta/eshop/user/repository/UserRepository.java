package com.roberta.eshop.user.repository;

import com.roberta.eshop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
