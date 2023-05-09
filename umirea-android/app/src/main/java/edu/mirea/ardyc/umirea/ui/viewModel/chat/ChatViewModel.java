package edu.mirea.ardyc.umirea.ui.viewModel.chat;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;
import edu.mirea.ardyc.umirea.data.repository.impl.chat.ChatRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;

@HiltViewModel
public class ChatViewModel extends AndroidViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private ChatRepository chatRepository;

    @Inject
    public ChatViewModel(Application application, ChatRepository chatRepository) {
        super(application);
        this.chatRepository = chatRepository;
    }

    public void sendMessage(String message) {
        AppViewModel.executorService.execute(() -> {
            String token = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            DataResponse<ChatMessage> response = chatRepository.sendMessage(message, token);
            if (response.isError()) {
                this.message.postValue(response.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }
}