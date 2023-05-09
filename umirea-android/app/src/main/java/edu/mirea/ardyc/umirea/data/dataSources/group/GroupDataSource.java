package edu.mirea.ardyc.umirea.data.dataSources.group;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.dataSources.room.UmireaDatabase;
import edu.mirea.ardyc.umirea.data.dataSources.room.group.entities.MemberEntity;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.group.Member;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;

public class GroupDataSource extends DataSource {

    public GroupDataSource(Context context) {
        super(context);
    }

    public DataResponse<Group> getData() {
        String groupName = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("group_name", null);
        String groupToken = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("group_token", null);
        String groupSchedule = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("group_baseSchedule", null);
        String groupUUID = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("group_uuid", null);
        List<Member> members = UmireaDatabase.getDatabase(context).memberDao().getMembers().stream().map(MemberEntity::toMember).collect(Collectors.toList());
        return new DataResponse<>(new Group(members, groupUUID, groupName, groupToken, groupSchedule));
    }

    public void updateGroupSchedule(String groupSchedule) {
        context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit().putString("group_baseschedule", groupSchedule).apply();
    }

    public void updateMembers(List<Member> members) {
        UmireaDatabase.getDatabase(context).memberDao().updateAll(members);
    }

    public void saveGroup(Group group) {
        updateMembers(group.getMembers());
        context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit()
                .putString("group_name", group.getName())
                .putString("group_token", group.getToken())
                .putString("group_uuid", group.getUuid())
                .putString("group_baseSchedule", group.getBaseSchedule())
                .apply();
    }

    public void removeGroup() {
        context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit()
                .remove("group_name")
                .remove("group_token")
                .remove("group_uuid")
                .remove("group_baseSchedule")
                .apply();
        UmireaDatabase.getDatabase(context).memberDao().clear();
    }
}
