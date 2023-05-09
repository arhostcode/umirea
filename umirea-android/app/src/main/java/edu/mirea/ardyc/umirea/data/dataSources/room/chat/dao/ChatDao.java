package edu.mirea.ardyc.umirea.data.dataSources.room.chat.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.room.chat.entities.MessageEntity;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;

@Dao
public abstract class ChatDao {

    @Query("SELECT * FROM chat")
    public abstract List<MessageEntity> getMessages();

    @Insert
    public abstract void insert(MessageEntity chatEntity);

    @Transaction
    public void insertAll(List<ChatMessage> messages) {
        messages.forEach(chatEntity -> insert(MessageEntity.fromModel(chatEntity)));
    }

    @Query("DELETE FROM chat")
    public abstract void clear();

    @Query("SELECT * FROM chat ORDER BY id DESC LIMIT 1")
    public abstract MessageEntity getLastMessage();

}
