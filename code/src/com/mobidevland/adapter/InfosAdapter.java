package com.mobidevland.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobidevland.R;

public class InfosAdapter extends BaseAdapter {

	private String[] titles = { "A propos de #MOBL", "Conditions d'utilisation", "Protection de la vie privée", "Mentions légales", "Crédits", "Notes de version", "Partager l'application", "Votre avis nous intéresse" };
	private Context mContext;

	public InfosAdapter(final Context context) {
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return 8;
	}

	@Override
	public String getItem(int position) {
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
			convertView = inflater.inflate(R.layout.info_item, parent, false);
		}
		TextView tv = (TextView) convertView;
		tv.setText(getItem(position));
//		if (position % 2 == 0) {
//			convertView.setBackgroundResource(R.drawable.backg_list_gray_dark);
//		} else {
//			convertView.setBackgroundResource(R.drawable.backg_list_gray_light);
//		}
		return convertView;
	}

}
