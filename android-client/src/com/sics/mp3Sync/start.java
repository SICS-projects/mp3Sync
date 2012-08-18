package com.sics.mp3Sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sics.mp3Sync.container.SongData;
import com.sics.mp3Sync.container.SongListOptions;
import com.sics.mp3Sync.customAdapter.SyncFormListViewAdapter;
import com.sics.mp3Sync.eventListener.DownloadListenerInterface;
import com.sics.mp3Sync.manager.DatabaseManager;
import com.sics.mp3Sync.manager.SongListOptionsManager;
import com.sics.mp3Sync.threads.DownloadMp3ListTask;

import JGApps.MP3Synch.R;
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
    	this.downlodSongListWithServer();
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
        	this.downlodSongListWithServer();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void downlodSongListWithServer() {
		List<SongData> serverData;
		List<SongData> localSongData;
			//TODO: Merging algorithmus verbessern. Bekommt so nicht alle songs mit(contextswitch vor getdatalistfromserver)
//			int maxSongIDOnDatabase = DatabaseManager.getLastSongListIDFromDatabase();
			
//			HttpMetadataManager httpMetadataManager = new HttpMetadataManager(this); 
//			serverData = httpMetadataManager.getDataListFromServer();
			DownloadMp3ListTask downloadMp3ListTask = new DownloadMp3ListTask(this);
			downloadMp3ListTask.setOnMetadataDownloadFinished(new DownloadMetaDataFinished(this));
			downloadMp3ListTask.execute();
			
	}
    
    public void loadSonglistFromDatabase(){
    	DatabaseManager databaseManager = new DatabaseManager(this);
		List<SongData> localSongData = databaseManager.getSonglistFromDatabase();
		
		SongListOptionsManager songListOptionsManager = new SongListOptionsManager();
		SongListOptions slOptions = songListOptionsManager.createSongListOptions(localSongData);
		
		fillListView(slOptions);
	}
	
	 private void fillListView(SongListOptions slOptions) {
	    songsListView.setAdapter(new SyncFormListViewAdapter(this, this.getApplicationContext(), R.layout.synchfilelistviewitem, slOptions));
	 }
	 
	 private class DownloadMetaDataFinished implements DownloadListenerInterface{
		private Activity activity;
		 
		public DownloadMetaDataFinished(Activity activity){
			this.activity = activity;
		}
		 
		public void onDownloadFinished(Object... o) {
			// TODO Auto-generated method stub
			DatabaseManager databaseManager = new DatabaseManager(this.activity);
			databaseManager.saveNewSongs((List<SongData>)o[0]);
			List<SongData> localSongData = databaseManager.getSonglistFromDatabase();
			
			SongListOptionsManager songListOptionsManager = new SongListOptionsManager();
			SongListOptions slOptions = songListOptionsManager.createSongListOptions(localSongData);
			
			fillListView(slOptions);
		}

		public void onDownloadProgress(Integer progress) {
			// TODO Auto-generated method stub
			
		}
		 
	 }
}