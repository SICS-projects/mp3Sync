/**
 * 
 */
package JGApps.MP3Synch.Threads;

import JGApps.MP3Synch.Exceptions.NoAppContextException;
import JGApps.MP3Synch.Exceptions.WrongHttpReturnStateException;
import JGApps.MP3Synch.Global.Global;
import JGApps.MP3Synch.Manager.GUIManager;
import JGApps.MP3Synch.Manager.PreferencesManager;
import android.os.AsyncTask;

/**
 * @author joachim
 *
 */
public class UpdateSonglistTask extends AsyncTask<String, Integer, byte[]> {

	private Boolean stopMerging = false;
	@Override
	protected byte[] doInBackground(String... params) {
		
		while(this.stopMerging == false){
			this.publishProgress();

			
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress){
		GUIManager.mergeSongListWithServer();
	}
	
	

}
