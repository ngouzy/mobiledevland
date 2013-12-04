package com.mobidevland.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobidevland.R;

public class MainGridAdapter extends BaseAdapter {

	private final String[] titles = { "news", "jobs", "wiki mobi", "events", "appel à projets", "touch", "QR code", "mon compte", "favoris", "chat"};
	private final int[] backs = { R.drawable.backg_blue_normal, R.drawable.backg_red_normal, R.drawable.backg_green_normal, R.drawable.backg_red_normal, R.drawable.backg_yellow_normal,
			R.drawable.backg_blue_normal, R.drawable.backg_yellow_normal, R.drawable.backg_blue_normal, R.drawable.backg_red_normal, R.drawable.backg_green_normal, R.drawable.backg_yellow_normal,
			R.drawable.backg_green_normal };
	private final int[] icons = { R.drawable.butt_news, R.drawable.butt_jobs, R.drawable.butt_wiki_mobi, R.drawable.butt_events, R.drawable.butt_appel_projet, R.drawable.butt_touch,
			R.drawable.butt_qrcode, R.drawable.butt_moncompte, R.drawable.butt_favoris, R.drawable.butt_chat};
	
	private Context mContext;

	public MainGridAdapter(final Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return 12;
	}

	@Override
	public Object getItem(int position) {
		return titles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.grid_item, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.text);
		ImageView img = (ImageView) convertView.findViewById(R.id.img);
		convertView.setBackgroundResource(backs[position]);
		if (position != 10 && position != 11) {
			tv.setText(titles[position]);
			img.setImageResource(icons[position]);
		} else {
			tv.setText("");
			img.setImageDrawable(null);
		}
		return convertView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		if (position == 10 || position == 11)
			return false;
		return true;
	}

}
