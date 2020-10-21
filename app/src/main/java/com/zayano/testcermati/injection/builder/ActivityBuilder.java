package com.zayano.testcermati.injection.builder;


import com.zayano.testcermati.presentation.main.MainActivity;
import com.zayano.testcermati.presentation.main.MainActivityModule;
import com.zayano.testcermati.presentation.userprofile.UserProfileActivity;
import com.zayano.testcermati.presentation.userprofile.UserProfileActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {UserProfileActivityModule.class})
    abstract UserProfileActivity bindUserProfileActivity();
}
