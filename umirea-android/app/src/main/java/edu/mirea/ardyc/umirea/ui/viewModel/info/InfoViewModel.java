package edu.mirea.ardyc.umirea.ui.viewModel.info;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;

@HiltViewModel
public class InfoViewModel extends AndroidViewModel {

    private MutableLiveData<List<InfoMessage>> infoMessages;

    @Inject
    public InfoViewModel(@NonNull Application application, InfoService infoService) {
        super(application);
    }


    public MutableLiveData<List<InfoMessage>> getInfoMessages() {
        return infoMessages;
    }

    public void setInfoMessages(MutableLiveData<List<InfoMessage>> infoMessages) {
        this.infoMessages = infoMessages;
    }
}