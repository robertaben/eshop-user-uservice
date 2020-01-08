package com.roberta.eshop.user.controller;

import com.roberta.eshop.user.model.Address;
import com.roberta.eshop.user.model.AddressType;
import com.roberta.eshop.user.persistence.ResourceNotFoundException;
import com.roberta.eshop.user.repository.AddressRepository;
import com.roberta.eshop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}/address")
    public List<Address> getAllAddressesByUserId(@PathVariable(value = "userId") Integer userId, Address address) {
        if (userRepository.findById(userId).isEmpty())
            throw new ResourceNotFoundException("UserId not found " + userId);
        return addressRepository.findByUserId(userId);
    }

    @PostMapping("/{userId}/address")
    public Address createAddress(@PathVariable(value = "userId") Integer userId, @Valid @RequestBody Address address) {
        return userRepository.findById(userId).map(user -> {
            address.setUser(user);
            if(address.getAddressType() == null)
                address.setAddressType(AddressType.SHIPPING);
            return addressRepository.save(address);
        }).orElseThrow(() -> new ResourceNotFoundException("id-" + userId));
    }

    @PutMapping("/{userId}/address/{addressId}")
    public Address updateAddress(@PathVariable(value = "userId") Integer userId,
                                 @PathVariable(value = "addressId") Integer addressId,
                                 @Valid @RequestBody Address addressRequest) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("UserId " + userId + " not found");
        }

        return addressRepository.findById(addressId).map(address -> {
            address.setAddressType(addressRequest.getAddressType());
            address.setCity(addressRequest.getCity());
            address.setHouseNumber(addressRequest.getHouseNumber());
            address.setStreet(addressRequest.getStreet());
            address.setZipCode(addressRequest.getZipCode());
            return addressRepository.save(address);
        }).orElseThrow(() -> new ResourceNotFoundException("AddressId " + addressId + "not found"));
    }

    @DeleteMapping("/{userId}/address/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable(value = "userId") Integer userId,
                                           @PathVariable(value = "addressId") Integer addressId) {
        return addressRepository.findByIdAndUserId(addressId, userId).map(address -> {
            addressRepository.delete(address);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("AddressId not found with id " + addressId + " and userId " + userId));
    }
}
