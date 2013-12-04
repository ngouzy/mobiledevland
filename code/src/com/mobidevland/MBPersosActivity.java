package com.mobidevland;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobidevland.commons.SettingsManager;

public class MBPersosActivity extends MBBaseActivity {
	private Button editButton;
	private LinearLayout formView;
	private EditText firstnameEditText;
	private EditText lastnameEditText;
	private EditText emailEditText;
	private EditText companyEditText;
	private EditText addressEditText;
	private EditText zipEditText;
	private EditText cityEditText;

	private boolean editMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_persos);
		getActionBar().hide();
		editButton = (Button) findViewById(R.id.persos_edit_action);
		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editMode) {
					SettingsManager sm = SettingsManager
							.getInstance(MBPersosActivity.this);
					sm.setStringPreference(SettingsManager.PREF_USER_FIRSTNAME,
							firstnameEditText.getText().toString());
					sm.setStringPreference(SettingsManager.PREF_USER_LASTNAME,
							lastnameEditText.getText().toString());
					sm.setStringPreference(SettingsManager.PREF_USER_EMAIL,
							emailEditText.getText().toString());
					sm.setStringPreference(SettingsManager.PREF_USER_COMPANY,
							companyEditText.getText().toString());
					sm.setStringPreference(SettingsManager.PREF_USER_ADDRESS,
							addressEditText.getText().toString());
					sm.setStringPreference(SettingsManager.PREF_USER_ZIP,
							zipEditText.getText().toString());
					sm.setStringPreference(SettingsManager.PREF_USER_CITY,
							cityEditText.getText().toString());
				}
				toggleEditViewMode(!editMode);

			}
		});
		formView = (LinearLayout) findViewById(R.id.persos_form);
		firstnameEditText = (EditText) findViewById(R.id.persos_firstname);
		lastnameEditText = (EditText) findViewById(R.id.persos_lastname);
		emailEditText = (EditText) findViewById(R.id.persos_email);
		companyEditText = (EditText) findViewById(R.id.persos_company);
		addressEditText = (EditText) findViewById(R.id.persos_address);
		zipEditText = (EditText) findViewById(R.id.persos_zip);
		cityEditText = (EditText) findViewById(R.id.persos_city);
		toggleEditViewMode(false);
	}

	private void toggleEditViewMode(boolean editMode) {
		this.editMode = editMode;

		int childcount = formView.getChildCount();
		for (int i = 0; i < childcount; i++) {
			EditText editText = (EditText) formView.getChildAt(i);
			editText.setEnabled(editMode);
		}
		if (editMode) {
			editButton.setText("enregistrer");
		} else {
			editButton.setText("modifier");
		}
		firstnameEditText.requestFocus();
	}

	@Override
	protected void onResume() {
		super.onResume();
		SettingsManager sm = SettingsManager.getInstance(this);
		String firstname = sm
				.getStringPreference(SettingsManager.PREF_USER_FIRSTNAME);
		String lastname = sm
				.getStringPreference(SettingsManager.PREF_USER_LASTNAME);
		String email = sm.getStringPreference(SettingsManager.PREF_USER_EMAIL);
		String company = sm
				.getStringPreference(SettingsManager.PREF_USER_COMPANY);
		String address = sm
				.getStringPreference(SettingsManager.PREF_USER_ADDRESS);
		String zip = sm.getStringPreference(SettingsManager.PREF_USER_ZIP);
		String city = sm.getStringPreference(SettingsManager.PREF_USER_CITY);

		// ParseUser user = ParseUser.getCurrentUser();
		firstnameEditText.setText(firstname, TextView.BufferType.EDITABLE);
		lastnameEditText.setText(lastname, TextView.BufferType.EDITABLE);
		emailEditText.setText(email, TextView.BufferType.EDITABLE);
		companyEditText.setText(company, TextView.BufferType.EDITABLE);
		addressEditText.setText(address, TextView.BufferType.EDITABLE);
		zipEditText.setText(zip, TextView.BufferType.EDITABLE);
		cityEditText.setText(city, TextView.BufferType.EDITABLE);
	}

}
