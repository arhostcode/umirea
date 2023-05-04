package edu.mirea.ardyc.umirea.ui.viewModel.info;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.repository.impl.info.InfoRemoteRepository;

public class InfoService {

    private InfoRemoteRepository infoRemoteRepository;

    public InfoService(Context context) {
        infoRemoteRepository = new InfoRemoteRepository(context);
    }

    public MutableLiveData<List<InfoMessage>> getMutableInfoData() {
        MutableLiveData<List<InfoMessage>> mutableLiveData = new MutableLiveData<>();

        new Thread(() -> mutableLiveData.postValue(infoRemoteRepository.getData())).start();
        return mutableLiveData;
    }

    public List<InfoMessage> getInfoData() {
        return infoRemoteRepository.getData();
    }
}
