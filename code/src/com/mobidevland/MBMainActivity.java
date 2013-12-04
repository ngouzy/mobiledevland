package com.mobidevland;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mobidevland.business.net.NewsManager;
import com.mobidevland.commons.SettingsManager;
import com.mobidevland.ui.MainGridAdapter;

public class MBMainActivity extends MBBaseActivity {
	
	protected static final String TAG = "MBMainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_mbmain);

		Button infos = (Button) findViewById(R.id.infos);
		infos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MBMainActivity.this, MBInfosActivity.class));
			}
		});

		GridView grid = (GridView) findViewById(R.id.main_grid);
		grid.setAdapter(new MainGridAdapter(this));
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				switch (position) {
				case 0:
					startActivity(new Intent(MBMainActivity.this, MBNewsActivity.class));
					break;
				case 1:
					boolean isConnected = testConnection();
					if (isConnected) {
						startActivity(new Intent(MBMainActivity.this, MBJobActivity.class));
					}
					break;
				case 4:
					testConnection();
					break;
				case 5:
					isConnected = testConnection();
					if (isConnected) {
						MBTouchActivity.start(MBMainActivity.this);
					}
					break;
				case 6:
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

				    //intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				    intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
				    startActivityForResult(intent, 0);
					break;
				case 7:
					goToMonCompte();
					break;

				default:
					break;
				}
			}
		});

		NewsManager.getInstance().updteNewsList();
		NewsManager.getInstance().updteNewsUneCover();
	}

	private boolean testConnection() {
		if (!SettingsManager.getInstance(this).isConnected()) {
			showPopupRegistration();
			return false;
		}
		return true;

	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	            //  The Intents Fairy has delivered us some data!
	            String contents = intent.getStringExtra("SCAN_RESULT");
	            //String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	            Intent i = new Intent(Intent.ACTION_VIEW);
	            i.setData(Uri.parse(contents));
	            startActivity(i);
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        }
	    }
	}

	@Override
	protected void onResume() {
		super.onResume();

		ImageView led = (ImageView) findViewById(R.id.led);
		Button inscrire = (Button) findViewById(R.id.inscription);
		Log.v("MyApp", "isConnected: "+SettingsManager.getInstance(this).isConnected());
		if (SettingsManager.getInstance(this).isConnected()) {
			led.setImageResource(R.drawable.led_on);
			inscrire.setText("Mon compte");
		} else {
			led.setImageResource(R.drawable.led_off);
			inscrire.setText("S'inscrire");
		}
		inscrire.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToMonCompte();
			}
		});
		int connectionResult = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		switch (connectionResult) {
		case ConnectionResult.SUCCESS:
			break;
		case ConnectionResult.SERVICE_MISSING:
		case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
		case ConnectionResult.SERVICE_DISABLED:
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(connectionResult, this, 0);
			dialog.setCancelable(false);
			dialog.show();
			return;
		default:
			// TODO : handle other case
			break;
		}
	}

	private void goToMonCompte() {
		startActivity(new Intent(MBMainActivity.this, MBMonCompteActivity.class));
	}

	private void showPopupRegistration() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final ScrollView s_view = new ScrollView(getApplicationContext());
		final TextView t_view = new TextView(getApplicationContext());
		t_view.setText("Pour accéder à cette rubrique, vous devez être membre et/ou connecté. C'est gratuit !");
		t_view.setTextSize(14);
		s_view.addView(t_view);
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		t_view.setPadding(margin, margin, margin, margin);
		alert.setTitle("Attention");
		alert.setView(s_view);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
				goToMonCompte();
			}
		});
		alert.show();
	}

}
