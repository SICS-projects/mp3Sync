/**
 * 
 */
package com.sics.mp3Sync;

import JGApps.MP3Synch.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * @author Joachim
 *
 */
public class PreferencesActivity extends PreferenceActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}
}
