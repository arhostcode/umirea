package ru.ardyc.group.service;

import io.vertx.mutiny.core.eventbus.Message;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ardyc.auth.AuthClient;
import ru.ardyc.auth.User;
import ru.ardyc.group.entity.GroupEntity;
import ru.ardyc.group.model.Group;
import ru.ardyc.group.repo.GroupRepository;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.OutputErrorResponse;
import ru.ardyc.response.Response;

import javax.inject.Inject;
import java.util.UUID;

@Service
public class GroupService {

    @Inject
    @RestClient
    AuthClient authClient;

    @Autowired
    GroupRepository groupRepository;

    public Response createGroup(String token, String timeTable) {
        User user = User.fromJson(authClient.getInfo(token));
        if (user == null) {
            return new OutputErrorResponse("User not found.", 100);
        }
        if (user.getEducationGroup() != null) {
            return new OutputErrorResponse("You are already in a group.", 101);
        }
        GroupEntity group = new GroupEntity(UUID.randomUUID().toString(), timeTable);

        groupRepository.save(group);
        user.setEducationGroup(group.getUuid());
        return MessageResponse.create(Group.fromEntity(group));
    }

}
