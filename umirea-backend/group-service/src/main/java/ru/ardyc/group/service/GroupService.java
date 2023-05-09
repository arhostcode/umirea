package ru.ardyc.group.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ardyc.auth.AuthClient;
import ru.ardyc.auth.User;
import ru.ardyc.group.entity.GroupEntity;
import ru.ardyc.group.entity.MemberEntity;
import ru.ardyc.group.model.FullMember;
import ru.ardyc.group.model.Group;
import ru.ardyc.group.model.Member;
import ru.ardyc.group.repo.GroupRepository;
import ru.ardyc.group.repo.MemberRepository;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.OutputErrorResponse;
import ru.ardyc.response.Response;
import ru.ardyc.utils.DigestUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Inject
    @RestClient
    AuthClient authClient;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MemberRepository memberRepository;

    public Response createGroup(String token, String timeTable, String name) {
        String uuid = UUID.randomUUID().toString();
        GroupEntity group = new GroupEntity(uuid, name, timeTable, DigestUtils.encodeMD5(uuid));
        groupRepository.save(group);
        addMemberByUUID(token, group.getUuid(), "owner");
        return MessageResponse.create(Group.fromEntity(group));
    }

    public void addMemberByUUID(String token, String groupUuid, String role) {
        User user = User.fromJson(authClient.getInfo(token));
        GroupEntity group = groupRepository.getGroupEntityByUuid(groupUuid);
        addMemberToGroup(user, group, role);
    }

    public Response addMemberByNameAndToken(String token, String groupName, String groupToken) {
        User user = User.fromJson(authClient.getInfo(token));
        GroupEntity group = groupRepository.getGroupEntityByNameAndToken(groupName, groupToken);
        return addMemberToGroup(user, group, "member");
    }


    private Response addMemberToGroup(User user, GroupEntity group, String role) {
        if (user == null || group == null) {
            return new OutputErrorResponse("Пользователь или группа не найдена", 100);
        }
        if (!Objects.equals(user.getEducationGroup(), "null")) {
            return new OutputErrorResponse("You are already in a group.", 101);
        }
        System.out.println("Add member to group: " + user.getToken());
        MemberEntity member = new MemberEntity(user.getUniqueID(), role, group.getUuid(), null);
        memberRepository.save(member);
        authClient.setGroup(user.getToken(), group.getUuid());
        return MessageResponse.create(Member.fromEntity(member));
    }

    public Response removeMemberByNameAndToken(String userToken, String groupName, String groupToken) {
        User user = User.fromJson(authClient.getInfo(userToken));
        GroupEntity group = groupRepository.getGroupEntityByNameAndToken(groupName, groupToken);
        if (user == null || group == null) {
            return new OutputErrorResponse("User or group not found.", 100);
        }
        MemberEntity member = memberRepository.getMemberEntityByUuid(user.getUniqueID());
        memberRepository.delete(member);
        return MessageResponse.create("Member removed");
    }

    public Response kickUser(String userToken, String kickUserUUID) {
        User user = User.fromJson(authClient.getInfo(userToken));
        if (user == null) {
            return new OutputErrorResponse("Пользователь не найден", 100);
        }

        MemberEntity requestOwner = memberRepository.getMemberEntityByUuid(user.getUniqueID());
        MemberEntity member = memberRepository.getMemberEntityByUuid(kickUserUUID);
        User kickUser = User.fromJson(authClient.getInfoByUUID(kickUserUUID));
        if (kickUser == null || member == null || requestOwner == null) {
            return new OutputErrorResponse("Пользователь не найден", 100);
        }
        if (requestOwner.getRole().equals("member")) {
            return new OutputErrorResponse("У вас недостаточно прав для удаления", 101);
        }
        if (kickUser.getToken().equals(user.getToken())) {
            return new OutputErrorResponse("Нельзя удалить себя из группы", 102);
        }
        if (member.getRole().equals("owner")) {
            return new OutputErrorResponse("Нельзя удалить владельца группы", 103);
        }
        authClient.setGroup(kickUser.getToken(), "null");
        memberRepository.delete(member);
        return MessageResponse.create("Пользователь удалён");
    }

    public void deleteGroup(String userToken, String groupName, String groupToken) {
        User user = User.fromJson(authClient.getInfo(userToken));
        GroupEntity group = groupRepository.getGroupEntityByNameAndToken(groupName, groupToken);
        if (user == null || group == null) {
            return;
        }

        MemberEntity member = memberRepository.getMemberEntityByUuid(user.getUniqueID());
        if (member == null) {
            return;
        }

        if (Objects.equals(member.getRole(), "owner")) {
            groupRepository.delete(group);
        }
    }

    public Response getUserGroup(String userToken) {
        GroupEntity groupEntity = getUserGroupByToken(userToken);
        if (groupEntity == null) {
            return MessageResponse.create("Group not found.");
        }
        Group group = Group.fromEntity(groupEntity);
        return MessageResponse.create(group);
    }

    public Response getUserGroupMembers(String userToken) {
        GroupEntity groupEntity = getUserGroupByToken(userToken);
        if (groupEntity == null) {
            return MessageResponse.create("Group not found.");
        }
        Group group = Group.fromEntity(groupEntity);
        List<MemberEntity> members = memberRepository.getMemberEntitiesByGroupId(group.getUuid());
        System.out.println("Members: " + members);
        return MessageResponse.create(members.stream().map(this::fromMember).collect(Collectors.toList()));
    }

    private GroupEntity getUserGroupByToken(String userToken) {
        User user = User.fromJson(authClient.getInfo(userToken));
        if (user == null) {
            return null;
        }
        return groupRepository.getGroupEntityByUuid(user.getEducationGroup());
    }

    private FullMember fromMember(MemberEntity member) {
        User user = User.fromJson(authClient.getInfoByUUID(member.getUuid()));
        if (user == null) {
            return null;
        }
        return new FullMember(user.getUniqueID(), user.getFirstName(), user.getLastName(), member.getRole(), member.getImageId());
    }

    public void changeGroupSchedule(String userToken, String groupSchedule) {
        GroupEntity groupEntity = getUserGroupByToken(userToken);
        if (groupEntity == null) {
            return;
        }
        groupEntity.setTimeTable(groupSchedule);
        groupRepository.save(groupEntity);
    }

    public Response removeMember(String userToken) {
        User user = User.fromJson(authClient.getInfo(userToken));
        if (user == null) {
            return new OutputErrorResponse("User not found.", 100);
        }

        MemberEntity member = memberRepository.getMemberEntityByUuid(user.getUniqueID());
        memberRepository.delete(member);
        authClient.setGroup(userToken, "null");
        return MessageResponse.create("Member removed");
    }
}
