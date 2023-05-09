package edu.mirea.ardyc.umirea.ui.viewModel.account;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.dataSources.room.UmireaDatabase;
import edu.mirea.ardyc.umirea.data.repository.impl.auth.UserRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.chat.ChatRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.group.GroupRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.info.InfoRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.DashboardRepository;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;

@HiltViewModel
public class AccountViewModel extends ViewModel {

    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private DashboardRepository dashboardRepository;
    private InfoRepository infoRepository;
    private ChatRepository chatRepository;

    private CloudRepository cloudRepository;

    @Inject
    public AccountViewModel(ChatRepository chatRepository, UserRepository userRepository, GroupRepository groupRepository, DashboardRepository dashboardRepository, InfoRepository infoRepository, CloudRepository cloudRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.dashboardRepository = dashboardRepository;
        this.chatRepository = chatRepository;
        this.infoRepository = infoRepository;
        this.cloudRepository = cloudRepository;
    }

    public void removeUserData() {
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            chatRepository.clear();
        });
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            userRepository.removeUser();
        });
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            groupRepository.removeGroup();
        });
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            dashboardRepository.removeDashboard();
        });

        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            infoRepository.clear();
        });
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            cloudRepository.clear();
        });
    }

}