package com.zayano.testcermati.presentation.userprofile;

import com.zayano.testcermati.data.GithubServiceRepository;
import com.zayano.testcermati.utils.Response;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserProfileActivityViewModel extends ViewModel {

    private GithubServiceRepository repository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Response> responseData = new MutableLiveData<>();

    public UserProfileActivityViewModel(GithubServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    public MutableLiveData<Response> getResponseData() {
        return responseData;
    }

    public void getUserProfile(String username) {
        disposable.add(repository.getUserProfile(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> responseData.setValue(Response.loading()))
                .subscribe(
                        userProfile -> responseData.setValue(Response.success(userProfile)),
                        throwable -> responseData.setValue(Response.error(throwable))
                )
        );
    }
}
