package ru.ardyc.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ardyc.auth.service.UserService;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.Response;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/getInfo")
    public Response getInfo(@RequestParam String token) {
        return userService.getUserByToken(token);
    }

    @GetMapping(path = "/setPassword")
    public Response setPassword(@RequestParam String token, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.setPassword(token, oldPassword, newPassword);
    }

    // Internal api
    @PostMapping(path = "/setRole")
    public Response setRole(@RequestParam String token, @RequestParam String role) {
        return userService.setRole(token, role);
    }

    @PostMapping(path = "/setGroup")
    public Response setGroup(@RequestParam String token, @RequestParam String group) {
        return userService.setGroup(token, group);
    }

}
