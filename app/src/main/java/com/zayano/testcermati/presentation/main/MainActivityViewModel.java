package com.zayano.testcermati.presentation.main;

import com.zayano.testcermati.data.GithubServiceRepository;
import com.zayano.testcermati.utils.Response;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel implementation for MainActivity
 *
 * @author Vyacheslav Osipov
 * @see ViewModel
 */
public class MainActivityViewModel extends ViewModel {
    private GithubServiceRepository repository;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Response> responseData = new MutableLiveData<>();

    private String searchQuery;

    public MainActivityViewModel(GithubServiceRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<Response> getResponseData() {
        return responseData;
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    public void searchUser(String searchQuery) {
        this.searchQuery = searchQuery;

        disposable.add(repository.searchUser(searchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> responseData.setValue(Response.loading()))
                .subscribe(
                        response -> responseData.setValue(Response.success(response)),
                        throwable -> responseData.setValue(Response.error(throwable))
                )
        );
    }

    public void loadNextPageUserData(int page) {
        if (searchQuery !=  null) {
            disposable.add(repository.loadNextPageUserData(searchQuery, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response -> responseData.setValue(Response.update(response)),
                            throwable -> responseData.setValue(Response.error(throwable))
                    )
            );
        }
    }
}
