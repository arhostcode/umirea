package edu.mirea.ardyc.umirea.ui.view.auth.ui.hello;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentHelloBinding;
import edu.mirea.ardyc.umirea.ui.viewModel.auth.HelloViewModel;

public class HelloFragment extends Fragment {

    private HelloViewModel viewModel;
    private FragmentHelloBinding fragmentHelloBinding;
    private MutableLiveData<Boolean> isInitialized = new MutableLiveData<>(false);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HelloViewModel.class);
        fragmentHelloBinding = FragmentHelloBinding.inflate(getLayoutInflater(), container, false);
        isInitialized.observe(getViewLifecycleOwner(), (val) -> {
            if (val)
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_hello_to_navigation_authorization);
        });
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isInitialized.postValue(true);
        }).start();
        return fragmentHelloBinding.getRoot();

    }


}