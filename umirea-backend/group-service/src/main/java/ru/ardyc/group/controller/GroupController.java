package ru.ardyc.group.controller;

import org.jboss.logging.annotations.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ardyc.group.service.GroupService;
import ru.ardyc.response.OutputErrorResponse;
import ru.ardyc.response.Response;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping(path = "/create")
    public Response create(@RequestParam String userToken, @RequestParam String groupTimeTable, @RequestParam String groupName) {
        if (!isValid(userToken, groupTimeTable, groupName)) {
            return new OutputErrorResponse("Неверные параметры", 100);
        }
        return groupService.createGroup(userToken, groupTimeTable, groupName);
    }

    @GetMapping(path = "/connect")
    public Response connect(@RequestParam String userToken, @RequestParam String groupName, @RequestParam String groupToken) {
        if (!isValid(userToken, groupName, groupToken)) {
            return new OutputErrorResponse("Неверные параметры", 100);
        }
        return groupService.addMemberByNameAndToken(userToken, groupName, groupToken);
    }

    @GetMapping(path = "/disconnect")
    public Response disconnect(@RequestParam String userToken) {
        if (!isValid(userToken)) {
            return new OutputErrorResponse("Неверные параметры", 100);
        }
        return groupService.removeMember(userToken);
    }

    @PostMapping(path = "/kick")
    public Response kick(@RequestParam String userToken, @RequestParam String kickUserUuid) {
        if (!isValid(userToken, kickUserUuid)) {
            return new OutputErrorResponse("Неверные параметры", 100);
        }
        return groupService.kickUser(userToken, kickUserUuid);
    }

    @PostMapping(path = "/set/role")
    public Response setRole(@RequestParam String userToken, @RequestParam String userUuid, @RequestParam String role) {
        if (!isValid(userToken, userUuid, role)) {
            return new OutputErrorResponse("Неверные параметры", 100);
        }
        return groupService.kickUser(userToken, userUuid);
    }

    @DeleteMapping(path = "/delete")
    public void delete(@RequestParam String userToken, @RequestParam String groupName, @RequestParam String groupToken) {
        if (!isValid(userToken, groupName, groupToken)) {
            return;
        }
        groupService.deleteGroup(userToken, groupName, groupToken);
    }

    @GetMapping(path = "/get")
    public Response getUserGroup(@RequestParam String userToken) {
        if (!isValid(userToken)) {
            return new OutputErrorResponse("Неверные параметры", 100);
        }
        return groupService.getUserGroup(userToken);
    }


    @GetMapping(path = "/get/members")
    public Response getUserGroupMembers(@RequestParam String userToken) {
        if (!isValid(userToken)) {
            return new OutputErrorResponse("Неверные параметры", 100);
        }
        return groupService.getUserGroupMembers(userToken);
    }

    @PostMapping(path = "/change/schedule")
    public void changeGroupSchedule(@RequestParam String userToken, @RequestParam String groupSchedule) {
        if (!isValid(userToken, groupSchedule)) {
            return;
        }
        groupService.changeGroupSchedule(userToken, groupSchedule);
    }

    private boolean isValid(Object... args) {
        for (Object o : args) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }


}
