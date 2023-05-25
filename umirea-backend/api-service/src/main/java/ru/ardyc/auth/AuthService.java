package ru.ardyc.auth;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ardyc.auth.AuthClient;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping
public class AuthService {
    @Inject
    @RestClient
    AuthClient authClient;

    @GetMapping(path = "auth/login")
    public Response login(@RequestParam String login, @RequestParam String password) {
        return authClient.login(login, password);
    }

    @GetMapping(path = "auth/register")
    public Response register(@RequestParam String login, @RequestParam String password,  @RequestParam String firstName, @RequestParam String lastName, @RequestParam String verificationCode) {
        return authClient.register(login, password, firstName, lastName, verificationCode);
    }

    @GetMapping(path = "auth/verify")
    public Response verify(@RequestParam String login, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName) {
        return authClient.verify(login, password, firstName, lastName);
    }

    @GetMapping(path = "auth/verifyReset")
    public Response verifyReset(@RequestParam String login) {
        return authClient.verifyResetPassword(login);
    }

    @GetMapping(path = "auth/reset")
    public Response reset(@RequestParam String login, @RequestParam String newPassword, @RequestParam String verificationCode) {
        return authClient.resetPassword(login, newPassword, verificationCode);
    }

    @GetMapping(path = "user/getInfo")
    public Response getInfo(@RequestParam String token) {
        return authClient.getInfo(token);
    }
}
