package sa.githubclient.screens.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.PublishSubject;
import sa.githubclient.GitHubApp;
import sa.githubclient.R;
import sa.githubclient.api.models.User;
import sa.githubclient.screens.main.dagger.DaggerMainActivityComponent;
import sa.githubclient.screens.main.dagger.MainActivityModule;
import sa.githubclient.screens.main.mvp.MainPresenter;
import sa.githubclient.screens.main.mvp.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    public static final String USER_NAME_KEY = "User_name_key";
    public static final String PASSWORD_KEY = "Password_key";
    private static final String TAG = MainActivity.class.getSimpleName();
    private PublishSubject<String> searchViewSubject = PublishSubject.create();

    @Inject
    MainPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_bar)
    SearchView searchView;
    @BindView(R.id.progressBarContainer)
    FrameLayout loader;

    public static void start(Context context, String userName, String password) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_NAME_KEY, userName);
        intent.putExtra(PASSWORD_KEY, password);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainActivityComponent.builder()
                .appComponent(GitHubApp.getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public Observable<String> onSearchClick() {
        return searchViewSubject.asObservable();
    }

    @Override
    public void showError(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
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
    public void showUser(User user) {
        Log.d(TAG, "User name is - " + user.getName());
    }

    @Override
    public void showRepos(List<Repository> repositories) {
        Log.d(TAG, "User has - " + repositories.size());
    }
}
