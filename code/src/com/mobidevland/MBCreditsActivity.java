package com.mobidevland;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MBCreditsActivity extends MBBaseActivity {

	private String url;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		url = "file:///android_asset/credits.html";
		ParseQuery<ParseObject> query = ParseQuery.getQuery("credits");
		query.orderByDescending("createdAt");
		query.getFirstInBackground(new GetCallback<ParseObject>() {

			public void done(ParseObject object, ParseException e) {
				if (object == null) {
					Log.d("score", "The getFirst request failed.");
					finish();
				} else {
					Log.d("score", "Retrieved the object.");
					ParseFile file = object.getParseFile("url");
					if (file != null && !TextUtils.isEmpty(file.getUrl())) {
						url = file.getUrl();
					}
				}
				showView();
			}
		});
		webView = new WebView(this);
		setContentView(webView);
	}

	private void showView() {
		webView.loadUrl(url);
	}

}
