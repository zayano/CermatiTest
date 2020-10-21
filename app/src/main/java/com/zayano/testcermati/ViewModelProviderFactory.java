package com.zayano.testcermati;

import org.jetbrains.annotations.NotNull;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelProviderFactory<V> implements ViewModelProvider.Factory {

    private final static String UNKNOWN_CLASS = "Unknown class name";

    private V viewModel;

    public ViewModelProviderFactory(V viewModel) {
        this.viewModel = viewModel;
    }


    @NotNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(viewModel.getClass())) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException(UNKNOWN_CLASS);
    }
}
