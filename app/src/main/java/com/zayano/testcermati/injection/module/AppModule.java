package com.zayano.testcermati.injection.module;

import android.app.Application;
import android.content.Context;

import com.zayano.testcermati.data.remote.GithubService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    GithubService provideGithubService() {
        return GithubService.Builder.newGithubService();
    }
}
