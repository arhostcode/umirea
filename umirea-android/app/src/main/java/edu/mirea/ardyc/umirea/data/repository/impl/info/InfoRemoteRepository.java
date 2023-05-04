package edu.mirea.ardyc.umirea.data.repository.impl.info;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public class InfoRemoteRepository extends Repository{
    public InfoRemoteRepository(Context context) {
        super(context);
    }

    public List<InfoMessage> getData() {
        List<InfoMessage> messages = new ArrayList<>();
        messages.add(new InfoMessage("M1", "Как вызвать сатану", "Вызвать сатану очень просто нужно всего лишь убить его", "ID1"));
        messages.add(new InfoMessage("M1", "Как вызвать сатану", "Вызвать сатану очень просто нужно всего лишь убить его", "ID2"));
        return messages;
    }
}
