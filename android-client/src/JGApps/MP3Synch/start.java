package JGApps.MP3Synch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sics_android_sdk.Exceptions.HttpPortNotValidException;
import com.sics_android_sdk.Exceptions.WrongHttpReturnStateException;
import com.sics_android_sdk.Exceptions.WrongHttpServerURLException;

import JGApps.MP3Synch.Container.SongData;
import JGApps.MP3Synch.Container.SongListOptions;
import JGApps.MP3Synch.CustomAdapter.SyncFormListViewAdapter;
import JGApps.MP3Synch.Manager.DatabaseManager;
import JGApps.MP3Synch.Manager.HttpMetadataManager;
import JGApps.MP3Synch.Manager.SongListOptionsManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class start extends Activity {
	
SongListOptions songListOptions = new SongListOptions();
	private ListView songsListView;
	SyncFormListViewAdapter listViewAdapter = null;
	//Hashtable<String, String> songListOptions = new Hashtable<String, String>(); 
	List<HashMap<String, String>> httpValues = new ArrayList<HashMap<String, String>>();
	ArrayAdapter<String> ad = null;
	 
	 Button tv = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        getGuiElements();
        
        loadAndMergeSongs();
    }
    
    private void getGuiElements(){
    	songsListView = (ListView)findViewById(R.id.songsList);
    }
    
    private void loadAndMergeSongs() {
    	
    	this.loadSonglistFromDatabase();
    	this.mergeSongListWithServer();
    	/*UpdateSonglistTask songlistUpdater = new UpdateSonglistTask();
    	songlistUpdater.execute(null);*/
    }
    
   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenumain, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.preferencesMainItem:
        	Intent intent = new Intent(this, PreferencesActivity.class);
        	startActivity(intent);
            return true;
        case R.id.helpItem:
            //showHelp();
            return true;
        case R.id.reloadList:
        	this.mergeSongListWithServer();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void mergeSongListWithServer() {
		List<SongData> serverData;
		List<SongData> localSongData;
		try {
			//TODO: Merging algorithmus verbessern. Bekommt so nicht alle songs mit(contextswitch vor getdatalistfromserver)
//			int maxSongIDOnDatabase = DatabaseManager.getLastSongListIDFromDatabase();
			
			HttpMetadataManager httpMetadataManager = new HttpMetadataManager(this); 
			serverData = httpMetadataManager.getDataListFromServer();
			
			DatabaseManager databaseManager = new DatabaseManager(this);
			databaseManager.saveNewSongs(serverData);
			localSongData = databaseManager.getSonglistFromDatabase();
			
			SongListOptionsManager songListOptionsManager = new SongListOptionsManager();
			SongListOptions slOptions = songListOptionsManager.createSongListOptions(localSongData);
			
			fillListView(slOptions);
		} catch (HttpPortNotValidException e) {
			e.printStackTrace();
		} catch (WrongHttpReturnStateException e) {
			e.printStackTrace();
		} catch (WrongHttpServerURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public void loadSonglistFromDatabase(){
    	DatabaseManager databaseManager = new DatabaseManager(this);
		List<SongData> localSongData = databaseManager.getSonglistFromDatabase();
		
		SongListOptionsManager songListOptionsManager = new SongListOptionsManager();
		SongListOptions slOptions = songListOptionsManager.createSongListOptions(localSongData);
		
		fillListView(slOptions);
	}
	
	 private void fillListView(SongListOptions slOptions) {
	    songsListView.setAdapter(new SyncFormListViewAdapter(this, R.layout.synchfilelistviewitem, slOptions));
	 }
}