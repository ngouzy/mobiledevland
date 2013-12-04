package com.mobidevland;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.mobidevland.commons.SettingsManager;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class MBMonCompteActivity extends MBBaseActivity {

	private View mask;

	@Override
	protected void onResume() {
		super.onResume();

		if (SettingsManager.getInstance(this).isConnected()) {
			setContentView(R.layout.activity_moncompte_connected);

			TextView persosButton = (TextView) findViewById(R.id.persos);
			persosButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(new Intent(MBMonCompteActivity.this,
							MBPersosActivity.class));
				}
			});

			Button disconnect = (Button) findViewById(R.id.disconnect);
			disconnect.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					EasyTracker easyTracker = EasyTracker
							.getInstance(MBMonCompteActivity.this);

					// MapBuilder.createEvent().build() returns a Map of event
					// fields and values
					// that are set and sent with the hit.
					easyTracker.send(MapBuilder.createEvent("ui_action", // Event
																			// category
																			// (required)
							"button_press", // Event action (required)
							"logout_button", // Event label
							null) // Event value
							.build());
					ParseUser.logOut();
					SettingsManager.getInstance(MBMonCompteActivity.this)
							.removeStringPreference(
									SettingsManager.PREF_USER_USERNAME);
					SettingsManager.getInstance(MBMonCompteActivity.this)
							.removeStringPreference(
									SettingsManager.PREF_USER_PWD);
					loadNotConnectedView();
				}
			});
		} else {
			loadNotConnectedView();
		}
	}

	private void loadNotConnectedView() {
		setContentView(R.layout.activity_moncompte_notconnected);

		mask = findViewById(R.id.mask);
		mask.setVisibility(View.GONE);
		Button create = (Button) findViewById(R.id.create);
		create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EasyTracker easyTracker = EasyTracker
						.getInstance(MBMonCompteActivity.this);

				// MapBuilder.createEvent().build() returns a Map of event
				// fields and values
				// that are set and sent with the hit.
				easyTracker.send(MapBuilder.createEvent("ui_action", // Event
																		// category
																		// (required)
						"button_press", // Event action (required)
						"create_button", // Event label
						null) // Event value
						.build());
				startActivity(new Intent(MBMonCompteActivity.this,
						MBLoginActivity.class));
				finish();
			}
		});
		Button register = (Button) findViewById(R.id.register);
		final EditText mail = (EditText) findViewById(R.id.mail);
		final EditText password = (EditText) findViewById(R.id.password);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EasyTracker easyTracker = EasyTracker
						.getInstance(MBMonCompteActivity.this);

				// MapBuilder.createEvent().build() returns a Map of event
				// fields and values
				// that are set and sent with the hit.
				easyTracker.send(MapBuilder.createEvent("ui_action", // Event
																		// category
																		// (required)
						"button_press", // Event action (required)
						"register_button", // Event label
						null) // Event value
						.build());
				MBMonCompteActivity.this
						.setProgressBarIndeterminateVisibility(true);
				mask.setVisibility(View.VISIBLE);
				ParseUser.logInInBackground(mail.getText().toString(), password
						.getText().toString(), new LogInCallback() {

					@Override
					public void done(ParseUser user, ParseException e) {
						MBMonCompteActivity.this
								.setProgressBarIndeterminateVisibility(false);
						mask.setVisibility(View.GONE);
						if (user != null) {
							// Hooray! The user is logged in.
							Log.d("MyApp", "login succed.");
							SettingsManager.getInstance(
									MBMonCompteActivity.this)
									.setStringPreference(
											SettingsManager.PREF_USER_USERNAME,
											user.getUsername());
							SettingsManager.getInstance(
									MBMonCompteActivity.this)
									.setStringPreference(
											SettingsManager.PREF_USER_PWD,
											password.getText().toString());
							startMainActivity();
						} else {
							// Signup failed. Look at the ParseException to see
							// what happened.
							Log.d("MyApp", "login failed.");
							e.printStackTrace();
							showDialogMailSend("Attention",
									"Login et/ou mot de passe incorrects");
						}
					}
				});
			}
		});
		Button facebook = (Button) findViewById(R.id.facebook);
		facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EasyTracker easyTracker = EasyTracker
						.getInstance(MBMonCompteActivity.this);

				// MapBuilder.createEvent().build() returns a Map of event
				// fields and values
				// that are set and sent with the hit.
				easyTracker.send(MapBuilder.createEvent("ui_action", // Event
																		// category
																		// (required)
						"button_press", // Event action (required)
						"facebook_button", // Event label
						null) // Event value
						.build());
				MBMonCompteActivity.this
						.setProgressBarIndeterminateVisibility(true);
				mask.setVisibility(View.VISIBLE);
				ParseFacebookUtils.logIn(MBMonCompteActivity.this,
						new LogInCallback() {

							@Override
							public void done(ParseUser user, ParseException err) {
								MBMonCompteActivity.this
										.setProgressBarIndeterminateVisibility(false);
								mask.setVisibility(View.GONE);
								if (user == null) {
									Log.d("MyApp",
											"Uh oh. The user cancelled the Facebook login.");
									if (err != null) {
										err.printStackTrace();
									}
									showDialogMailSend("Attention",
											"Problème d'authentification");
								} else if (user.isNew()) {
									Log.d("MyApp",
											"Contact signed up and logged in through Facebook!");
									startMainActivity();
								} else {
									Log.d("MyApp",
											"Contact logged in through Facebook!");
									startMainActivity();
								}
							}
						});
			}
		});
		TextView forgotten = (TextView) findViewById(R.id.forgotten);
		forgotten.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EasyTracker easyTracker = EasyTracker
						.getInstance(MBMonCompteActivity.this);

				// MapBuilder.createEvent().build() returns a Map of event
				// fields and values
				// that are set and sent with the hit.
				easyTracker.send(MapBuilder.createEvent("ui_action", // Event
																		// category
																		// (required)
						"button_press", // Event action (required)
						"forgotten_button", // Event label
						null) // Event value
						.build());
				if (TextUtils.isEmpty(mail.getText().toString())) {
					showDialogMailSend("Attention",
							"Veuillez renseigner un mail pour réinitialiser votre mot de passe.");
				} else {
					MBMonCompteActivity.this
							.setProgressBarIndeterminateVisibility(true);
					mask.setVisibility(View.VISIBLE);
					ParseUser.requestPasswordResetInBackground(mail.getText()
							.toString(), new RequestPasswordResetCallback() {

						@Override
						public void done(ParseException e) {
							MBMonCompteActivity.this
									.setProgressBarIndeterminateVisibility(false);
							mask.setVisibility(View.GONE);
							if (e == null) {
								// An email was successfully sent with reset
								// instructions.
								showDialogMailSend("Information",
										"Un email vous a été envoyé pour réinitialiser votre mot de passe.");
							} else {
								// Something went wrong. Look at the
								// ParseException to see what's up.
								showDialogMailSend("Information",
										"Problème pour réinitialiser le mot de passe.");
								Log.d("MyApp", "Problem to reset password");
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void startMainActivity() {
		Intent intent = new Intent(MBMonCompteActivity.this,
				MBMainActivity.class);
		startActivity(intent);
		finish();
	}

	private void showDialogMailSend(String title, String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final ScrollView s_view = new ScrollView(getApplicationContext());
		final TextView t_view = new TextView(getApplicationContext());
		t_view.setText(message);
		t_view.setTextSize(14);
		s_view.addView(t_view);
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		t_view.setPadding(margin, margin, margin, margin);
		alert.setTitle(title);
		alert.setView(s_view);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		alert.show();
	}

}
