package com.med.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.med.backend.persistence.entity.User;
import com.med.backend.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Cargando datos desde data.json...");

        try (InputStream inputStream = new ClassPathResource("data.json").getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            List<User> items = mapper.readValue(inputStream, new TypeReference<List<User>>() {});

            for (User user : items) {
                // Verificar si el usuario ya existe por su username
                if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
                    userRepository.save(user);
                    System.out.println("Usuario " + user.getUsername() + " cargado.");
                } else {
                    System.out.println("Usuario " + user.getUsername() + " ya existe. No se cargará nuevamente.");
                }
            }

            System.out.println("Carga de datos completa.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
