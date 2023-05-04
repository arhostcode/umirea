package edu.mirea.ardyc.umirea.ui.viewModel;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.mirea.ardyc.umirea.data.net.cloud.CloudRemoteService;
import edu.mirea.ardyc.umirea.data.net.auth.AuthRemoteService;
import edu.mirea.ardyc.umirea.data.net.group.GroupRemoteService;
import edu.mirea.ardyc.umirea.data.repository.impl.auth.UserRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.group.GroupRepository;
import edu.mirea.ardyc.umirea.ui.viewModel.auth.UserService;
import edu.mirea.ardyc.umirea.ui.viewModel.cloud.CloudService;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardService;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupService;
import edu.mirea.ardyc.umirea.ui.viewModel.info.InfoService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public final class ServicesModule {

    @Singleton
    @Provides
    public InfoService infoService(@ApplicationContext Context context) {
        return new InfoService(context);
    }

    @Singleton
    @Provides
    public DashboardService dashboardService(@ApplicationContext Context context) {
        return new DashboardService(context);
    }


    @Singleton
    @Provides
    public UserService userService(@ApplicationContext Context context, UserRepository authRepository) {
        return new UserService(context, authRepository);
    }

    @Singleton
    @Provides
    public GroupService groupService(@ApplicationContext Context context, GroupRepository groupRepository) {
        return new GroupService(context, groupRepository);
    }

    @Singleton
    @Provides
    public CloudService cloudService(@ApplicationContext Context context, CloudRepository remoteCloudRepository) {
        return new CloudService(context, remoteCloudRepository);
    }


    @Singleton
    @Provides
    public AuthRemoteService authRemoteService(@ApplicationContext Context context) {
        String authServer = "http://84.252.128.74:8081";
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(authServer)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(AuthRemoteService.class);
    }

    @Singleton
    @Provides
    public GroupRemoteService groupRemoteService(@ApplicationContext Context context) {
        String authServer = "http://84.252.128.74:8084";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(authServer)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GroupRemoteService.class);
    }

    @Singleton
    @Provides
    public CloudRemoteService cloudRemoteService(@ApplicationContext Context context) {
        String authServer = "http://10.0.2.2:8086";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(authServer)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(CloudRemoteService.class);
    }
}
