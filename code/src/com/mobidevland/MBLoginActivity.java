package com.mobidevland;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobidevland.commons.SettingsManager;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MBLoginActivity extends MBBaseActivity {

	protected static final String TAG = "MBLoginActivity";

	private View mask;
	private EditText surnameT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		surnameT = (EditText) findViewById(R.id.surname);
		hideKeyboard();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		mask = findViewById(R.id.mask);
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			startMainActivity();
		} else {
			String usernameInPref = SettingsManager.getInstance(this).getStringPreference(SettingsManager.PREF_USER_USERNAME);
			if (TextUtils.isEmpty(usernameInPref)) {
				mask.setVisibility(View.GONE);

				Button laterBt = (Button) findViewById(R.id.later);
				laterBt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						startMainActivity();
					}
				});
				Button facebookBt = (Button) findViewById(R.id.facebook);
				
				facebookBt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						MBLoginActivity.this.setProgressBarIndeterminateVisibility(true);
						ParseFacebookUtils.logIn(MBLoginActivity.this, new LogInCallback() {

							@Override
							public void done(ParseUser user, ParseException err) {
								MBLoginActivity.this.setProgressBarIndeterminateVisibility(false);
								if (user == null) {
									Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
								} else if (user.isNew()) {
									Log.d("MyApp", "Contact signed up and logged in through Facebook!");
									startMainActivity();
								} else {
									Log.d("MyApp", "Contact logged in through Facebook!");
									startMainActivity();
								}
							}
						});
					}
				});
				final EditText nameT = (EditText) findViewById(R.id.name);
				final EditText mailT = (EditText) findViewById(R.id.mail);
				final EditText passwordT = (EditText) findViewById(R.id.password);
				final CheckBox cguCb = (CheckBox) findViewById(R.id.cgu);
				final EditText confirmT = (EditText) findViewById(R.id.confirm_password);
				Button registerBt = (Button) findViewById(R.id.register);

				hideKeyboard();
				
				registerBt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						hideKeyboard();
						if (!cguCb.isChecked()) {
							showErrorDialogCGU("Veuillez accepter les conditions d'utilisations avant de vous enregistrer.");
						} else if (!passwordT.getText().toString().equals(confirmT.getText().toString())) {
							showErrorDialogCGU("Les deux mots de passe rentrés ne sont pas les mêmes.");
						} else if (TextUtils.isEmpty(surnameT.getText().toString()) || TextUtils.isEmpty(nameT.getText().toString()) || TextUtils.isEmpty(mailT.getText().toString()) || TextUtils.isEmpty(passwordT.getText().toString())
								|| TextUtils.isEmpty(confirmT.getText().toString())) {
							showErrorDialogCGU("Veuillez compléter tous les champs du formulaire.");
						} else {
							final ParseUser user = new ParseUser();
							user.setUsername(mailT.getText().toString());
							user.setEmail(mailT.getText().toString());
							user.setPassword(passwordT.getText().toString());
							user.put("surname", surnameT.getText().toString());
							user.put("name", nameT.getText().toString());
							MBLoginActivity.this.setProgressBarIndeterminateVisibility(true);
							mask.setVisibility(View.VISIBLE);
							user.signUpInBackground(new SignUpCallback() {

								@Override
								public void done(com.parse.ParseException e) {
									if (e == null) {
										SettingsManager.getInstance(MBLoginActivity.this).setStringPreference(SettingsManager.PREF_USER_USERNAME, user.getUsername());
										SettingsManager.getInstance(MBLoginActivity.this).setStringPreference(SettingsManager.PREF_USER_PWD, passwordT.getText().toString());
										MBLoginActivity.this.setProgressBarIndeterminateVisibility(false);
										startMainActivity();
									} else {
										// Sign up didn't succeed. Look at the
										// ParseException
										// to figure out what went wrong
										Log.e("MBLoginActivity", e.getMessage());
										e.printStackTrace();
										showErrorDialogCGU("Problème lors de l'authentification");
										mask.setVisibility(View.GONE);
									}
								}
							});
						}
					}
				});
			} else {
				mask.setVisibility(View.VISIBLE);
				MBLoginActivity.this.setProgressBarIndeterminateVisibility(true);
				String passwordInPref = SettingsManager.getInstance(this).getStringPreference(SettingsManager.PREF_USER_PWD);
				ParseUser.logInInBackground(usernameInPref, passwordInPref, new LogInCallback() {

					public void done(ParseUser user, ParseException e) {
						MBLoginActivity.this.setProgressBarIndeterminateVisibility(false);
						if (user != null) {
							startMainActivity();
						} else {
							showErrorDialogCGU("Problème lors de l'authentification");
							mask.setVisibility(View.GONE);
						}
					}
				});
			}
		}
	}

	private void startMainActivity() {
		Intent intent = new Intent(MBLoginActivity.this, MBMainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void showErrorDialogCGU(String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final ScrollView s_view = new ScrollView(getApplicationContext());
		final TextView t_view = new TextView(getApplicationContext());
		t_view.setText(message);
		t_view.setTextSize(14);
		s_view.addView(t_view);
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		t_view.setPadding(margin, margin, margin, margin);
		alert.setTitle("Attention");
		alert.setView(s_view);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		alert.show();
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(surnameT.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED);
	}

}
