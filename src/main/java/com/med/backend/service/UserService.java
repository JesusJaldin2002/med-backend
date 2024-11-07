package com.med.backend.service;

import com.med.backend.dto.SaveUser;
import com.med.backend.persistence.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User registerOneCustomer(SaveUser newUser);

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByIdentifier(String identifier);

}