package edu.mirea.ardyc.umirea.data.repository.impl.info;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.dataSources.chat.ChatDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.chat.RemoteChatDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.info.InfoDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.info.RemoteInfoDataSource;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public class InfoRepository extends Repository {

    private final InfoDataSource infoDataSource;
    private final RemoteInfoDataSource remoteInfoDataSource;


    @Inject
    public InfoRepository(Context context, InfoDataSource infoDataSource, RemoteInfoDataSource remoteInfoDataSource) {
        super(context);
        this.infoDataSource = infoDataSource;
        this.remoteInfoDataSource = remoteInfoDataSource;
    }

    public DataResponse<List<InfoMessage>> loadInfoLocal() {
        return new DataResponse<>(infoDataSource.getMessages());
    }

    public DataResponse<List<InfoMessage>> loadInfo(String userToken) {
        String lastMessageId = "";
        InfoMessage chatMessage = infoDataSource.getLastMessage();
        if (chatMessage != null) {
            lastMessageId = chatMessage.getUuid();
        }
        DataResponse<List<InfoMessage>> messages = remoteInfoDataSource.loadData(userToken, lastMessageId);
        if (!messages.isError())
            infoDataSource.save(messages.getData());
        return messages;
    }

    public DataResponse<InfoMessage> create(String name, String message, String userToken) {
        return remoteInfoDataSource.create(name, message, userToken);
    }

    public void clear() {
        infoDataSource.clear();
    }

}
