package ru.ardyc.group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ardyc.group.service.GroupService;
import ru.ardyc.response.Response;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping(path = "/create")
    public Response create(@RequestParam String userToken, @RequestParam String groupTimeTable) {
        return groupService.createGroup(userToken, groupTimeTable);
    }

    @DeleteMapping(path = "/delete")
    public Response delete(@RequestParam String userToken, @RequestParam String groupTimeTable) {
        return groupService.createGroup(userToken, groupTimeTable);
    }
}
