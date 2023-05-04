package edu.mirea.ardyc.umirea.ui.viewModel;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.mirea.ardyc.umirea.data.dataSources.cloud.CloudDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.cloud.RemoteCloudDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.group.GroupDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.group.RemoteGroupDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.user.RemoteUserDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.user.UserDataSource;
import edu.mirea.ardyc.umirea.data.net.auth.AuthRemoteService;
import edu.mirea.ardyc.umirea.data.repository.impl.auth.UserRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.group.GroupRepository;


@Module
@InstallIn(SingletonComponent.class)
public class RepositoriesModule {

    @Provides
    public UserRepository authRepository(@ApplicationContext Context context, UserDataSource userDataSource, RemoteUserDataSource remoteUserDataSource) {
        return new UserRepository(context, userDataSource, remoteUserDataSource);
    }

    @Provides
    public GroupRepository groupRepository(@ApplicationContext Context context, GroupDataSource groupDataSource, RemoteGroupDataSource remoteGroupDataSource) {
        return new GroupRepository(context, groupDataSource, remoteGroupDataSource);
    }

    @Provides
    public CloudRepository cloudRepository(@ApplicationContext Context context, CloudDataSource cloudDataSource, RemoteCloudDataSource remoteCloudDataSource) {
        return new CloudRepository(context, cloudDataSource, remoteCloudDataSource);
    }


}
