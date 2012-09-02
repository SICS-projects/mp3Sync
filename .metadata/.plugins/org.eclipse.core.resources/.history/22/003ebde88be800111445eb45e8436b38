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
public class GlobalValueIsNullException extends Exception{
	public void showDialog(String nameOfGolbalValue){
		String httpServerURL;
		try {
			AlertDialogGUI.CreateAlertDialog("Error ..", "GlobalValue is Null:" + nameOfGolbalValue + this.getStackTrace(), Global.getAppContext());
		} catch (NoAppContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
