package ru.ardyc.auth;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ardyc.auth.AuthClient;

import javax.inject.Inject;

@RestController
@RequestMapping("/auth")
public class AuthService {
    @Inject
    @RestClient
    AuthClient authClient;

    @GetMapping(path = "/login")
    public String login(@RequestParam String login, @RequestParam String password) {
        return authClient.login(login, password);
    }

    @GetMapping(path = "/register")
    public String register(@RequestParam String login, @RequestParam String password, @RequestParam String educationGroup, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String imageId, @RequestParam String role) {
        return authClient.register(login, password, educationGroup, firstName, lastName, imageId, role);
    }
}
