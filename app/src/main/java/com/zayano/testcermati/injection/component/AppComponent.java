package com.zayano.testcermati.injection.component;

import android.app.Application;

import com.zayano.testcermati.ZayanoApp;
import com.zayano.testcermati.injection.builder.ActivityBuilder;
import com.zayano.testcermati.injection.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(ZayanoApp app);
}
