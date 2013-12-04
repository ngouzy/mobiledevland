package com.mobidevland;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class MBApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "xfl7TlNoHQ8vAfsQaxPC8b8spetGl25P2OgjbQFO", "BkHddmDrNlZyz8mzjTJPm3OTD9atyFhvjgVh2dDo");
		ParseFacebookUtils.initialize("1400052673566650");
		PushService.setDefaultPushCallback(this, MBMainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.subscribe(this, "Pub", MBAdvertisingActivity.class);
		PushService.subscribe(this, "Article", MBNewsDetailsActivity.class);
		
	}

}
