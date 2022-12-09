package kr.co.project.view.sign.util;

import android.content.Context;
import android.content.SharedPreferences;

import kr.co.project.R;

public class PreferenceUtils {

    private static Context mAppContext;
    private static final String PREFERENCE_KEY_USER_TOKEN = "";


    private PreferenceUtils() {
    }

    public static void init(Context appContext) {
        mAppContext = appContext;
    }


    public static void setToken(String token){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(String.valueOf(R.string.PREFERENCE_KEY_USER_TOKEN), token).apply();
    }
    public static String getToken(){
        return getSharedPreferences().getString(String.valueOf(R.string.PREFERENCE_KEY_USER_TOKEN), "");
    }



    public static void setConnected(boolean tf) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(String.valueOf(R.string.PREFERENCE_KEY_CONNECTED), tf).apply();
    }




    private static SharedPreferences getSharedPreferences() {
        return mAppContext.getSharedPreferences("won", Context.MODE_PRIVATE);
    }

//    public static String getNickname() {
//        return getSharedPreferences().getString(PREFERENCE_KEY_NICKNAME, "");
//    }



//    public static void setNickname(String nickname) {
//        SharedPreferences.Editor editor = getSharedPreferences().edit();
//        editor.putString(PREFERENCE_KEY_NICKNAME, nickname).apply();
//    }

    public static boolean getConnected() {
        return getSharedPreferences().getBoolean(String.valueOf(R.string.PREFERENCE_KEY_CONNECTED), false);
    }
    public static void setUserId(String userId) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(String.valueOf(R.string.PREFERENCE_KEY_USER_ID), userId).apply();
    }

    public static String getUserId() {
        return getSharedPreferences().getString(String.valueOf(R.string.PREFERENCE_KEY_USER_ID), "");
    }



    public static void clearAll() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear().apply();
    }

}
