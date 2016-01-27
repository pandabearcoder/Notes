package elec.dev.notes.proj.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {

    public static final String PREF_FILENAME = "uxPreference";
    public static final String KEY_LEARNED = "user_learned_drawer";
    public static final String KEY_LAST_NOTEBOOK_ID = "last_used_notebook_id";

    public static void saveToPreferences(Context context, String prefName, Boolean prefValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(Pref.PREF_FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(prefName, prefValue);
        editor.apply();
    }

    public static void saveToPreferences(Context context, String prefName, int prefValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(Pref.PREF_FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(prefName, prefValue);
        editor.apply();
    }

    public static Boolean readFromPreferences(Context context, String prefName, Boolean defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(Pref.PREF_FILENAME,Context.MODE_PRIVATE);
        return sharedPref.getBoolean(prefName, defaultValue);
    }

    public static int readFromPreferences(Context context, String prefName, int defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(Pref.PREF_FILENAME,Context.MODE_PRIVATE);
        return sharedPref.getInt(prefName, defaultValue);
    }
}
