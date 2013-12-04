package com.mobidevland;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MBAdvertisingActivity extends Activity {

	protected static final String TAG = "MBSplashScreenActivity";

	protected Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler();
		getPub();
	}

	private void getPub() {
		setContentView(R.layout.activity_splashscreen);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Ads");
		query.orderByDescending("createdAt");
		query.getFirstInBackground(new GetCallback<ParseObject>() {

			public void done(ParseObject object, ParseException e) {
				if (object == null) {
					Log.d("score", "The getFirst request failed.");
					launchMainActivity();
				} else {
					Log.d("score", "Retrieved the object.");
					ParseFile file = object.getParseFile("ads_img");
					if (file != null && !TextUtils.isEmpty(file.getUrl())) {
						downloadAds(file.getUrl());
					} else {
						launchMainActivity();
					}
				}
			}
		});

	}

	private void downloadAds(final String url) {
		ImageView pub = (ImageView) findViewById(R.id.pub);

		Picasso.with(this).load(url).into(pub, new Callback() {

			@Override
			public void onError() {
				handler.removeCallbacksAndMessages(null);
				launchMainActivity();
			}

			@Override
			public void onSuccess() {
				handler.removeCallbacksAndMessages(null);
				handler.postDelayed(showAdvertising, 2 * DurationInMillis.ONE_SECOND * getResources().getInteger(R.integer.pubScreenTime));
			}

		});

	}

	private void launchMainActivity() {
		finish();
	}

	private Runnable showAdvertising = new Runnable() {

		@Override
		public void run() {
			launchMainActivity();
		}
	};

}
