package com.mobidevland.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mobidevland.MBMainActivity;
import com.mobidevland.MBMapActivity;
import com.mobidevland.MBNewsActivity;
import com.mobidevland.MBTouchDetailActivity;
import com.mobidevland.R;
import com.mobidevland.adapter.ContactsAdapter;
import com.mobidevland.business.net.ContactsManager;
import com.mobidevland.data.Contact;

public class ContactsFragment extends Fragment {

	private static final String TAG = ContactsFragment.class.getName();

	protected ListView mListView;

	private ContactsAdapter mContactsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	private MBMapActivity getMapActivity() {
		return (MBMapActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contact_list,
				container, false);
		mListView = (ListView) rootView.findViewById(R.id.contact_list_view);
		refresh();
		return rootView;
	}

	public void refresh() {
		if (mContactsAdapter == null) {
			mContactsAdapter = new ContactsAdapter(getMapActivity());
		}
		if (mListView != null) {
			mListView.setAdapter(mContactsAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					Contact contact = mContactsAdapter.getItem(position);
					ContactsManager.getInstance().setSelectedContact(contact);
					startActivity(new Intent(getActivity(), MBTouchDetailActivity.class));
				}
			});
		}
		List<Contact> contacts = getMapActivity().contactList;
		if (contacts != null) {
			mContactsAdapter.setData(contacts);
		}
	}

}
