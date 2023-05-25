package edu.mirea.ardyc.umirea.data.dataSources.chat;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.net.chat.ChatRemoteService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RemoteChatDataSource extends DataSource {

    private ChatRemoteService chatRemoteService;

    public RemoteChatDataSource(Context context, ChatRemoteService chatRemoteService) {
        super(context);
        this.chatRemoteService = chatRemoteService;
    }

    public DataResponse<List<ChatMessage>> loadData(String userToken, String lastMessageId) {
        List<ChatMessage> messages = null;
        Call<JsonObject> foldersCall = chatRemoteService.getMessages(userToken, lastMessageId);
        try {
            Response<JsonObject> response = foldersCall.execute();
            JsonElement code = response.body().get("code");
            JsonElement message = response.body().get("message");
            if (code.getAsInt() == 0) {
                messages = new Gson().fromJson(message, new TypeToken<List<ChatMessage>>() {
                }.getType());
                return new DataResponse<>(messages);
            }
            return new DataResponse<>(null, message.getAsString());
        } catch (Exception e) {
            return new DataResponse<>(null, "Ошибка загрузке сообщений");
        }
    }

    public DataResponse<ChatMessage> sendMessage(String message, String userToken) {
        Call<JsonObject> messageCall = chatRemoteService.sendMessage(userToken, RequestBody.create(MediaType.parse("text/plain"), message));
        try {
            Response<JsonObject> response = messageCall.execute();
            JsonElement code = response.body().get("code");
            JsonElement messageResponse = response.body().get("message");
            if (code.getAsInt() == 0) {
                return new DataResponse<>(new Gson().fromJson(messageResponse, ChatMessage.class));
            }
            return new DataResponse<>(null, messageResponse.getAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка отправки сообщения");
        }
    }
}
