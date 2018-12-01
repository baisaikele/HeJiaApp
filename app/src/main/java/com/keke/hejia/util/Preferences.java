package com.keke.hejia.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.keke.hejia.base.HeJiaApp;

import java.util.HashSet;
import java.util.Set;

public class Preferences {
    private static final String PREF_NAME = "appsys.local.dbfile";
    
    public static final String KEY_CHANNEL_NAME = "KEY_CHANNEL_NAME";
    public static final String KEY_CHANNEL_ID = "KEY_CHANNEL_ID";
    public static final String KEY_SESSION_ID = "key_session_id";
    public static final String KEY_USER_INFO = "key_user_info";
    public static final String KEY_GIFT_LIST = "key_user_info";
    public static final String KEY_SYSTEM_MESSAGE_UNREAD = "KEY_SYSTEM_MESSAGE_UNREAD";
    public static final String KEY_MY_MESSAGE_UNREAD = "KEY_MY_MESSAGE_UNREAD";
    
    private static Preferences sPreferences;
    
    private SharedPreferences mSettings;
    
    private Preferences(Context context) {
        mSettings = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    public static Preferences getInstance() {
        if (sPreferences == null) {
            sPreferences = new Preferences(HeJiaApp.instance);
        }
        return sPreferences;
    }
    
    public boolean isContains(String key) {
        return mSettings.contains(key);
    }
    
    public int getInt(String name, int def) {
        return mSettings.getInt(name, def);
    }
    
    public void putInt(String name, int value) {
        SharedPreferences.Editor edit = mSettings.edit();
        edit.putInt(name, value);
        edit.apply();
    }
    
    public void putFloat(String name, float value) {
        SharedPreferences.Editor edit = mSettings.edit();
        edit.putFloat(name, value);
        edit.apply();
    }
    
    
    public String getString(String key) {
        return mSettings.getString(key, "");
    }
    
    public String getString(String key, String defaultValue) {
        return mSettings.getString(key, defaultValue);
    }
    
    public void putString(String key, String value) {
        SharedPreferences.Editor edit = mSettings.edit();
        edit.putString(key, value);
        edit.apply();
    }
    
    public long getLong(String key, long defaultValue) {
        return mSettings.getLong(key, defaultValue);
    }
    
    public void putLong(String key, long value) {
        SharedPreferences.Editor edit = mSettings.edit();
        edit.putLong(key, value);
        edit.apply();
    }
    
    public boolean getBoolean(String key, boolean defaultValue) {
        return mSettings.getBoolean(key, defaultValue);
    }
    
    public float getFloat(String key, float defaultValue) {
        return mSettings.getFloat(key, defaultValue);
    }
    
    public void putBoolean(String key, boolean bool) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(key, bool);
        editor.apply();
    }
    
    public void putStringSet(String key, Set<String> values) {
        mSettings.edit().putStringSet(key, values).apply();
    }
    
    public Set<String> getStringSet(String key) {
        return mSettings.getStringSet(key, new HashSet<String>());
    }
    
    public void remove(String key) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.remove(key);
        editor.apply();
    }
    
    public void clear() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
    }
    
    
}

