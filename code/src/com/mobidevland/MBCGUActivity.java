package com.mobidevland;

import android.os.Bundle;
import android.webkit.WebView;


public class MBCGUActivity extends MBBaseActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		WebView webView = new WebView(this);
		webView.loadUrl("file:///android_asset/cgu.html");
		setContentView(webView);
	}

}
