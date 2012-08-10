package JGApps.MP3Synch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import JGApps.MP3Synch.Comunication.HttpMetadataController;
import JGApps.MP3Synch.Container.SongData;
import JGApps.MP3Synch.Container.SongListOptions;
import JGApps.MP3Synch.CustomAdapter.SyncFormListViewAdapter;
import JGApps.MP3Synch.Exceptions.AppContextAlreadySetException;
import JGApps.MP3Synch.Exceptions.HttpPortNotValidException;
import JGApps.MP3Synch.Exceptions.NoAppContextException;
import JGApps.MP3Synch.Exceptions.WrongHttpReturnStateException;
import JGApps.MP3Synch.Global.Global;
import JGApps.MP3Synch.Manager.GUIManager;
import JGApps.MP3Synch.Manager.SongListOptionsManager;
import JGApps.MP3Synch.Threads.UpdateSonglistTask;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class start extends ListActivity {
	
SongListOptions songListOptions = new SongListOptions();
	
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
        
        initProgram();
    }
    
    private void initProgram() {
    			Global.setAppContext(this);
	
    	
    	GUIManager.loadSonglistFromDatabase();
    	GUIManager.mergeSongListWithServer();
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
        	GUIManager.mergeSongListWithServer();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}