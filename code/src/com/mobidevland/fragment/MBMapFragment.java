package com.mobidevland.fragment;

import java.util.List;

import android.app.Application;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobidevland.MBMapActivity;
import com.mobidevland.R;
import com.mobidevland.data.Contact;

public class MBMapFragment extends MapFragment {

	private static final String TAG = MBMapFragment.class.getName();

	private GoogleMap mMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initUIComponents();
	}

	private MBMapActivity getMapActivity() {
		return (MBMapActivity) getActivity();
	}

	private void initUIComponents() {
		mMap = getMap();
		CameraPosition camPos = CameraPosition.fromLatLngZoom(
				getMapActivity().currentPosition, 10);
		CameraUpdate cu = CameraUpdateFactory.newCameraPosition(camPos);
		mMap.moveCamera(cu);
		mMap.setMyLocationEnabled(true);
		initInfoWindowAdapter();
	}

	private void initInfoWindowAdapter() {
		// Setting a custom info window adapter for the google map
		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {

				LayoutInflater inflater = (LayoutInflater) getActivity()
						.getApplicationContext().getSystemService(
								Application.LAYOUT_INFLATER_SERVICE);
				View v;
				v = inflater.inflate(R.layout.map_info_window, null);
				TextView titleWidget = (TextView) v
						.findViewById(R.id.infowindow_title);
				titleWidget.setText(marker.getTitle());

				return v;

			}
		});
	}

	public void refresh() {
		List<Contact> userList = getMapActivity().contactList;
		if (userList == null || userList.size() < 1) {
			return;
		}
		mMap.clear();
		LatLng position = mMap.getCameraPosition().target;
		Location currentLocation = new Location("Position on map");
		currentLocation.setLatitude(position.latitude);
		currentLocation.setLongitude(position.longitude);
		Location contactLocation = new Location("Contact location");
		com.google.android.gms.maps.model.LatLngBounds.Builder b = LatLngBounds
				.builder();
		b.include(position);
		for (Contact user : userList) {
			contactLocation.setLatitude(user.getLatitude());
			contactLocation.setLongitude(user.getLongitude());
			if (currentLocation.distanceTo(contactLocation) < 10000) {
				BitmapDescriptor icon;
				if ("Agence".equals(user.getCategory())) {
					icon = BitmapDescriptorFactory
							.fromResource(R.drawable.pin_point_agency);
				} else if ("Freelance".equals(user.getCategory())) {
					icon = BitmapDescriptorFactory
							.fromResource(R.drawable.pin_point_freelance);
				} else if ("Investisseurs".equals(user.getCategory())) {
					icon = BitmapDescriptorFactory
							.fromResource(R.drawable.pin_point_investors);
				} else if ("Ecole".equals(user.getCategory())) {
					icon = BitmapDescriptorFactory
							.fromResource(R.drawable.pin_point_school);
				} else if ("Etudiant(e)".equals(user.getCategory())) {
					icon = BitmapDescriptorFactory
							.fromResource(R.drawable.pin_point_students);
				} else {
					icon = BitmapDescriptorFactory
							.fromResource(R.drawable.pin_point_marker);
				}
				mMap.addMarker(new MarkerOptions().position(user.getPosition())
						.title(user.getLastname()).icon(icon));
				b.include(user.getPosition());
			}

		}
		LatLngBounds bounds = b.build();
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
				r.getDisplayMetrics());
		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,
				Math.round(px));
		mMap.animateCamera(cu);

	}

}
