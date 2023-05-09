package edu.mirea.ardyc.umirea.data.repository.impl.chat;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.dataSources.chat.ChatDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.chat.RemoteChatDataSource;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.chat.Chat;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public class ChatRepository extends Repository {

    private final ChatDataSource chatDataSource;
    private final RemoteChatDataSource remoteChatDataSource;


    @Inject
    public ChatRepository(Context context, ChatDataSource chatDataSource, RemoteChatDataSource remoteChatDataSource) {
        super(context);
        this.chatDataSource = chatDataSource;
        this.remoteChatDataSource = remoteChatDataSource;
    }

    public DataResponse<List<ChatMessage>> loadChatMessagesLocal() {
        return new DataResponse<>(chatDataSource.getMessages());
    }

    public DataResponse<List<ChatMessage>> loadChatMessages(String userToken) {
        String lastMessageId = "";
        ChatMessage chatMessage = chatDataSource.getLastMessage();
        if (chatMessage != null) {
            lastMessageId = chatMessage.getUuid();
        }
        DataResponse<List<ChatMessage>> messages = remoteChatDataSource.loadData(userToken, lastMessageId);
        if (!messages.isError())
            chatDataSource.save(messages.getData());
        return messages;
    }

    public DataResponse<ChatMessage> sendMessage(String message, String userToken) {
        return remoteChatDataSource.sendMessage(message, userToken);
    }

    public void clear() {
        chatDataSource.clear();
    }

}
