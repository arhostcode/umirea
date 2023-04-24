package edu.mirea.ardyc.umirea.data.repository.impl.group;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.stream.Collectors;

import edu.mirea.ardyc.umirea.data.dataSources.room.UmireaDatabase;
import edu.mirea.ardyc.umirea.data.dataSources.room.group.entities.MemberEntity;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.group.Member;
import edu.mirea.ardyc.umirea.data.repository.LocalRepository;

public class LocalGroupRepository extends LocalRepository<Group> {
    public LocalGroupRepository(Context context) {
        super(context);
    }

    @Override
    public MutableLiveData<Group> getData() {
        MutableLiveData<Group> data = new MutableLiveData<>();
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            String groupName = context.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("group_name", null);
            String groupToken = context.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("group_token", null);
            String groupSchedule = context.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("group_baseschedule", null);
            List<Member> members = UmireaDatabase.getDatabase(context).memberDao().getMembers().stream().map(MemberEntity::toMember).collect(Collectors.toList());
            data.postValue(new Group(members, groupName, groupToken, groupSchedule));
        });
        return data;
    }

    public void getDataAndPerform(GroupAction action) {
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            String groupName = context.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("group_name", null);
            String groupToken = context.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("group_token", null);
            String groupSchedule = context.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("group_baseschedule", null);
            List<Member> members = UmireaDatabase.getDatabase(context).memberDao().getMembers().stream().map(MemberEntity::toMember).collect(Collectors.toList());
            action.perform(new Group(members, groupName, groupToken, groupSchedule));
        });
    }

    public void updateGroup(Group group) {
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            context.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).edit().putString("group_name", group.getName()).putString("group_token", group.getToken()).putString("group_baseschedule", group.getBaseSchedule()).apply();
            UmireaDatabase.getDatabase(context).memberDao().updateAll(group.getMembers());
        });
    }

    public void updateGroupSchedule(String groupSchedule) {
        context.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).edit().putString("group_baseschedule", groupSchedule).apply();
    }

    public void updateMembers(List<Member> members) {
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            UmireaDatabase.getDatabase(context).memberDao().updateAll(members);
        });
    }
}
