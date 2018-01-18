package sa.githubclient.utils;

import android.content.SharedPreferences;

import static sa.githubclient.utils.CommonUtils.checkNonNull;

public class SharedPref {

    private static final String EMPTY_STRING = "";
    private static final String PREF_KEY_CURRENT_USER_ID = "user_id_key";
    private final SharedPreferences preferences;

    public SharedPref(SharedPreferences preferences) {
        checkNonNull(preferences);
        this.preferences = preferences;
    }

    public void setCurrentUserId (String userId) {
        String id = userId == null ? EMPTY_STRING : userId;
        preferences.edit().putString(PREF_KEY_CURRENT_USER_ID, id).apply();
    }

    public String getCurrentUserId() {
        String userId = preferences.getString(PREF_KEY_CURRENT_USER_ID, EMPTY_STRING);
        return userId.equals(EMPTY_STRING) ? null : userId;
    }

}
