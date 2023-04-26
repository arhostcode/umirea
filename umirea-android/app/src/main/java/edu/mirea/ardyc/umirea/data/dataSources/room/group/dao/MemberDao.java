package edu.mirea.ardyc.umirea.data.dataSources.room.group.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.LessonEntity;
import edu.mirea.ardyc.umirea.data.dataSources.room.group.entities.MemberEntity;
import edu.mirea.ardyc.umirea.data.model.group.Member;

@Dao
public abstract class MemberDao {

    @Query("SELECT * FROM group_members")
    public abstract List<MemberEntity> getMembers();

    @Insert
    public abstract void insert(MemberEntity member);

    @Transaction
    public void insert(Member member) {
        insert(MemberEntity.fromMember(member));
    }

    @Transaction
    public void updateAll(List<Member> members) {
        clear();
        members.forEach(this::insert);
    }

    @Query("DELETE FROM group_members")
    public abstract void clear();
}
