package edu.mirea.ardyc.umirea.data.dataSources.group;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.group.Member;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.data.net.group.GroupRemoteService;
import edu.mirea.ardyc.umirea.data.repository.impl.group.api.RemoteGroup;
import retrofit2.Call;
import retrofit2.Response;

public class RemoteGroupDataSource extends DataSource {

    private final GroupRemoteService groupRemoteService;

    public RemoteGroupDataSource(Context context, GroupRemoteService groupRemoteService) {
        super(context);
        this.groupRemoteService = groupRemoteService;
    }


    public DataResponse<Group> createGroup(String userToken, String groupTimetable, String groupName) {
        Call<JsonObject> groupCall = groupRemoteService.createGroup(userToken, groupTimetable, groupName);
        Response<JsonObject> response = null;
        try {
            response = groupCall.execute();
            return parseGroupWithMembers(userToken, response.body());
        } catch (Exception e) {
            return DataResponse.error("Ошибка при создании группы");
        }

    }


    public DataResponse<Group> getGroup(String userToken) {
        Call<JsonObject> groupCall = groupRemoteService.getGroup(userToken);
        Response<JsonObject> response = null;
        try {
            response = groupCall.execute();
            return parseGroupWithMembers(userToken, response.body());
        } catch (Exception e) {
            return DataResponse.error("Ошибка при получении группы");
        }
    }

    private String getAsStringOrNull(JsonElement object) {
        return object.isJsonNull() ? null : object.getAsString();
    }

    public DataResponse<Group> connectToGroup(String userToken, String groupName, String groupToken) {
        Call<JsonObject> groupCall = groupRemoteService.connectGroup(userToken, groupName, groupToken);
        Response<JsonObject> response = null;
        try {
            response = groupCall.execute();
            return parseGroupWithMembers(userToken, response.body());
        } catch (IOException e) {
            return DataResponse.error("Ошибка при подключении к группе");
        }

    }

    private DataResponse<Group> parseGroupWithMembers(String userToken, JsonObject jsonElement) {
        try {
            JsonElement code = jsonElement.get("code");
            JsonElement message = jsonElement.get("message");
            System.out.println(message);
            if (code.getAsInt() == 0) {
                RemoteGroup remoteGroup = new Gson().fromJson(message.getAsJsonObject(), RemoteGroup.class);
                Call<JsonObject> groupMembersCall = groupRemoteService.getMembers(userToken);

                Response<JsonObject> groupMembersResponse = null;

                groupMembersResponse = groupMembersCall.execute();

                List<Member> members = new ArrayList<>();
                JsonElement codeMembers = groupMembersResponse.body().get("code");
                JsonElement messageMembers = groupMembersResponse.body().get("message");
                if (codeMembers.getAsInt() == 0) {
                    for (JsonElement member : messageMembers.getAsJsonArray()) {
                        members.add(new Member(member.getAsJsonObject().get("firstName").getAsString(),
                                member.getAsJsonObject().get("lastName").getAsString(),
                                member.getAsJsonObject().get("role").getAsString(),
                                member.getAsJsonObject().get("uuid").getAsString(),
                                getAsStringOrNull(member.getAsJsonObject().get("imageId"))));
                    }
                    Group group = new Group(members, remoteGroup.getUuid(), remoteGroup.getName(), remoteGroup.getToken(), remoteGroup.getTimeTable());
                    return new DataResponse<>(group);
                } else {
                    return DataResponse.error("Ошибка при получении группы");
                }
            }
        } catch (Exception e) {
            return DataResponse.error("Ошибка при получении группы");
        }
        return DataResponse.error("Ошибка при получении группы");
    }

    public void changeSchedule(String userToken, String schedule) {
        try {
            groupRemoteService.changeSchedule(userToken, schedule).execute();
        } catch (Exception ignored) {
        }
    }

    public void kickMember(String userToken, String kickUserUuid) {
        try {
            groupRemoteService.kick(userToken, kickUserUuid).execute();
        } catch (IOException ignored) {
        }
    }
}
