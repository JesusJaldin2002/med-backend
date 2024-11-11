package com.med.backend.controller;

import com.med.backend.dto.auth.RegisteredUser;
import com.med.backend.dto.user.SaveUser;
import com.med.backend.persistence.entity.User;
import com.med.backend.persistence.repository.UserRepository;
import com.med.backend.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.med.backend.service.Webhook.WebhookService; 

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticateService;

    @Autowired
    private WebhookService webhookService; // Servicio para enviar el webhook

    // Mutación para registrar un nuevo usuario, accesible a todos
    @PreAuthorize("permitAll()")
    @MutationMapping(name = "registerUser")
    public RegisteredUser registerUser(@Argument("input") @Valid SaveUser newUser) {
         // Registrar el usuario
         RegisteredUser registeredUser = authenticateService.registerOneCustomer(newUser);
        // Log para verificar los datos que se enviarán en el webhook
        System.out.println("Enviando webhook con los datos:");
        System.out.println("ID: " + registeredUser.getId());
        System.out.println("Username: " + registeredUser.getUsername());
        System.out.println("Email: " + registeredUser.getEmail());
        System.out.println("Name: " + registeredUser.getName());
        System.out.println("Role: " + registeredUser.getRole().toString());
         // Enviar el webhook con los datos del usuario registrado
         webhookService.enviarWebhookUsuario(
             String.valueOf(registeredUser.getId()),
             registeredUser.getUsername(),
             registeredUser.getEmail(),
             registeredUser.getName(),
             registeredUser.getRole().toString()
         );

         return registeredUser;
    }

    // Consulta para obtener todos los usuarios, acceso denegado para todos
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @QueryMapping(name = "findAllUsers")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
