package edu.mirea.ardyc.umirea.ui.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalDateTime;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.group.Member;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.LessonTime;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableMonth;
import edu.mirea.ardyc.umirea.databinding.FragmentHomeBinding;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private AppSharedViewModel appSharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initListeners();
        initTimetable();
        initChat();
        initInfo();
        initCloud();
        return root;
    }

    private void initListeners(){
        binding.allLessons.setOnClickListener((v) -> {
            NavHostFragment.findNavController(this).navigate(R.id.navigation_dashboard, null, null);
        });

        binding.allFiles.setOnClickListener((v) -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_navigation_cloud, null, null);
        });

        binding.allNews.setOnClickListener((v) -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_navigation_info, null, null);
        });
    }

    private void initTimetable(){
        appSharedViewModel.getTimetableMutableLiveData().observe(getViewLifecycleOwner(), timetable -> {
            LocalDateTime time = LocalDateTime.now();
            TimetableMonth month = timetable.getForMonthYear(time.getMonthValue(), time.getYear());
            int lessonByTime = LessonTime.getLessonByTime(time.getHour(), time.getMinute());
            if (lessonByTime == -1)
                return;
            if (month != null) {
                TimetableDay day = month.getDay(time.getDayOfMonth());
                if (day != null) {
                    for (Lesson l : day.getLessons()) {
                        if (l.getLessonTime() == lessonByTime) {
                            updateCurrentLesson(l);
                        }

                        if (l.getLessonTime() == lessonByTime + 1) {
                            updateNextLesson(l);
                        }
                    }
                }
            }
        });
    }

    private void initChat() {
        appSharedViewModel.getChatMutableLiveData().observe(getViewLifecycleOwner(), chat -> {
            Group group = appSharedViewModel.getGroupMutableLiveData().getValue();
            if (chat.getChatMessages().size() == 0)
                return;
            if (group != null) {
                ChatMessage message = chat.getChatMessages().get(chat.getChatMessages().size() - 1);
                Member member = group.getById(message.getOwnerUuid());
                if (member == null) {
                    binding.messageName.setText("Пользователь Удалён");
                } else {
                    binding.messageName.setText(String.format(getResources().getString(R.string.user_full_name), member.getFirstName(), member.getLastName()));
                }
                binding.message.setText(message.getMessage());
            }

        });
    }

    private void initInfo() {
        appSharedViewModel.getListInfoMutableLiveData().observe(getViewLifecycleOwner(), infoMessages -> {
            Group group = appSharedViewModel.getGroupMutableLiveData().getValue();
            if (infoMessages == null || infoMessages.size() == 0)
                return;
            if (group != null) {
                InfoMessage message = infoMessages.get(infoMessages.size() - 1);
                Member member = group.getById(message.getOwner());
                if (member == null) {
                    binding.infoOwner.setText("Пользователь Удалён");
                } else {
                    binding.infoOwner.setText(String.format(getResources().getString(R.string.user_full_name), member.getFirstName(), member.getLastName()));
                }
                binding.infoTitle.setText(message.getName());
                binding.infoText.setText(message.getMessage());
            }

        });
    }

    private void initCloud() {
        appSharedViewModel.getCloudFolderMutableLiveData().observe(getViewLifecycleOwner(), cloudFolders -> {
            if (cloudFolders == null || cloudFolders.isEmpty())
                return;
            CloudFolder folder = cloudFolders.get(cloudFolders.size() - 1);
            if (folder.getFiles() == null || folder.getFiles().isEmpty())
                return;
            CloudFile file = folder.getFiles().get(folder.getFiles().size() - 1);
            if (file == null)
                return;
            binding.lastFile.setText(file.getName());
        });
    }

    private void updateCurrentLesson(Lesson l) {
        binding.currentLessonName.setText(l.getName() + " " + l.getRoom());
        binding.currentLessonHw.setImageDrawable(l.getHomeWork() == null ? null : ResourcesCompat.getDrawable(getResources(), R.drawable.hw_icon, null));
        binding.currentLessonNote.setImageDrawable(l.getTask() == null ? null : ResourcesCompat.getDrawable(getResources(), R.drawable.note_icon, null));
        binding.currentLessonType.setImageDrawable(ResourcesCompat.getDrawable(getResources(), l.getLessonIcon(), null));
    }

    private void updateNextLesson(Lesson l) {
        binding.nextLessonName.setText(l.getName() + " " + l.getRoom());
        binding.nextLessonHw.setImageDrawable(l.getHomeWork() == null ? null : ResourcesCompat.getDrawable(getResources(), R.drawable.hw_icon, null));
        binding.nextLessonNote.setImageDrawable(l.getTask() == null ? null : ResourcesCompat.getDrawable(getResources(), R.drawable.note_icon, null));
        binding.nextLessonType.setImageDrawable(ResourcesCompat.getDrawable(getResources(), l.getLessonIcon(), null));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}