package edu.mirea.ardyc.umirea.data.repository.impl.group;

import android.content.Context;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.dataSources.group.GroupDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.group.RemoteGroupDataSource;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public class GroupRepository extends Repository {

    private GroupDataSource groupDataSource;
    private RemoteGroupDataSource remoteGroupDataSource;

    @Inject
    public GroupRepository(Context context, GroupDataSource groupDataSource, RemoteGroupDataSource remoteGroupDataSource) {
        super(context);
        this.groupDataSource = groupDataSource;
        this.remoteGroupDataSource = remoteGroupDataSource;
    }

    public DataResponse<Group> getRemoteGroup(String userToken) {
        DataResponse<Group> response = remoteGroupDataSource.getGroup(userToken);
        if (!response.isError())
            groupDataSource.saveGroup(response.getData());
        return response;
    }


    public DataResponse<Group> createGroup(String userToken, String groupTimetable, String groupName) {
        try {
            return remoteGroupDataSource.createGroup(userToken, groupTimetable, groupName);
        } catch (Exception e) {
            return new DataResponse<>(null, "Создание группы не удалось");
        }
    }

    public void saveGroup(Group group) {
        groupDataSource.saveGroup(group);
    }

    public DataResponse<Group> connectToGroup(String userToken, String groupName, String groupToken) {
        try {
            return remoteGroupDataSource.connectToGroup(userToken, groupName, groupToken);
        } catch (Exception e) {
            return new DataResponse<>(null, "Подключение к группе не удалось");
        }
    }

    public void removeGroup() {
        groupDataSource.removeGroup();
    }

    public void changeSchedule(String userToken, String schedule) {
        remoteGroupDataSource.changeSchedule(userToken, schedule);
    }

    public void kickMember(String userToken, String kickUserUuid) {
        remoteGroupDataSource.kickMember(userToken, kickUserUuid);
    }

    public DataResponse<Group> getLocalGroup() {
        return groupDataSource.getData();
    }
}
