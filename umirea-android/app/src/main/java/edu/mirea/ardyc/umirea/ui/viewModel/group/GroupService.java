package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.content.Context;


import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.impl.group.GroupRepository;

public class GroupService {

    private Context context;
    private GroupRepository groupRepository;

    public GroupService(Context context, GroupRepository groupRepository) {
        this.context = context;
        this.groupRepository = groupRepository;
    }

    public DataResponse<Group> createGroup(String userToken, String groupTimetable, String groupName) {
        try {
            return groupRepository.createGroup(userToken, groupTimetable, groupName);
        } catch (Exception e) {
            return new DataResponse<>(null, "Создание группы не удалось");
        }
    }

    public DataResponse<Group> loadRemoteGroup(String userToken) {
        return groupRepository.getRemoteGroup(userToken);
    }

    public DataResponse<Group> loadLocalGroup() {
        return groupRepository.getLocalGroup();
    }

    public void saveGroup(Group group) {
        groupRepository.saveGroup(group);
    }

    public DataResponse<Group> connectToGroup(String userToken, String groupName, String groupToken) {
        try {
            return groupRepository.connectToGroup(userToken, groupName, groupToken);
        } catch (Exception e) {
            return new DataResponse<>(null, "Подключение к группе не удалось");
        }
    }

    public void removeGroup() {
        groupRepository.removeGroup();
    }

    public DataResponse<Group> changeSchedule(String schedule, String userToken, Group currentGroup) {
        currentGroup.setBaseSchedule(schedule);
        groupRepository.changeSchedule(userToken, schedule);
        return new DataResponse<>(currentGroup, null);
    }

    public void kickMember(String userToken, String kickUserUuid) {
        groupRepository.kickMember(userToken, kickUserUuid);
    }
}
