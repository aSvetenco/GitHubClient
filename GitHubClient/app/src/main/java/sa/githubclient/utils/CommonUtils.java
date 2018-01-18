package sa.githubclient.utils;


public final class CommonUtils {

  public static void checkNonNull(Object o) {
    if (o == null) {
      throw new NullPointerException("Inserted value cannot be null");
    }
  }
}
