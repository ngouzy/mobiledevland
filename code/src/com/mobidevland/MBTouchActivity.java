package com.mobidevland;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MBTouchActivity extends MBBaseActivity {

	private Button mapButton;

	public static void start(Context ctx) {
		Intent intent = new Intent(ctx, MBTouchActivity.class);
		ctx.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mbtouch);
		// Show the Up button in the action bar.
		setupActionBar();
		mapButton = (Button) findViewById(R.id.mapButton);
		mapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MBMapActivity.start(MBTouchActivity.this);
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().hide();
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

}
