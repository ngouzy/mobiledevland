package com.mobidevland.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.mobidevland.R;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

public class GalleryAdapter extends BaseAdapter {

	private List<ParseObject> news;

	final private Context mContext;
	public Bitmap mImageBM;
	private int mElementWith = 0;
	private int mElementHeihgt = 0;

	public GalleryAdapter(final Context context, final int size) {
		mContext = context;
		// mImageBM = new Bitmap[size];
		//
		// for (int i = 0; i < size; i++) {
		mImageBM = ((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.place_holder_small)).getBitmap();
		// }
		mElementWith = mImageBM.getWidth();
		mElementHeihgt = mImageBM.getHeight();
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
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		// Use this code if you want to load from resources
		final ImageView myImg = new ImageView(mContext);
		myImg.setLayoutParams(new Gallery.LayoutParams(mElementWith, mElementHeihgt));
		myImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		// myImg.setPadding(0, (int) mContext.getResources().getDimension(R.dimen.margin_top_mobicarte), 0, (int) mContext.getResources().getDimension(R.dimen.margin_top_mobicarte));
		// Make sure we set anti-aliasing otherwise we get jaggies
		final BitmapDrawable drawable = (BitmapDrawable) myImg.getDrawable();
		// drawable.setAntiAlias(true);
		ParseObject data = getItem(position);
		ParseFile file = data.getParseFile("img");
		if (file != null && !TextUtils.isEmpty(file.getUrl())) {
			Picasso.with(mContext).load(data.getParseFile("img").getUrl()).placeholder(R.drawable.place_holder_big).error(R.drawable.place_holder_big).into(myImg);
		}

		return myImg;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setNews(final List<ParseObject> news) {
		this.news = news;
		notifyDataSetChanged();
	}

}
