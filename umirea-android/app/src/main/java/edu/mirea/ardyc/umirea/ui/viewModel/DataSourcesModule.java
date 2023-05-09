package edu.mirea.ardyc.umirea.ui.viewModel;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.mirea.ardyc.umirea.data.dataSources.chat.ChatDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.chat.RemoteChatDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.cloud.CloudDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.cloud.RemoteCloudDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.dashboard.DashboardDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.dashboard.RemoteDashboardDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.group.GroupDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.group.RemoteGroupDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.info.InfoDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.info.RemoteInfoDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.user.RemoteUserDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.user.UserDataSource;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableMonth;
import edu.mirea.ardyc.umirea.data.net.auth.AuthRemoteService;
import edu.mirea.ardyc.umirea.data.net.chat.ChatRemoteService;
import edu.mirea.ardyc.umirea.data.net.cloud.CloudRemoteService;
import edu.mirea.ardyc.umirea.data.net.dashboard.DashboardRemoteService;
import edu.mirea.ardyc.umirea.data.net.group.GroupRemoteService;
import edu.mirea.ardyc.umirea.data.net.info.InfoRemoteService;


@Module
@InstallIn(SingletonComponent.class)
public class DataSourcesModule {

    @Singleton
    @Provides
    public GroupDataSource groupDataSource(@ApplicationContext Context context) {
        return new GroupDataSource(context);
    }

    @Singleton
    @Provides
    public RemoteGroupDataSource groupRemoteDataSource(@ApplicationContext Context context, GroupRemoteService groupRemoteService) {
        return new RemoteGroupDataSource(context, groupRemoteService);
    }

    @Singleton
    @Provides
    public CloudDataSource cloudDataSource(@ApplicationContext Context context) {
        return new CloudDataSource(context);
    }

    @Singleton
    @Provides
    public RemoteCloudDataSource remoteCloudDataSource(@ApplicationContext Context context, CloudRemoteService cloudRemoteService) {
        return new RemoteCloudDataSource(context, cloudRemoteService);
    }

    @Singleton
    @Provides
    public UserDataSource userDataSource(@ApplicationContext Context context) {
        return new UserDataSource(context);
    }

    @Singleton
    @Provides
    public RemoteUserDataSource remoteUserDataSource(@ApplicationContext Context context, AuthRemoteService authRemoteService) {
        return new RemoteUserDataSource(context, authRemoteService);
    }

    @Singleton
    @Provides
    public ChatDataSource chatDataSource(@ApplicationContext Context context) {
        return new ChatDataSource(context);
    }

    @Singleton
    @Provides
    public RemoteChatDataSource remoteChatDataSource(@ApplicationContext Context context, ChatRemoteService chatRemoteService) {
        return new RemoteChatDataSource(context, chatRemoteService);
    }

    @Singleton
    @Provides
    public InfoDataSource infoDataSource(@ApplicationContext Context context) {
        return new InfoDataSource(context);
    }

    @Singleton
    @Provides
    public RemoteInfoDataSource remoteInfoDataSource(@ApplicationContext Context context, InfoRemoteService infoRemoteService) {
        return new RemoteInfoDataSource(context, infoRemoteService);
    }


    @Singleton
    @Provides
    public DashboardDataSource dashboardDataSource(@ApplicationContext Context context) {
        return new DashboardDataSource(context);
    }

    @Singleton
    @Provides
    public RemoteDashboardDataSource remoteDashboardDataSource(@ApplicationContext Context context, DashboardRemoteService dashboardRemoteService) {
        return new RemoteDashboardDataSource(context, dashboardRemoteService);
    }

}
