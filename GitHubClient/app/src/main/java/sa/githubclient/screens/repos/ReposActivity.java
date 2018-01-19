package sa.githubclient.screens.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import sa.githubclient.GitHubApp;
import sa.githubclient.R;
import sa.githubclient.api.models.Repository;
import sa.githubclient.screens.repos.dagger.DaggerReposActivityComponent;
import sa.githubclient.screens.repos.dagger.ReposActivityModule;
import sa.githubclient.screens.repos.mvp.ReposPresenter;
import sa.githubclient.screens.repos.mvp.ReposView;

import static sa.githubclient.screens.repos.dagger.ReposActivityModule.USER_NAME;


public class ReposActivity extends AppCompatActivity implements ReposView {

    public static final String USER_NAME_KEY = "User_name_key";

    @Inject
    ReposPresenter presenter;
    @Inject
    ReposRVAdapter adapter;
    @Inject
    @Named(USER_NAME)
    String userName;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.repos_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarContainer)
    FrameLayout loader;


    public static void start(Context context, String userName) {
        Intent intent = new Intent(context, ReposActivity.class);
        intent.putExtra(USER_NAME_KEY, userName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerReposActivityComponent.builder()
                .appComponent(GitHubApp.getAppComponent())
                .reposActivityModule(new ReposActivityModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_repos);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.repos_by, userName));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        presenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showError(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideLoading() {
        loader.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRepos(List<Repository> repositories) {
        adapter.swapData(repositories);
    }
}
