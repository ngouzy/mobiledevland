package com.mobidevland;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.mobidevland.data.Contact;
import com.mobidevland.fragment.ContactsFragment;
import com.mobidevland.fragment.MBMapFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MBMapActivity extends MBBaseActivity implements LocationListener,
		ConnectionCallbacks, OnConnectionFailedListener {

	private static final String TAG = MBMapActivity.class.getName();
	private static final LatLng RENNES = new LatLng(48.113475, -1.675708);
	private static final int LOCATION_ACCURACY = 200;
	public static final LocationRequest LOCATION_REQUEST = LocationRequest
			.create().setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

	public LatLng currentPosition = RENNES;
	public List<Contact> contactList;

	private LocationClient mLocationClient;
	private Button mFiltreButton;
	private Button mListeButton;
	private Button mMapButton;
	private Button mTargetButton;
	private Button mRefreshButton;

	private MBMapFragment mMapFragment;
	private ContactsFragment mContactsFragment;

	public static void start(Context ctx) {
		Intent intent = new Intent(ctx, MBMapActivity.class);
		ctx.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mbmap);
		getActionBar().hide();
		FragmentManager fm = getFragmentManager();
		mMapFragment = (MBMapFragment) fm.findFragmentById(R.id.fragment_map);
		mContactsFragment = (ContactsFragment) fm
				.findFragmentById(R.id.fragment_contacts);
		initWidgets();
	}

	private void initWidgets() {
		mContactsFragment.getView().setVisibility(View.GONE);
		mFiltreButton = (Button) findViewById(R.id.filtreButton);

		mListeButton = (Button) findViewById(R.id.listeButton);
		mListeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switchMapListe();
			}
		});

		mMapButton = (Button) findViewById(R.id.mapButton);
		mMapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switchMapListe();
			}
		});

		mTargetButton = (Button) findViewById(R.id.targetButton);
		mTargetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GoogleMap mMap = mMapFragment.getMap();
				CameraPosition camPos = CameraPosition.fromLatLngZoom(
						currentPosition, mMap.getCameraPosition().zoom);
				CameraUpdate cu = CameraUpdateFactory.newCameraPosition(camPos);
				mMap.animateCamera(cu);
				refresh();
			}
		});

		mRefreshButton = (Button) findViewById(R.id.refreshButton);
		mRefreshButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				refresh();
			}
		});

	}

	private void switchMapListe() {
		if (mMapButton.getVisibility() == View.VISIBLE) {
			mMapButton.setVisibility(View.GONE);
			mListeButton.setVisibility(View.VISIBLE);
			mMapFragment.getView().setVisibility(View.VISIBLE);
			mContactsFragment.getView().setVisibility(View.GONE);

		} else {
			mMapButton.setVisibility(View.VISIBLE);
			mListeButton.setVisibility(View.GONE);
			mMapFragment.getView().setVisibility(View.GONE);
			mContactsFragment.getView().setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpLocationClientIfNeeded();
		mLocationClient.connect();
		refresh();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mLocationClient != null) {
			mLocationClient.disconnect();
		}
	}

	protected void setUpLocationClientIfNeeded() {
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(getApplicationContext(), this, // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		LatLng newCoordinates = new LatLng(location.getLatitude(),
				location.getLongitude());
		float[] results = new float[1];
		Location.distanceBetween(currentPosition.latitude,
				currentPosition.longitude, newCoordinates.latitude,
				newCoordinates.longitude, results);
		if (results[0] > LOCATION_ACCURACY) {
			currentPosition = newCoordinates;
			refresh();
		}
	}

	private void refresh() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");

		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> contactListParse,
					ParseException e) {
				if (e == null) {
					Log.d(TAG, "Retrieved " + contactListParse.size()
							+ " Contacts");
					contactList = new ArrayList<Contact>();
					for (ParseObject contactParse : contactListParse) {
						Contact contact = new Contact();
						contact.setFirstname(contactParse
								.getString("firstname"));
						contact.setLastname(contactParse.getString("lastname"));
						contact.setTitle(contactParse.getString("title"));
						contact.setPhone(contactParse.getString("phone"));
						contact.setEmail(contactParse.getString("email"));
						contact.setAddress(contactParse.getString("address"));
						contact.setZip(contactParse.getString("zip"));
						contact.setCity(contactParse.getString("city"));
						contact.setCompany(contactParse.getString("company"));
						contact.setCategory(contactParse.getString("category"));
						ParseGeoPoint position = contactParse
								.getParseGeoPoint("position");
						contact.setLatitude(position.getLatitude());
						contact.setLongitude(position.getLongitude());
						contactList.add(contact);
					}
					if (mMapFragment != null) {
						mMapFragment.refresh();
					}
					if (mContactsFragment != null) {
						mContactsFragment.refresh();
					}

				} else {
					Log.d(TAG, "Error: " + e.getMessage());
				}
			}
		});

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLocationClient.requestLocationUpdates(LOCATION_REQUEST, this); // LocationListener
	}

	@Override
	public void onDisconnected() {
		currentPosition = RENNES;
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		currentPosition = RENNES;
	}

}
