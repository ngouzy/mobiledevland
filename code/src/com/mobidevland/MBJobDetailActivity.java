package com.mobidevland;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mobidevland.R.id;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MBJobDetailActivity extends MBBaseActivity {

	private String entText;
	private String posteText;
	private String profileText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_detail);

		final TextView tv = (TextView) findViewById(R.id.description);
		Button entreprise = (Button) findViewById(id.entreprise);
		Button poste = (Button) findViewById(id.poste);
		Button profile = (Button) findViewById(id.profile);
		entText = getResources().getString(R.string.long_text);
		posteText = getResources().getString(R.string.long_text);
		profileText = getResources().getString(R.string.long_text);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Job");
		query.orderByDescending("createdAt");
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (object != null) {
					entText = object.getString("entreprise");
					posteText = object.getString("poste");
					profileText = object.getString("profil");
					tv.setText(entText);
				}
			}
		});
		entreprise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv.setText(entText);
			}
		});
		poste.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv.setText(posteText);
			}
		});
		profile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv.setText(profileText);
			}
		});
	}

}
