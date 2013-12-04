package com.mobidevland;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ListView;

import com.mobidevland.adapter.NewsAdapter;
import com.mobidevland.business.net.NewsManager;
import com.mobidevland.widget.GalleryAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MBNewsActivity extends MBBaseActivity implements OnItemClickListener {

	protected static final String TAG = "MBNewsActivity";

	private ListView listView;
	private NewsAdapter adapter;
	@SuppressWarnings("deprecation")
	private Gallery coverflow;
	private GalleryAdapter adapterFlow;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mbnews);
		getActionBar().hide();
		
		Button infos = (Button) findViewById(R.id.infos);
		infos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		coverflow = (Gallery) findViewById(R.id.coverflowMobi);
		coverflow.setSpacing(1);
		coverflow.setAnimationDuration(1000);
		listView = (ListView) findViewById(R.id.news_listview);

		adapter = new NewsAdapter(this);
		adapter.setNews(NewsManager.getInstance().getNews());
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);

		adapterFlow = new GalleryAdapter(MBNewsActivity.this, NewsManager.getInstance().getNewsUne().size());
		coverflow.setAdapter(adapterFlow);
		adapterFlow.setNews(NewsManager.getInstance().getNewsUne());
		updteList();
		updteCover();
	}

	private void updteList() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Article");
		query.orderByDescending("acticleDate");
		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> acticleList, ParseException e) {
				if (e == null) {
					Log.d(TAG, "Retrieved " + acticleList.size() + " acticles");
					adapter.setNews(acticleList);
					NewsManager.getInstance().setNews(acticleList);
				} else {
					Log.d(TAG, "Error: " + e.getMessage());
				}
			}
		});
	}

	private void updteCover() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Article");
		query.orderByDescending("acticleDate");
		query.whereEqualTo("une", true);
		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> acticleList, ParseException e) {
				if (e == null) {
					Log.d(TAG, "updteCover => Retrieved " + acticleList.size() + " acticles");
					adapterFlow = new GalleryAdapter(MBNewsActivity.this, acticleList.size());
					coverflow.setAdapter(adapterFlow);
					adapterFlow.setNews(acticleList);
					NewsManager.getInstance().setNewsUne(acticleList);
				} else {
					Log.d(TAG, "updteCover => Error: " + e.getMessage());
				}
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, MBNewsDetailsActivity.class);
		intent.putExtra("position", arg2);
		startActivity(intent);
	}

}
