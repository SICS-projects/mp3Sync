/**
 * 
 */
package JGApps.MP3Synch.Exceptions;

import JGApps.MP3Synch.R;
import JGApps.MP3Synch.GUI.AlertDialogGUI;
import JGApps.MP3Synch.Global.Global;
import JGApps.MP3Synch.Manager.PreferencesManager;

/**
 * @author Joachim
 *
 */
public class HttpPortNotValidException extends Exception {
	public HttpPortNotValidException(){
		
	}
	
	public void showDialog(){
		String httpServerURL;
		try {
			httpServerURL = PreferencesManager.getPreferenceAsString(Global.getAppContext().getString(R.string.ftp_server_preference_host_key), "HTTP://", Global.getAppContext());
			AlertDialogGUI.CreateAlertDialog("Error ..", "Problem with Http connection. Host is: \"" + httpServerURL + "\"", Global.getAppContext());
		} catch (NoAppContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
