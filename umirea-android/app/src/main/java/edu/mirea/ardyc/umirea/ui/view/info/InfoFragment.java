package edu.mirea.ardyc.umirea.ui.view.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.hilt.android.AndroidEntryPoint;
import edu.mirea.ardyc.umirea.databinding.FragmentInfoBinding;
import edu.mirea.ardyc.umirea.ui.view.info.adapters.InfoMessagesAdapter;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.info.InfoViewModel;

@AndroidEntryPoint
public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    private InfoMessagesAdapter infoMessagesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InfoViewModel infoViewModel =
                new ViewModelProvider(requireActivity()).get(InfoViewModel.class);
        AppSharedViewModel appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        infoMessagesAdapter = new InfoMessagesAdapter(null);
        binding.infoMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.infoMessages.setAdapter(infoMessagesAdapter);
        appSharedViewModel.getListInfoMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            System.out.println(val + "  asd as sddddddddddddddddddddddddd]");
            infoMessagesAdapter.update(val);
        });

        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}