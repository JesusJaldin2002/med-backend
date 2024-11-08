package com.med.backend.service;

import com.med.backend.dto.user.SaveUser;
import com.med.backend.persistence.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User registerOneCustomer(SaveUser newUser);
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByIdentifier(String identifier);
    Optional<User> findById(int id);
    User save(User user);
    void deleteById(int id);


}