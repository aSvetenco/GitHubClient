package sa.githubclient.screens.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;

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
import sa.githubclient.screens.repos.ReposActivity;
import sa.githubclient.utils.CommonUtils;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String EMPTY_STRING = "";
    private PublishSubject<String> searchViewSubject = PublishSubject.create();

    @Inject
    MainPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_bar)
    SearchView searchView;
    @BindView(R.id.progressBarContainer)
    FrameLayout loader;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.repository_btn)
    Button seeReposButton;
    @BindView(R.id.login)
    TextView userLogin;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.email)
    TextView userEmail;
    @BindView(R.id.bio)
    TextView userBio;
    @BindView(R.id.location)
    TextView userLocation;
    @BindView(R.id.public_repos)
    TextView userReposCount;
    @BindView(R.id.public_gists)
    TextView userGistsCount;
    @BindView(R.id.container)
    ScrollView container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainActivityComponent.builder()
                .appComponent(GitHubApp.getAppComponent())
                .mainActivityModule(new MainActivityModule())
                .build()
                .inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewSubject.onNext(query);
                hideKeyboard();
                searchView.clearFocus();
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
    public Observable<Void> onSeeUserReposClick() {
        return RxView.clicks(seeReposButton);
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
    public void hideKeyboard() {
        CommonUtils.hideKeyboard(this);
    }

    @Override
    public void hideLoading() {
        loader.setVisibility(GONE);
    }

    @Override
    public void showLoading() {
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUser(User user) {
        container.setVisibility(View.VISIBLE);
        if (user.getAvatarUrl() != null) {
            Picasso.with(this).load(user.getAvatarUrl()).into(avatar);
        }

        userLogin.setText(getString(R.string.user_login, user.getLogin()));
        String email = getString(R.string.user_doesnt_have_public_email);
        String name = EMPTY_STRING;
        String bio = EMPTY_STRING;
        String location = EMPTY_STRING;
        String reposCount = EMPTY_STRING;
        String gistsCount = EMPTY_STRING;

        if (user.getName() != null) {
            name = user.getName();
        }
        userName.setText(getString(R.string.user_name, name));

        if (user.getEmail() != null) {
            email = user.getEmail();
        }
        userEmail.setText(getString(R.string.user_email, email));

        if (user.getBio() != null) {
            bio = user.getBio();
        }
        userBio.setText(getString(R.string.bio, bio));

        if (user.getLocation() != null) {
            location = user.getLocation();
        }
        userLocation.setText(getString(R.string.location, location));

        if (user.getPublicRepos() != null) {
            reposCount = String.valueOf(user.getPublicRepos());
        }
        userReposCount.setText(getString(R.string.public_repos, reposCount));
        seeReposButton.setText(getString(R.string.see_user_repos, reposCount));

        if (user.getPublicGists() != null) {
            gistsCount = String.valueOf(user.getPublicGists());
        }
        userGistsCount.setText(getString(R.string.public_gists, gistsCount));
    }

    @Override
    public void startRepoActivity(String userName) {
        ReposActivity.start(this, userName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
