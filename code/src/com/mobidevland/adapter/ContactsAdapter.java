package com.mobidevland.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobidevland.R;
import com.mobidevland.data.Contact;

public class ContactsAdapter extends BaseAdapter {

	protected static final String TAG = ContactsAdapter.class.getName();

	private List<Contact> contacts;
	private final Context mContext;

	public ContactsAdapter(final Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		if (contacts == null) {
			return 0;
		}
		return contacts.size();
	}

	@Override
	public Contact getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Contact item = getItem(position);
		ContactsViewHolder dataHolder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.contacts_list_item, null);
			dataHolder = new ContactsViewHolder();
			dataHolder.init(convertView);
			convertView.setTag(dataHolder);
		} else {
			dataHolder = (ContactsViewHolder) convertView.getTag();
		}
		dataHolder.update(item, position);

		return convertView;
	}

	/**
	 * Contact list view
	 */
	private class ContactsViewHolder {

		public View root;
		public TextView nameTextView;
		public TextView companyTextView;
		public ImageView ledIcon;
		public ImageView favorisIcon;
		public ImageView chatIcon;
		public ImageView addIcon;

		public void init(View root) {
			this.root = root;
			nameTextView = (TextView) root.findViewById(R.id.contact_name);
			companyTextView = (TextView) root
					.findViewById(R.id.contact_company);
			ledIcon = (ImageView) root.findViewById(R.id.contact_led);
			favorisIcon = (ImageView) root.findViewById(R.id.contact_favoris);
			chatIcon = (ImageView) root.findViewById(R.id.contact_chat);
			addIcon = (ImageView) root.findViewById(R.id.contact_add);
		}

		public void update(final Contact contact, int position) {
			nameTextView.setText(contact.getFirstname() + " "
					+ contact.getLastname());
			companyTextView.setText(contact.getCompany());
			if (position % 2 == 0) {
				root.setBackgroundColor(root.getResources().getColor(
						R.color.white));
				favorisIcon.setImageResource(R.drawable.icon_favoris_normal);
				chatIcon.setImageResource(R.drawable.icon_chat_normal);
				addIcon.setImageResource(R.drawable.icon_add_normal);
			} else {
				root.setBackgroundColor(root.getResources().getColor(
						R.color.green));
				favorisIcon.setImageResource(R.drawable.icon_favoris_pressed);
				chatIcon.setImageResource(R.drawable.icon_chat_pressed);
				addIcon.setImageResource(R.drawable.icon_add_pressed);
			}
			if ("Agence".equals(contact.getCategory())) {
				ledIcon.setImageResource(R.drawable.led_red);
			} else if ("Freelance".equals(contact.getCategory())) {
				ledIcon.setImageResource(R.drawable.led_pink);
			} else if ("Investisseurs".equals(contact.getCategory())) {
				ledIcon.setImageResource(R.drawable.led_green);
			} else if ("Ecole".equals(contact.getCategory())) {
				ledIcon.setImageResource(R.drawable.led_blue);
			} else if ("Etudiant(e)".equals(contact.getCategory())) {
				ledIcon.setImageResource(R.drawable.led_yellow);
			} else {
				ledIcon.setImageResource(R.drawable.led_orange);
			}
		}
	}

	public void setData(final List<Contact> contacts) {
		this.contacts = contacts;
		notifyDataSetChanged();
	}

}