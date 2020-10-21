package com.zayano.testcermati.data.remote;

import com.zayano.testcermati.data.model.GithubResponse;
import com.zayano.testcermati.data.model.UserProfile;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface GithubService {

    String ENDPOINT = "https://api.github.com";

    @GET("/users/{username}")
    Observable<UserProfile> getUser(@Path("username") String username);

    @GET("/search/users")
    Observable<GithubResponse> searchUsers(@Query("q") String searchQuery,
                                           @Query("page") int page,
                                           @Query("per_page") int per_page);


    class Builder {
        public static GithubService newGithubService() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GithubService.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            return retrofit.create(GithubService.class);
        }
    }
}
