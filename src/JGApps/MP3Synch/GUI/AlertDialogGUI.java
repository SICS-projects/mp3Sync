/**
 * 
 */
package JGApps.MP3Synch.GUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import JGApps.MP3Synch.Exceptions.NoAppContextException;
import JGApps.MP3Synch.Global.Global;




/**
 * @author Joachim
 *
 */
public class AlertDialogGUI {
	public static void CreateAlertDialog(String title, String message, Context contect){ 
		
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(contect).create();
		
		alertDialog.setTitle(title);  
		alertDialog.setMessage(message);  
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
		  public void onClick(DialogInterface dialog, int which) {  
		    return;  
		} });   
		alertDialog.show();  
	   
	}
}
