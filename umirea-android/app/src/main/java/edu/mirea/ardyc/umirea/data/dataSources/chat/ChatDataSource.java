package edu.mirea.ardyc.umirea.data.dataSources.chat;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.dataSources.room.UmireaDatabase;
import edu.mirea.ardyc.umirea.data.dataSources.room.chat.entities.MessageEntity;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;

public class ChatDataSource extends DataSource {
    @Inject
    public ChatDataSource(Context context) {
        super(context);
    }

    public void save(List<ChatMessage> messages) {
        UmireaDatabase.getDatabase(context).chatDao().insertAll(messages);
    }

    public void clear() {
        UmireaDatabase.getDatabase(context).chatDao().clear();
    }

    public List<ChatMessage> getMessages() {
        return UmireaDatabase.getDatabase(context).chatDao().getMessages().stream().map(MessageEntity::toModel).collect(Collectors.toList());
    }

    public ChatMessage getLastMessage() {
        MessageEntity message = UmireaDatabase.getDatabase(context).chatDao().getLastMessage();
        return message == null ? null : message.toModel();
    }

}
