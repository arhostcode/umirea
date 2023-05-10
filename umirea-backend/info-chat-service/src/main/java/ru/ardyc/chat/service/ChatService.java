package ru.ardyc.chat.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ardyc.auth.AuthClient;
import ru.ardyc.auth.User;
import ru.ardyc.chat.entity.MessageEntity;
import ru.ardyc.chat.model.Message;
import ru.ardyc.chat.repo.MessageRepository;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.OutputErrorResponse;
import ru.ardyc.response.Response;

import javax.inject.Inject;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    public MessageRepository messageRepository;

    @Inject
    @RestClient
    public AuthClient userClient;


    public Response sendMessage(String text, String userToken) {
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
        MessageEntity message = new MessageEntity(UUID.randomUUID().toString(), user.getUniqueID(), user.getEducationGroup(), text);
        messageRepository.save(message);
        return new MessageResponse(Message.fromEntity(message));
    }

    public Response getMessagesAfter(String userToken, String after) {
        User user = User.fromJson(userClient.getInfo(userToken));
        if (user == null) {
            return new OutputErrorResponse("Пользователь не найден");
        }
        if (user.getEducationGroup() == null) {
            return new OutputErrorResponse("Вы не состоите в группе");
        }

        if (after == null || after.isEmpty()) {
            return new MessageResponse(messageRepository.getMessageEntitiesByGroupUuid(user.getEducationGroup()).stream().map(Message::fromEntity).collect(Collectors.toList()));
        }
        MessageEntity message = messageRepository.getMessageEntityByUuid(after);
        if (message == null) {
            return new OutputErrorResponse("Сообщение не найдено");
        }
        return new MessageResponse(messageRepository.getMessageEntitiesByGroupUuidAndIdAfter(user.getEducationGroup(), message.getId()).stream().map(Message::fromEntity).collect(Collectors.toList()));
    }
}
