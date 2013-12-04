package com.mobidevland.business.net;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NewsManager {

	protected static final String TAG = "NewsManager";

	private static NewsManager instance;

	private List<ParseObject> news;
	private List<ParseObject> newsUne;

	private NewsManager() {
		news = new ArrayList<ParseObject>();
		newsUne = new ArrayList<ParseObject>();
	}

	public static NewsManager getInstance() {
		if (instance == null) {
			instance = new NewsManager();
		}
		return instance;
	}

	/**
	 * @return the news
	 */
	public List<ParseObject> getNews() {
		return news;
	}

	/**
	 * @param news
	 *            the news to set
	 */
	public void setNews(List<ParseObject> news) {
		this.news = news;
	}

	public void updteNewsList() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Article");
		query.orderByDescending("acticleDate");
		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> acticleList, ParseException e) {
				if (e == null) {
					Log.d(TAG, "Retrieved " + acticleList.size() + " acticles");
					setNews(acticleList);
				} else {
					Log.d(TAG, "Error: " + e.getMessage());
				}
			}
		});
	}

	public void updteNewsUneCover() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Article");
		query.orderByDescending("acticleDate");
		query.whereEqualTo("une", true);
		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> acticleList, ParseException e) {
				if (e == null) {
					Log.d(TAG, "updteCover => Retrieved " + acticleList.size() + " acticles");
					setNewsUne(acticleList);
				} else {
					Log.d(TAG, "updteCover => Error: " + e.getMessage());
				}
			}
		});

	}

	/**
	 * @return the newsUne
	 */
	public List<ParseObject> getNewsUne() {
		return newsUne;
	}

	/**
	 * @param newsUne
	 *            the newsUne to set
	 */
	public void setNewsUne(List<ParseObject> newsUne) {
		this.newsUne = newsUne;
	}

}
