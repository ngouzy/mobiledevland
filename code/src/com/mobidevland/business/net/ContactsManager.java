package com.mobidevland.business.net;

import com.mobidevland.data.Contact;

public class ContactsManager {

	private static ContactsManager instance;

	private Contact selectedContact;

	public static ContactsManager getInstance() {
		if (instance == null) {
			instance = new ContactsManager();
		}
		return instance;
	}

	public Contact getSelectedContact() {
		return selectedContact;
	}

	public void setSelectedContact(Contact selectedContact) {
		this.selectedContact = selectedContact;
	}

}
