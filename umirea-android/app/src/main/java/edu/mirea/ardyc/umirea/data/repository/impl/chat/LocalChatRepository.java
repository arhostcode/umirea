package edu.mirea.ardyc.umirea.data.repository.impl.chat;

import android.content.Context;

import java.util.UUID;

import edu.mirea.ardyc.umirea.data.model.chat.Chat;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;
import edu.mirea.ardyc.umirea.data.model.group.Member;

public class LocalChatRepository extends ChatRepository {
    public LocalChatRepository(Context context) {
        super(context);
    }

    @Override
    public Chat getData() {
        Chat chat = new Chat();
        chat.addMessage(new ChatMessage("ID1", "Привет всем"));
        chat.addMessage(new ChatMessage("ID1", "Люблю жёлуди"));
        chat.addMessage(new ChatMessage("ID2", "Всем спецоперацию"));
        chat.addMessage(new ChatMessage("ID2", "Дорогие друзья"));
        chat.addMessage(new ChatMessage("ID2", "Нененене нене не"));
        chat.addMessage(new ChatMessage("ID2", "Хачукал ывапрос"));
        return chat;
    }
}
