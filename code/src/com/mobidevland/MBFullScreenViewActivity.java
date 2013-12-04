package com.mobidevland;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mobidevland.adapter.FullScreenImageAdapter;
import com.mobidevland.business.net.NewsManager;
import com.parse.ParseFile;
import com.parse.ParseObject;

public class MBFullScreenViewActivity extends Activity {

	protected static final String TAG = "MBFullScreenViewActivity";

	private ArrayList<String> urlList = new ArrayList<String>();

	private int position = 0;

	private ParseObject newsData;
	
	private ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mbfullscreen);
		
		Button infos = (Button) findViewById(R.id.infos);
		infos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("position")) {
			position = getIntent().getExtras().getInt("position");
		} else {
			finish();
		}

		newsData = NewsManager.getInstance().getNews().get(position);

		ParseFile file2 = newsData.getParseFile("img2");
		if (file2 != null && !TextUtils.isEmpty(file2.getUrl())) {
			urlList.add(file2.getUrl());
		}

		ParseFile file3 = newsData.getParseFile("img3");
		if (file3 != null && !TextUtils.isEmpty(file3.getUrl())) {
			urlList.add(file3.getUrl());
		}

		ParseFile file4 = newsData.getParseFile("img4");
		if (file4 != null && !TextUtils.isEmpty(file4.getUrl())) {
			urlList.add(file4.getUrl());
		}

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new FullScreenImageAdapter(this, urlList));

	}

}
