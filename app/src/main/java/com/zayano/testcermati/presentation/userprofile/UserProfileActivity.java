package com.zayano.testcermati.presentation.userprofile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zayano.testcermati.R;
import com.zayano.testcermati.data.model.UserProfile;
import com.zayano.testcermati.utils.ImageLoader;
import com.zayano.testcermati.utils.Response;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;
import retrofit2.HttpException;

import static com.zayano.testcermati.utils.Response.ERROR;
import static com.zayano.testcermati.utils.Response.LOADING;
import static com.zayano.testcermati.utils.Response.SUCCESS;


public class UserProfileActivity extends AppCompatActivity {
    public static final String KEY_USERNAME = "key_username";

    private ConstraintLayout layout;
    private ImageView ivUser;
    private TextView tvUsername;
    private TextView tvUserId;
    private TextView tvRepos;
    private TextView tvFollowers;
    private TextView tvFollowing;
    private ProgressBar progressBar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    UserProfileActivityViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProfileActivityViewModel.class);
        viewModel.getResponseData().observe(this, this::fetchResponse);

        String username = getIntent().getStringExtra(KEY_USERNAME);
        getUserProfile(username);

        layoutSetup();
    }

    private void getUserProfile(String username) {
        viewModel.getUserProfile(username);
    }

    private void layoutSetup() {
        layout = findViewById(R.id.constraint_layout);
        ivUser = findViewById(R.id.iv_user);
        tvUsername = findViewById(R.id.tv_user_name);
        tvUserId = findViewById(R.id.tv_user_id);
        tvRepos = findViewById(R.id.tv_repos);
        tvFollowers = findViewById(R.id.tv_followers);
        tvFollowing = findViewById(R.id.tv_following);
        progressBar = findViewById(R.id.progress_bar);
    }


    private void fetchResponse(Response response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState((UserProfile) response.data);
                break;

            case ERROR:
                renderErrorState(response.error);
                break;
        }
    }

    private void renderLoadingState() {
        layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void renderDataState(UserProfile userProfile) {
        ImageLoader.setImageCircleUrl(ivUser, userProfile.getAvatarUrl());
        tvUsername.setText(userProfile.getLogin());
        tvUserId.setText("ID:  " + userProfile.getId());
        tvRepos.setText(String.valueOf(userProfile.getPublicRepos()));
        tvFollowers.setText(String.valueOf(userProfile.getFollowers()));
        tvFollowing.setText(String.valueOf(userProfile.getFollowing()));

        progressBar.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    private void renderErrorState(Throwable error) {
        progressBar.setVisibility(View.GONE);
        if (error instanceof HttpException) {
            Toast.makeText(this,
                    getResources().getString(R.string.connection_error),
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(
                    this,
                    getResources().getString(R.string.no_connection_error),
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


}
