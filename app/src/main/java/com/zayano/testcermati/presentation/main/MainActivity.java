package com.zayano.testcermati.presentation.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.android.AndroidInjection;
import retrofit2.HttpException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.zayano.testcermati.R;
import com.zayano.testcermati.data.model.GithubResponse;
import com.zayano.testcermati.presentation.userprofile.UserProfileActivity;
import com.zayano.testcermati.utils.RecyclerViewScrollListener;
import com.zayano.testcermati.utils.Response;

import javax.inject.Inject;

import static com.zayano.testcermati.utils.Response.ERROR;
import static com.zayano.testcermati.utils.Response.LOADING;
import static com.zayano.testcermati.utils.Response.SUCCESS;
import static com.zayano.testcermati.utils.Response.UPDATE;

public class MainActivity extends AppCompatActivity {
    private MaterialSearchBar searchBar;
    private ProgressBar progressBar;
    private RecyclerView rvUser;
    private MainActivityAdapter mainAdapter;
    private RecyclerViewScrollListener scrollListener;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
        viewModel.getResponseData().observe(this, this::fetchResponse);

        initSearchBar();
        initRecyclerView();
    }

    private void fetchResponse(Response response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case ERROR:
                renderErrorState(response.error);
                break;

            case SUCCESS:
                renderDataState((GithubResponse) response.data);
                break;

            case UPDATE:
                renderUpdateState((GithubResponse) response.data);
        }
    }

    private void initSearchBar() {
        searchBar = findViewById(R.id.search_bar_user);
        searchBar.hideSuggestionsList();
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {

            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                searchUser(text.toString());
                searchBar.disableSearch();
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
    }

    private void initRecyclerView() {
        progressBar = findViewById(R.id.progress_bar);
        rvUser = findViewById(R.id.rv_user);
        mainAdapter = new MainActivityAdapter();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvUser.setLayoutManager(linearLayoutManager);
        rvUser.setHasFixedSize(true);
        rvUser.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        rvUser.setAdapter(mainAdapter);

        scrollListener = new RecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextPage(page + 1);
            }
        };

        rvUser.addOnScrollListener(scrollListener);

        mainAdapter.setOnItemClickListener(((itemView, position) -> {
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
            intent.putExtra(UserProfileActivity.KEY_USERNAME, mainAdapter.getData(position).getLogin());
            startActivity(intent);
        }));
    }

    private void loadNextPage(int page) {
        viewModel.loadNextPageUserData(page);
    }

    private void searchUser(String searchQuery) {
        viewModel.searchUser(searchQuery);
    }

    private void renderLoadingState() {
        rvUser.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }


    private void renderDataState(GithubResponse data) {
        progressBar.setVisibility(View.GONE);

        if (data.getTotalCount() == 0) {
            Toast.makeText(this,
                    getResources().getString(R.string.user_not_found),
                    Toast.LENGTH_SHORT).show();
        } else {
            mainAdapter.addNewData(data.getItems());
            rvUser.setVisibility(View.VISIBLE);
        }
    }

    private void renderUpdateState(GithubResponse data) {
        mainAdapter.addData(data.getItems());
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