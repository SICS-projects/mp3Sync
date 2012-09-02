/**
 * 
 */
package JGApps.MP3Synch.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Joachim
 *
 */
public class PreferencesManager {
	
	public static String getPreferenceAsString(String key, String defaultValue, Context contect){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(contect);
		return preferences.getString(key, defaultValue);
	}
	
	public static Integer getPreferenceAsInteger(String key, Integer defaultValue, Context contect){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(contect);
		return preferences.getInt(key, defaultValue);
	}
	
	
}
