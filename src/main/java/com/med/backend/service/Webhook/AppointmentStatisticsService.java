package com.med.backend.service.Webhook;


import com.med.backend.dto.appointmentStatistics.AppointmentStatisticsDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AppointmentStatisticsService {

    private final RestTemplate restTemplate;

    public AppointmentStatisticsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendDailyAppointmentStatistics(AppointmentStatisticsDTO statistics) {
        String djangoUrl = "http://localhost:8000/dashboard/appointment-statistics/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AppointmentStatisticsDTO> request = new HttpEntity<>(statistics, headers);
        restTemplate.postForEntity(djangoUrl, request, String.class);
    }
}
