package edu.mirea.ardyc.umirea.data.repository.impl.group;

import android.content.Context;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public abstract class GroupRepository extends Repository<Group> {

    public GroupRepository(Context context) {
        super(context);
    }
}
