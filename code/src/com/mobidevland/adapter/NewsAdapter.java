package com.mobidevland.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobidevland.R;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

public class NewsAdapter extends BaseAdapter {

	protected static final String TAG = "NewsAdapter";

	private List<ParseObject> news;
	private Context mContext;

	public NewsAdapter(final Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		if (news == null) {
			return 0;
		}
		return news.size();
	}

	@Override
	public ParseObject getItem(int position) {
		return news.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ParseObject item = getItem(position);
		NewsViewHolder dataHolder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.news_list_item, null);
			dataHolder = new NewsViewHolder();
			dataHolder.init(convertView);
			convertView.setTag(dataHolder);
		} else {
			dataHolder = (NewsViewHolder) convertView.getTag();
		}
		dataHolder.update(item);
		if (position % 2 == 0) {
			convertView.setBackgroundResource(R.drawable.backg_list_gray_dark);
		} else {
			convertView.setBackgroundResource(R.drawable.backg_list_gray_light);
		}

		return convertView;
	}

	/**
	 * Contact list view
	 */
	private class NewsViewHolder {

		public ImageView quickContactBadge;
		public TextView titleTextView;
		public TextView dateTextView;
		public TextView shortDescTextView;

		public void init(View root) {
			quickContactBadge = (ImageView) root.findViewById(R.id.news_icon);
			titleTextView = (TextView) root.findViewById(R.id.news_title);
			dateTextView = (TextView) root.findViewById(R.id.news_date);
			shortDescTextView = (TextView) root.findViewById(R.id.news_desc);
		}

		public void update(final ParseObject data) {
			ParseFile file = data.getParseFile("img");
			if (file != null && !TextUtils.isEmpty(file.getUrl())) {
				Picasso.with(mContext).load(file.getUrl()).placeholder(R.drawable.place_holder_big).error(R.drawable.place_holder_big).into(quickContactBadge);
			}

			titleTextView.setText(data.getString("title"));
			dateTextView.setText(format.format(data.getDate("acticleDate")));
			shortDescTextView.setText(data.getString("desc"));

		}
	}

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setNews(final List<ParseObject> news) {
		this.news = news;
		notifyDataSetChanged();
	}

}