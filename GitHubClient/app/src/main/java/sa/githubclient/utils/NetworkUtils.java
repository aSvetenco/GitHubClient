package sa.githubclient.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import rx.Observable;
import sa.githubclient.utils.exceptions.NoNetworkException;

public class NetworkUtils {

  private Context context;

  public NetworkUtils(Context context) {
    this.context = context;
  }

  public Observable<Boolean> networkAvailable() {
    ConnectivityManager connectivityManager =
      (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return Observable.defer(() -> Observable.just(activeNetworkInfo != null && activeNetworkInfo.isConnected()));
  }

  public Observable<Boolean> networkAvailableWithException() {
    ConnectivityManager connectivityManager =
      (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return Observable.defer(() -> Observable.just(activeNetworkInfo != null && activeNetworkInfo.isConnected())
      .filter(isAvailable ->
      {
        if (!isAvailable) {
          throw new NoNetworkException("No network exception");
        }
        return true;
      }));
  }
}