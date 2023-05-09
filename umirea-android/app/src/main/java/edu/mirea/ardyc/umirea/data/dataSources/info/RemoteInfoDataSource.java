package edu.mirea.ardyc.umirea.data.dataSources.info;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.net.info.InfoRemoteService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RemoteInfoDataSource extends DataSource {

    private InfoRemoteService infoRemoteService;

    public RemoteInfoDataSource(Context context, InfoRemoteService infoRemoteService) {
        super(context);
        this.infoRemoteService = infoRemoteService;
    }

    public DataResponse<List<InfoMessage>> loadData(String userToken, String lastMessageId) {
        List<InfoMessage> messages = null;
        Call<JsonObject> foldersCall = infoRemoteService.getMessages(userToken, lastMessageId);
        try {
            Response<JsonObject> response = foldersCall.execute();
            JsonElement code = response.body().get("code");
            JsonElement message = response.body().get("message");
            if (code.getAsInt() == 0) {
                messages = new Gson().fromJson(message, new TypeToken<List<InfoMessage>>() {
                }.getType());
                return new DataResponse<>(messages);
            }
            return new DataResponse<>(null, message.getAsString());
        } catch (Exception e) {
            return new DataResponse<>(null, "Ошибка загрузке новостей");
        }
    }

    public DataResponse<InfoMessage> create(String name, String message, String userToken) {
        Call<JsonObject> messageCall = infoRemoteService.sendMessage(userToken, name, RequestBody.create(MediaType.parse("text/plain"), message));
        try {
            Response<JsonObject> response = messageCall.execute();
            JsonElement code = response.body().get("code");
            System.out.println(response.body());
            JsonElement messageResponse = response.body().get("message");
            if (code.getAsInt() == 0) {
                return new DataResponse<>(new Gson().fromJson(messageResponse, InfoMessage.class));
            }
            return new DataResponse<>(null, messageResponse.getAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка создания новости");
        }
    }
}
