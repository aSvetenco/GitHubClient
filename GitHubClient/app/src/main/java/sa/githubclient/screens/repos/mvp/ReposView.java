package sa.githubclient.screens.repos.mvp;

import rx.Observable;

public interface ReposView {

    Observable<CharSequence> getUserName();
    Observable<CharSequence> getPassword();
    Observable<Void> onContinueClick();
    void showMainActivity(String name, String password);
    void showUserNameError(int resId);
    void showPasswordError(int resId);
 /*   void resetErrors();
*/

}
