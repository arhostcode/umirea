package edu.mirea.ardyc.umirea.data.repository.impl.chat;

import android.content.Context;

import edu.mirea.ardyc.umirea.data.model.chat.Chat;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public abstract class ChatRepository extends Repository<Chat> {
    public ChatRepository(Context context) {
        super(context);
    }
}
