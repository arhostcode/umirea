package edu.mirea.ardyc.umirea.ui.viewModel.info;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.repository.impl.info.InfoRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.view.info.dialogs.InfoContent;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;

@HiltViewModel
public class InfoViewModel extends AndroidViewModel {

    private InfoRepository infoRepository;
    private MutableLiveData<List<InfoMessage>> info = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public InfoViewModel(@NonNull Application application, InfoRepository infoRepository) {
        super(application);
        this.infoRepository = infoRepository;
    }

    public void createInfo(InfoContent infoContent) {
        AppViewModel.executorService.execute(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            DataResponse<InfoMessage> dataResponse = infoRepository.create(infoContent.getTitle(), infoContent.getText(), userToken);
            if (dataResponse.isError()) {
                error.postValue(dataResponse.getMessage());
            }
            DataResponse<List<InfoMessage>> infos = infoRepository.loadInfo(userToken);
            if (!infos.isError()) {
                List<InfoMessage> infoMessages = info.getValue();
                infoMessages.addAll(infos.getData());
                info.postValue(infoMessages);
            }
        });
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void setInfo(MutableLiveData<List<InfoMessage>> info) {
        this.info = info;
    }
}