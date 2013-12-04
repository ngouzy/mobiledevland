package com.mobidevland.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PubReceiver extends BroadcastReceiver {

	private static final String TAG = "PubReceiver";

	public static final String ACTION_PUB = "com.mobidevland.SHOW_PUB";
	public static final String ACTION_PUB_NOW = "com.mobidevland.SHOW_PUB_NOW";
	public static final String ACTION_NEWS = "com.mobidevland.SHOW_NEWS";
	public static final String ACTION_NEWS_NOW = "com.mobidevland.SHOW_NEWS_NOW";

	public static final String KEY_PARSE_CHANNEL = "com.parse.Channel";
	public static final String KEY_PARSE_DATA = "com.parse.Data";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		String channel = intent.getExtras().getString(KEY_PARSE_CHANNEL);

		Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
		if (ACTION_PUB.equals(action)) {
			Intent intentPub = new Intent(ACTION_PUB_NOW);
			intentPub.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intentPub);
		}

	}

}
