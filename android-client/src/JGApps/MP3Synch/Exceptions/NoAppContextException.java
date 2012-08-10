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
public class NoAppContextException extends Exception {
	public NoAppContextException(){
		
	}
	
	public void showDialog(){
		String httpServerURL;
		try {
			AlertDialogGUI.CreateAlertDialog("Error ..", "Internal Error! " + this.getStackTrace(), Global.getAppContext());
		} catch (NoAppContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
