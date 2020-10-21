package com.zayano.testcermati.presentation.main;

import com.zayano.testcermati.ViewModelProviderFactory;
import com.zayano.testcermati.data.GithubServiceRepository;

import androidx.lifecycle.ViewModelProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Dependency injection module for MainActivity
 *
 * @author Osipov Vyacheslav
 * @see MainActivity
 * @see MainActivityViewModel
 * @see GithubServiceRepository
 */
@Module
public class MainActivityModule {

    @Provides
    MainActivityViewModel provideMainActivityViewModel(GithubServiceRepository repository) {
        return new MainActivityViewModel(repository);
    }

    @Provides
    ViewModelProvider.Factory provideMainActivityViewModelProvider(MainActivityViewModel mainActivityViewModel) {
        return new ViewModelProviderFactory<>(mainActivityViewModel);
    }

}
