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
import es.dmoral.toasty.Toasty;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private ChatViewModel chatViewModel;
    private AppSharedViewModel appSharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel =
                new ViewModelProvider(requireActivity()).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);

        appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);
        binding.chat.setLayoutManager(layoutManager);
        binding.chat.setAdapter(new ChatMessageAdapter(new Chat(), new Group()));
        binding.send.setOnClickListener((view) -> {
            chatViewModel.sendMessage(binding.message.getText().toString());
            binding.message.setText("");
        });
        initObservers();

        return root;
    }

    private void initObservers() {
        appSharedViewModel.getChatMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            ((ChatMessageAdapter) binding.chat.getAdapter()).update(val);
            if (!binding.chat.canScrollVertically(1))
                binding.chat.scrollToPosition(binding.chat.getAdapter().getItemCount() - 1);
        });

        appSharedViewModel.getGroupMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            ((ChatMessageAdapter) binding.chat.getAdapter()).update(val);
        });

        chatViewModel.getMessage().observe(getViewLifecycleOwner(), (val) -> {
            if(val == null || val.isEmpty())
                return;
            Toasty.info(requireContext(), val).show();
            chatViewModel.getMessage().postValue("");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}