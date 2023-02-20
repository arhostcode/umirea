package edu.mirea.ardyc.umirea.ui.view.cloud;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CloudViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CloudViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}