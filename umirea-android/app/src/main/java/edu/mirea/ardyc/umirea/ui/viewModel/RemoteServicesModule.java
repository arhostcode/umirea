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

    private static final String serverUrl = "https://umirea.ardyc.online/";

    @Singleton
    @Provides
    public AuthRemoteService authRemoteService() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS).callTimeout(1, TimeUnit.SECONDS).readTimeout(1, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(AuthRemoteService.class);
    }

    @Singleton
    @Provides
    public GroupRemoteService groupRemoteService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS).followRedirects(true).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GroupRemoteService.class);
    }

    @Singleton
    @Provides
    public CloudRemoteService cloudRemoteService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS).followRedirects(true).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(CloudRemoteService.class);
    }

    @Singleton
    @Provides
    public ChatRemoteService chatRemoteService() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ChatRemoteService.class);
    }

    @Singleton
    @Provides
    public InfoRemoteService infoRemoteService() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(InfoRemoteService.class);
    }


    @Singleton
    @Provides
    public DashboardRemoteService dashboardRemoteService() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(DashboardRemoteService.class);
    }

}
