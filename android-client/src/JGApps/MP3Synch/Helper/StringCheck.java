/**
 * 
 */
package JGApps.MP3Synch.Helper;

import android.R.string;
import JGApps.MP3Synch.Exceptions.WrongHttpServerURLException;

/**
 * @author joachim
 *
 */
public class StringCheck {
	public static Boolean checkForHttp(String stringToCheck){
		if (stringToCheck.startsWith("[http://]")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static String correctStringForHttp(String stringToCheck) throws WrongHttpServerURLException{
		String[] stringToCheck_parts;
		
		//Definition: Wenn der COnnectionString aus weniger als 7 Zeichen besteht ist ein "reparieren des Strings 
		//auf den richten Wert nicht möglich
		stringToCheck = stringToCheck.trim();
		
		if (stringToCheck.length() > 7){
			stringToCheck_parts = stringToCheck.split("(://)");
		}
		else{
			throw new WrongHttpServerURLException();
		}
		
		//wenn im array bereich 0 etwas drinnen steht und dieser wert der richtige wert ist
		//Note hiert auf http prüfen weil :// oben weggeschnitten wurde durch split. Daher wird :// auf wieder hinzugenommen
		if (stringToCheck_parts.length > 0 && stringToCheck_parts[0].toLowerCase().equals("http")){
			return stringToCheck_parts[0] + "://" + stringToCheck_parts[1];
		}else{
			//DOTO: Pattern matching klasse
			return "http://" + stringToCheck_parts[0];
		}
	}
}
