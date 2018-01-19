package sa.githubclient.screens.repos;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import sa.githubclient.GitHubApp;
import sa.githubclient.R;
import sa.githubclient.screens.main.MainActivity;
import sa.githubclient.screens.repos.dagger.DaggerSplashActivityComponent;
import sa.githubclient.screens.repos.dagger.ReposActivityModule;
import sa.githubclient.screens.repos.mvp.ReposPresenter;
import sa.githubclient.screens.repos.mvp.ReposView;

public class ReposActivity extends AppCompatActivity implements ReposView {

    @Inject
    ReposPresenter presenter;

    @BindView(R.id.user_name)
    TextInputLayout userName;
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.continue_btn)
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerSplashActivityComponent.builder()
                .appComponent(GitHubApp.getAppComponent())
                .splashActivityModule(new ReposActivityModule())
                .build()
                .inject(this);
        setContentView(R.layout.activity_repos);
        ButterKnife.bind(this);
        presenter.takeView(this);
    }

    @Override
    public Observable<CharSequence> getUserName() {
        return RxTextView.textChanges(userName.getEditText());
    }

    @Override
    public Observable<CharSequence> getPassword() {
        return RxTextView.textChanges(password.getEditText());
    }

    @Override
    public Observable<Void> onContinueClick() {
        return RxView.clicks(continueButton);
    }

    @Override
    public void showMainActivity(String name, String password) {
        MainActivity.start(this, name, password);
    }

    @Override
    public void showUserNameError(int resId) {
        userName.setError(getString(resId));
        userName.setErrorEnabled(true);
        userName.requestFocus();
    }

    @Override
    public void showPasswordError(int resId) {
        password.setError(getString(resId));
        password.setErrorEnabled(true);
        password.requestFocus();
    }

/*    @Override
    public void resetErrors() {
        userName.setError(null);
        password.setError(null);
        userName.setErrorEnabled(false);
        password.setErrorEnabled(false);
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deatachView();
    }

}
