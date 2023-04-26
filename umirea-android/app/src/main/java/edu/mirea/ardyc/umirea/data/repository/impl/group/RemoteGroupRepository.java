package edu.mirea.ardyc.umirea.data.repository.impl.group;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.group.Member;

public class RemoteGroupRepository extends GroupRepository {

    public RemoteGroupRepository(Context context) {
        super(context);
    }

    @Override
    public Group getData() {
        ArrayList<Member> members = new ArrayList<>();
        members.add(new Member("Дмитрий", "Кашеваров", "Студент", "ID1"));
        members.add(new Member("Евгений", "Петров", "Староста", "ID2"));
        return new Group(members, "ИКБО-04-22", "ofosshjekxhdhfkf", "ИКБО-04-22");
    }
}
