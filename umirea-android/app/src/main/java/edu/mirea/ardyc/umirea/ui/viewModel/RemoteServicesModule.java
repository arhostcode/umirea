package edu.mirea.ardyc.umirea.ui.viewModel;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.mirea.ardyc.umirea.data.net.auth.AuthRemoteService;
import edu.mirea.ardyc.umirea.data.net.chat.ChatRemoteService;
import edu.mirea.ardyc.umirea.data.net.cloud.CloudRemoteService;
import edu.mirea.ardyc.umirea.data.net.dashboard.DashboardRemoteService;
import edu.mirea.ardyc.umirea.data.net.group.GroupRemoteService;
import edu.mirea.ardyc.umirea.data.net.info.InfoRemoteService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RemoteServicesModule {

    @Singleton
    @Provides
    public AuthRemoteService authRemoteService() {
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
    public GroupRemoteService groupRemoteService() {
        String authServer = "http://84.252.128.74:8084";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(authServer)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GroupRemoteService.class);
    }

    @Singleton
    @Provides
    public CloudRemoteService cloudRemoteService() {
        String authServer = "http://84.252.128.74:8086";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(authServer)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(CloudRemoteService.class);
    }

    @Singleton
    @Provides
    public ChatRemoteService chatRemoteService() {
        String chatServer = "http://84.252.128.74:8087";
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(chatServer)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ChatRemoteService.class);
    }

    @Singleton
    @Provides
    public InfoRemoteService infoRemoteService() {
        String chatServer = "http://84.252.128.74:8087";
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(chatServer)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(InfoRemoteService.class);
    }


    @Singleton
    @Provides
    public DashboardRemoteService dashboardRemoteService() {
        String chatServer = "http://84.252.128.74:8089";
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(chatServer)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(DashboardRemoteService.class);
    }

}
