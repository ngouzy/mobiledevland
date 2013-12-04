package com.mobidevland.commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class SettingsManager {

	private final static String APP_PREFNAME = "mobidevland_pref";
	private static SettingsManager mSettingsManagerInstance;
	private final Context mContext;
	private final SharedPreferences mSharedPreferences;

	public static String PREF_USER_USERNAME = "pref_user_username";
	public static String PREF_USER_PWD = "pref_user_pwd";

	public static String PREF_USER_FIRSTNAME = "pref_user_firstname";
	public static String PREF_USER_LASTNAME = "pref_user_lastname";
	public static String PREF_USER_EMAIL = "pref_user_email";
	public static String PREF_USER_COMPANY = "pref_user_company";
	public static String PREF_USER_ADDRESS = "pref_user_address";
	public static String PREF_USER_ZIP = "pref_user_zip";
	public static String PREF_USER_CITY = "pref_user_city";

	public static SettingsManager getInstance(final Context _context) {
		synchronized (SettingsManager.class) {
			if (mSettingsManagerInstance == null) {
				mSettingsManagerInstance = new SettingsManager(_context);
			}
		}
		return mSettingsManagerInstance;
	}

	private SettingsManager(final Context _context) {
		mContext = _context;
		mSharedPreferences = mContext.getSharedPreferences(APP_PREFNAME,
				Context.MODE_PRIVATE);
	}

	public void setStringPreference(final String key, final String value) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public String getStringPreference(final String key) {
		return mSharedPreferences.getString(key, null);
	}

	public void removeStringPreference(final String key) {
		Editor edit = mSharedPreferences.edit();
		edit.remove(key);
		edit.commit();
	}

	public boolean isConnected() {
		if (!TextUtils.isEmpty(getStringPreference(PREF_USER_USERNAME)))
			return true;
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			return true;
		}
		return false;
	}

}
