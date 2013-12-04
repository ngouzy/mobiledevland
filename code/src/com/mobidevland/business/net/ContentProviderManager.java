package com.mobidevland.business.net;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.plus.PlusShare;
import com.mobidevland.R;

public class ContentProviderManager {

	private static ContentProviderManager instance;
	private Context context;

	private ContentProviderManager(final Context context) {
		this.context = context;
	}

	public static ContentProviderManager getInstance(final Context context) {
		if (instance == null) {
			instance = new ContentProviderManager(context);
		}
		return instance;
	}

	public Intent gplusShareContent(final String content) {
		Intent shareIntent = new PlusShare.Builder(context).setType("text/plain").setText(content).setContentUrl(Uri.parse("https://developers.google.com/+/")).getIntent();
		return shareIntent;

	}

	public Intent shareApp() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, context.getText(R.string.shareToContent));
		sendIntent.setType("text/plain");
		return Intent.createChooser(sendIntent, context.getText(R.string.shareTo));
	}

}
