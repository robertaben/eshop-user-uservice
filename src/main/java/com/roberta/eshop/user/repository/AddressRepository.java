package com.roberta.eshop.user.repository;

import com.roberta.eshop.user.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUserId(Integer userId);
    Optional<Address> findByIdAndUserId(Integer id, Integer userId);
}
