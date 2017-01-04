package ee.ttu.a103944.shopandr.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class Preference {

    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME = "shopAndrApp";
    public static final String PREF_COOKIES = "PREF_COOKIES";


    public Preference(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
    }

    public void saveCookies(String cookie){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_COOKIES,cookie);
        editor.apply();
    }

    public String getCookies(){
        return sharedPreferences.getString(PREF_COOKIES,new String());
    }
}
