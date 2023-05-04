package edu.mirea.ardyc.umirea.ui.viewModel;

import android.content.Context;

import javax.inject.Singleton;

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
import edu.mirea.ardyc.umirea.data.net.cloud.CloudRemoteService;
import edu.mirea.ardyc.umirea.data.net.group.GroupRemoteService;


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

}
