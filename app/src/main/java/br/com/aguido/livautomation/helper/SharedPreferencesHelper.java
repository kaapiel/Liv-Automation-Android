package br.com.aguido.livautomation.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;
import java.util.Set;

public final class SharedPreferencesHelper {

    public static Map<String, ?> readAll(Context context, String file) {

        Map<String, ?> prefs = null;

        try {

            SharedPreferences sharedPrefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);

            prefs = sharedPrefs.getAll();

        } catch (Exception e) {
            prefs = null;
            e.printStackTrace();
        }

        return prefs;
    }

    public static boolean read(Context context, String file, String key, boolean defaultValue) {

        boolean value = false;

        try {

            SharedPreferences sharedPrefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);

            value = sharedPrefs.getBoolean(key, defaultValue);

        } catch (Exception e) {
            value = defaultValue;
            e.printStackTrace();
        }

        return value;
    }

    public static String read(Context context, String file, String key, String defaultValue) {

        String value = null;

        try {

            SharedPreferences sharedPrefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);

            value = sharedPrefs.getString(key, defaultValue);

        } catch (Exception e) {
            value = defaultValue;
            e.printStackTrace();
        }

        return value;
    }

    public static long read(Context context, String file, String key, long defaultValue) {

        long value = Long.MIN_VALUE;

        try {

            SharedPreferences sharedPrefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);

            value = sharedPrefs.getLong(key, defaultValue);

        } catch (Exception e) {
            value = defaultValue;
            e.printStackTrace();
        }

        return value;
    }


    public static boolean write(Context context, String file, String key, boolean value) {

        boolean sucess = false;

        try {

            SharedPreferences sharedPrefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);

            Editor editor = sharedPrefs.edit();

            editor.putBoolean(key, value);

            editor.commit();

            sucess = true;

        } catch (Exception e) {
            sucess = false;
            e.printStackTrace();
        }

        return sucess;
    }

    public static boolean write(Context context, String file, String key, String value) {

        boolean sucess = false;

        try {

            SharedPreferences sharedPrefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);

            Editor editor = sharedPrefs.edit();

            editor.putString(key, value);

            editor.commit();

            sucess = true;

        } catch (Exception e) {
            sucess = false;
            e.printStackTrace();
        }

        return sucess;
    }

    public static boolean write(Context context, String file, String key, long value) {

        boolean sucess = false;

        try {

            SharedPreferences sharedPrefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);

            Editor editor = sharedPrefs.edit();

            editor.putLong(key, value);

            editor.commit();

            sucess = true;

        } catch (Exception e) {
            sucess = false;
            e.printStackTrace();
        }

        return sucess;
    }

    public static boolean write(Context context, String file, String key, Set<String> values) {

        boolean sucess = false;

        try {

            SharedPreferences sharedPrefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);

            Editor editor = sharedPrefs.edit();

            editor.putStringSet(key, values);

            editor.commit();

            sucess = true;

        } catch (Exception e) {
            sucess = false;
            e.printStackTrace();
        }

        return sucess;
    }

    public static boolean remove(Context context, String file, String key) {

        boolean sucess = false;

        try {

            SharedPreferences sharedPrefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);

            Editor editor = sharedPrefs.edit();

            editor.remove(key);

            editor.commit();

            sucess = true;

        } catch (Exception e) {
            sucess = false;
            e.printStackTrace();
        }

        return sucess;
    }
}