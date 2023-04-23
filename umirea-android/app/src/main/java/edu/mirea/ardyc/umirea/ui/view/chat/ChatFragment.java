package edu.mirea.ardyc.umirea.ui.view.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.mirea.ardyc.umirea.data.model.chat.Chat;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.databinding.FragmentChatBinding;
import edu.mirea.ardyc.umirea.ui.view.chat.adapters.ChatMessageAdapter;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.chat.ChatViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupProcessor;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private ChatViewModel chatViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel =
                new ViewModelProvider(requireActivity()).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);

        AppSharedViewModel appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);
        binding.chat.setLayoutManager(layoutManager);
        binding.chat.setAdapter(new ChatMessageAdapter(new Chat(), new Group()));
        appSharedViewModel.getChatMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            ((ChatMessageAdapter) binding.chat.getAdapter()).update(val);
        });

        appSharedViewModel.getGroupMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            ((ChatMessageAdapter) binding.chat.getAdapter()).update(val);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}