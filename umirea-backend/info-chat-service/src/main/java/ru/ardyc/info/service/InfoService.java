package ru.ardyc.info.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ardyc.auth.AuthClient;
import ru.ardyc.auth.User;
import ru.ardyc.info.entity.InfoEntity;
import ru.ardyc.info.model.Info;
import ru.ardyc.info.repo.InfoRepository;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.OutputErrorResponse;
import ru.ardyc.response.Response;

import javax.inject.Inject;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InfoService {

    @Autowired
    public InfoRepository infoRepository;

    @Inject
    @RestClient
    public AuthClient userClient;


    public Response createInfo(String name, String text, String userToken) {
        if (text.length() > 2000) {
            return new OutputErrorResponse("Слишком длинный текст");
        }
        User user = User.fromJson(userClient.getInfo(userToken));
        if (user == null) {
            return new OutputErrorResponse("Пользователь не найден");
        }
        if (user.getEducationGroup() == null) {
            return new OutputErrorResponse("Вы не состоите в группе");
        }
        InfoEntity info = new InfoEntity(name, UUID.randomUUID().toString(), user.getUniqueID(), user.getEducationGroup(), text);
        infoRepository.save(info);
        return new MessageResponse(Info.fromEntity(info));
    }

    public Response getInfoAfter(String userToken, String after) {
        User user = User.fromJson(userClient.getInfo(userToken));
        if (user == null) {
            return new OutputErrorResponse("Пользователь не найден");
        }
        if (user.getEducationGroup() == null) {
            return new OutputErrorResponse("Вы не состоите в группе");
        }

        if (after == null || after.isEmpty()) {
            return new MessageResponse(infoRepository.getInfoEntitiesByGroupUuid(user.getEducationGroup()).stream().map(Info::fromEntity).collect(Collectors.toList()));
        }
        InfoEntity info = infoRepository.getInfoEntityByUuid(after);
        if (info == null) {
            return new OutputErrorResponse("Новость не найдена", 404);
        }
        return new MessageResponse(infoRepository.getInfoEntitiesByGroupUuidAndIdAfter(user.getEducationGroup(), info.getId()).stream().map(Info::fromEntity).collect(Collectors.toList()));
    }
}
