package com.med.backend.service.Webhook;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {
    private final RestTemplate restTemplate;

    public WebhookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarWebhookUsuario(String id, String username, String email, String name, String role) {
        String url = "http://127.0.0.1:8000/dashboard/registrar-usuario/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", id);
        payload.put("username", username);
        payload.put("email", email);
        payload.put("name", name);
        payload.put("role", role);

        System.out.println(payload);
        System.out.println(headers);

        try {
            // Convertir el payload a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String payloadJson = objectMapper.writeValueAsString(payload);
            System.out.println("Datos JSON enviados en el webhook: " + payloadJson);

            // Crear la solicitud con el JSON
            HttpEntity<String> request = new HttpEntity<>(payloadJson, headers);
            System.out.println("Request a enviar: " + request);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Webhook enviado exitosamente: " + response.getBody());
            } else {
                System.out.println("Error al enviar webhook, código de respuesta: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Error de cliente al enviar el webhook: " + e.getMessage());
        } catch (ResourceAccessException e) {
            System.err.println("No se pudo acceder al recurso: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al enviar el webhook: " + e.getMessage());
        }
    }
}
