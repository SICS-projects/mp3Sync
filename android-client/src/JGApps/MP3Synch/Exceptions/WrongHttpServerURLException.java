/**
 * 
 */
package JGApps.MP3Synch.Exceptions;

import JGApps.MP3Synch.GUI.AlertDialogGUI;
import JGApps.MP3Synch.Global.Global;

/**
 * @author joachim
 *
 */
public class WrongHttpServerURLException extends Exception{
	public WrongHttpServerURLException(){
		
	}

	public void showDialog(){
		try {
			AlertDialogGUI.CreateAlertDialog("Error ..", "Wrong HTTP Server URL! ", Global.getAppContext());
		} catch (NoAppContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.showDialog();
		}
	}
}
