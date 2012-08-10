/**
 * 
 */
package JGApps.MP3Synch.Manager;

import java.util.Hashtable;
import java.util.List;

import android.app.ListActivity;

import JGApps.MP3Synch.R;
import JGApps.MP3Synch.Comunication.HttpMetadataController;
import JGApps.MP3Synch.Container.SongData;
import JGApps.MP3Synch.Container.SongListOptions;
import JGApps.MP3Synch.CustomAdapter.SyncFormListViewAdapter;
import JGApps.MP3Synch.Exceptions.HttpPortNotValidException;
import JGApps.MP3Synch.Exceptions.NoAppContextException;
import JGApps.MP3Synch.Exceptions.WrongHttpReturnStateException;
import JGApps.MP3Synch.Global.Global;

/**
 * @author Joachim
 *
 */
public class GUIManager {
	public static void mergeSongListWithServer() {
		List<SongData> serverData;
		List<SongData> localSongData;
		try {
			//TODO: Merging algorithmus verbessern. Bekommt so nicht alle songs mit(contextswitch vor getdatalistfromserver)
//			int maxSongIDOnDatabase = DatabaseManager.getLastSongListIDFromDatabase();
			
			
			serverData = HttpMetadataManager.getDataListFromServer();
			
			DatabaseManager.saveNewSongs(serverData);
			
			localSongData = DatabaseManager.getSonglistFromDatabase();
			
			SongListOptions slOptions = SongListOptionsManager.createSongListOptions(localSongData);
			
			fillListView(slOptions);
		} catch (NoAppContextException e) {
			e.showDialog();
			e.printStackTrace();
		} catch (HttpPortNotValidException e) {
			e.showDialog();
			e.printStackTrace();
		} catch (WrongHttpReturnStateException e) {
			e.showDialog();
			e.printStackTrace();
		}
	}
	
	public static void loadSonglistFromDatabase(){

		List<SongData> localSongData = DatabaseManager.getSonglistFromDatabase();
		
		SongListOptions slOptions = SongListOptionsManager.createSongListOptions(localSongData);
		
		try {
			fillListView(slOptions);
		} catch (NoAppContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 private static void fillListView(SongListOptions slOptions) throws NoAppContextException{
		 ListActivity lActivity = (ListActivity)Global.getAppContext();
	    ((ListActivity)Global.getAppContext()).setListAdapter(new SyncFormListViewAdapter(lActivity, R.layout.synchfilelistviewitem, slOptions));
	 }
	 

}
