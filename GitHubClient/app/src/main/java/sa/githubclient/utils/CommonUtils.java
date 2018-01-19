package sa.githubclient.utils;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class CommonUtils {

  public static void checkNonNull(Object o) {
    if (o == null) {
      throw new NullPointerException("Inserted value cannot be null");
    }
  }
  public static void hideKeyboard(Activity activity) {
    View focusedView = activity.getWindow().getCurrentFocus();
    if (focusedView != null && focusedView.isFocused()) {
      InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }
}
