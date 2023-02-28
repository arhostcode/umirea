package ru.ardyc.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ardyc.auth.service.AuthService;
import ru.ardyc.response.Response;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/login")
    public Response auth(@RequestParam String login, @RequestParam String password) {
        return authService.auth(login, password);
    }

    @GetMapping(path = "/register")
    public Response register(@RequestParam String login, @RequestParam String password, @RequestParam String educationGroup, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String imageId, @RequestParam String verificationCode) {
        return authService.register(login, password, educationGroup, firstName, lastName, imageId, "Студент", verificationCode);
    }

    @GetMapping(path = "/verify")
    public Response verify(@RequestParam String login, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName) {
        return authService.verify(login, password, firstName, lastName);
    }

    @GetMapping(path = "/verifyReset")
    public Response verifyReset(@RequestParam String login) {
        return authService.verifyResetPassword(login);
    }

    @GetMapping(path = "/reset")
    public Response reset(@RequestParam String login, @RequestParam String newPassword, @RequestParam String verificationCode) {
        return authService.resetPassword(login, newPassword, verificationCode);
    }

    @GetMapping(path = "/delete/{token}")
    public Response deleteAccount(@PathVariable String token) {
        return authService.deleteAccount(token);
    }


}
