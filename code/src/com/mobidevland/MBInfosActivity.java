package com.mobidevland;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mobidevland.adapter.InfosAdapter;
import com.mobidevland.business.net.ContentProviderManager;


public class MBInfosActivity extends MBBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infos);
		
		ListView lv = (ListView) findViewById(R.id.list);
		lv.setAdapter(new InfosAdapter(this));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				switch (position) {
				case 1:
					startActivity(new Intent(MBInfosActivity.this, MBCGUActivity.class));
					break;
					
				case 4:
					startActivity(new Intent(MBInfosActivity.this, MBCreditsActivity.class));
					break;
					
				case 6:
					startActivity(ContentProviderManager.getInstance(MBInfosActivity.this).shareApp());
					break;

				default:
					break;
				}
				
			}
		});
	}

}
