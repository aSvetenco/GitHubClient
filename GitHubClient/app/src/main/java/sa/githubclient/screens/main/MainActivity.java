package sa.githubclient.screens.main;

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
import sa.githubclient.screens.main.dagger.DaggerMainActivityComponent;
import sa.githubclient.screens.main.dagger.MainActivityModule;
import sa.githubclient.screens.main.mvp.MainPresenter;
import sa.githubclient.screens.main.mvp.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MainPresenter presenter;

    @BindView(R.id.user_name)
    TextInputLayout userName;
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.continue_btn)
    Button continueButton;

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
    public void showMainActivity() {
    }

    @Override
    public void showError(int resId) {
        switch (resId) {
            case R.string.password_empty: {
                password.setError(getString(resId));
                break;
            }
            case R.string.user_name_empty: {
                userName.setError(getString(resId));
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deatachView();
    }

}
