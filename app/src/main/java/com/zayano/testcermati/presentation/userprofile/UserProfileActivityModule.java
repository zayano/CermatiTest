package com.zayano.testcermati.presentation.userprofile;


import com.zayano.testcermati.ViewModelProviderFactory;
import com.zayano.testcermati.data.GithubServiceRepository;

import androidx.lifecycle.ViewModelProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class UserProfileActivityModule {

    @Provides
    UserProfileActivityViewModel provideUserProfileActivityViewModel(GithubServiceRepository repository) {
        return new UserProfileActivityViewModel(repository);
    }

    @Provides
    ViewModelProvider.Factory provideUserProfileActivityViewModelProvider(UserProfileActivityViewModel userProfileActivityViewModel) {
        return new ViewModelProviderFactory<>(userProfileActivityViewModel);
    }
}
