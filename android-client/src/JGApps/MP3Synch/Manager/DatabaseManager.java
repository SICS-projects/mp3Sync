/**
 * 
 */
package JGApps.MP3Synch.Manager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.sics_android_sdk.Comunication.SqLiteController;

import JGApps.MP3Synch.Container.SongData;
import JGApps.MP3Synch.Helper.ListConverter;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * @author joachim
 *
 */
public class DatabaseManager {
	private Activity activity;
	
	public DatabaseManager(Activity activity){
		this.activity = activity;
	}
	
	public static Cursor getSonglistAsCursorFromDatabase(){
		return null;
}
	
	public List<SongData> getSonglistFromDatabase(){
		List<Hashtable<String, Object>> result = new ArrayList<Hashtable<String, Object>>();
		List<SongData> resultData = new ArrayList<SongData>();
		
		SqLiteController db = new SqLiteController(this.activity.getApplicationContext());
		
		db.createTableIfNotExists("songs", new String[] {"name", "filePath"}, null, false);
		result = db.selectAsHash("songs", new String[] {"_id", "name", "filePath"}, null, null, null, null, null);
		
		ListConverter converter = new ListConverter(result);
		resultData =  converter.convertToSongDataList();
		
		return resultData;
	}

	public void saveNewSongs(List<SongData> data) {
		SqLiteController db = new SqLiteController(this.activity.getApplicationContext());
		
		for(SongData sData : data){
			ContentValues values = new ContentValues();
			values.put("_id", sData.getID());
			values.put("name", sData.getDataName());
			values.put("filePath", sData.getDataPathOnServer());
			
			db.insert("songs",null, values);
		}
	}
}
