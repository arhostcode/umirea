package edu.mirea.ardyc.umirea.data.dataSources.info;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.dataSources.room.UmireaDatabase;
import edu.mirea.ardyc.umirea.data.dataSources.room.chat.entities.MessageEntity;
import edu.mirea.ardyc.umirea.data.dataSources.room.info.entities.InfoEntity;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;

public class InfoDataSource extends DataSource {
    @Inject
    public InfoDataSource(Context context) {
        super(context);
    }

    public void save(List<InfoMessage> messages) {
        UmireaDatabase.getDatabase(context).infoDao().insertAll(messages);
    }

    public void clear() {
        UmireaDatabase.getDatabase(context).infoDao().clearAll();
    }

    public List<InfoMessage> getMessages() {
        return UmireaDatabase.getDatabase(context).infoDao().getAll().stream().map(InfoEntity::toInfo).collect(Collectors.toList());
    }

    public InfoMessage getLastMessage() {
        InfoEntity message = UmireaDatabase.getDatabase(context).infoDao().getLast();
        return message == null ? null : message.toInfo();
    }

}
