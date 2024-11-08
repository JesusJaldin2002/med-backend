package com.med.backend.controller;

import com.med.backend.dto.LogoutResponse;
import com.med.backend.dto.auth.AuthenticationRequest;
import com.med.backend.dto.auth.AuthenticationResponse;
import com.med.backend.persistence.entity.User;
import com.med.backend.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll()")
    @MutationMapping(name = "authenticate")
    public AuthenticationResponse authenticate(@Argument("input") AuthenticationRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    @PreAuthorize("permitAll()")
    @QueryMapping(name = "validateToken")
    public Boolean validateToken(@Argument String jwt) {
        return authenticationService.validate(jwt);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping(name = "logout")
    public LogoutResponse logout() {
        authenticationService.logout();
        return new LogoutResponse("Logout successful");
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','DOCTOR','RECEPTIONIST','PATIENT')")
    @QueryMapping(name = "findMyProfile")
    public User findMyProfile() {
        return authenticationService.findLoggedInUser();
    }
}
