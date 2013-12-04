package com.mobidevland;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobidevland.business.net.ContactsManager;
import com.mobidevland.data.Contact;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

public class MBTouchDetailActivity extends MBBaseActivity {

	private TextView contactTv;
	private TextView descriptionTv;
	private ImageView photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Contact contact = ContactsManager.getInstance().getSelectedContact();
		setContentView(R.layout.activity_touch_detail);

		photo = (ImageView) findViewById(R.id.photo);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");
		query.whereContains("lastname", contact.getLastname());
		query.getFirstInBackground(new GetCallback<ParseObject>() {

			public void done(ParseObject object, ParseException e) {
				if (object != null)
					Log.d("score", "Retrieved the object.");
				ParseFile file = object.getParseFile("photo");
				if (file != null && !TextUtils.isEmpty(file.getUrl())) {
					Picasso.with(MBTouchDetailActivity.this).load(file.getUrl()).into(photo);
				}
			}
		});

		contactTv = (TextView) findViewById(R.id.contact);
		descriptionTv = (TextView) findViewById(R.id.description);
		contactTv.setText(contact.getFirstname() + " " + contact.getLastname() + " \n " + contact.getCompany() + " \n " + contact.getTitle() + " \n " + contact.getEmail() + " \n "
				+ contact.getPhone());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.touch_detail_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_moins:
			Log.v("MBTouchDetailActivity", "moins");
			float textSize = descriptionTv.getTextSize();
			descriptionTv.setTextSize(textSize - 1);
			break;
		case R.id.action_plus:
			Log.v("MBTouchDetailActivity", "plus");
			float textSize2 = descriptionTv.getTextSize();
			descriptionTv.setTextSize(textSize2 + 1);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
