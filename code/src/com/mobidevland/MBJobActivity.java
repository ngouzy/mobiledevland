package com.mobidevland;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class MBJobActivity extends MBBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobs);
		
		View illustration = findViewById(R.id.illustration);
		illustration.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MBJobActivity.this, MBJobDetailActivity.class));
			}
		});
	}

}
