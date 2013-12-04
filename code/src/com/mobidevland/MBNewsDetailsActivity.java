package com.mobidevland;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobidevland.business.net.ContentProviderManager;
import com.mobidevland.business.net.NewsManager;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

public class MBNewsDetailsActivity extends MBBaseActivity {

	protected static final String TAG = "MBNewsDetailsActivity";
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	private int position = 0;

	private ParseObject newsData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mbnews_details);
		getActionBar().hide();
		
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

		ImageView quickContactBadge = (ImageView) findViewById(R.id.news_icon);
		TextView titleTextView = (TextView) findViewById(R.id.news_title);
		TextView dateTextView = (TextView) findViewById(R.id.news_date);
		TextView descTextView = (TextView) findViewById(R.id.news_desc);

		ParseFile file = newsData.getParseFile("img");
		if (file != null && !TextUtils.isEmpty(file.getUrl())) {
			Picasso.with(this).load(file.getUrl()).placeholder(R.drawable.place_holder_big).error(R.drawable.place_holder_big).into(quickContactBadge);
		}

		titleTextView.setText(newsData.getString("title"));
		dateTextView.setText(format.format(newsData.getDate("acticleDate")));
		descTextView.setText(newsData.getString("desc"));
		
		quickContactBadge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MBNewsDetailsActivity.this, MBFullScreenViewActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
				
			}
		});
		
		findViewById(R.id.gplus).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				startActivity(ContentProviderManager.getInstance(MBNewsDetailsActivity.this).gplusShareContent(newsData.getString("title").concat("\n").concat(newsData.getString("desc"))));
			}
		});

	}

}
