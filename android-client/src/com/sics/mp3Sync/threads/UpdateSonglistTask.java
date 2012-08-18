/**
 * 
 */
package com.sics.mp3Sync.threads;

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
//		GUIManager.mergeSongListWithServer();
	}
	
	

}
