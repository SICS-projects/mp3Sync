/**
 * 
 */
package JGApps.MP3Synch.Comunication;

import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;

import JGApps.MP3Synch.Container.SongData;
import JGApps.MP3Synch.Exceptions.HttpPortNotValidException;
import JGApps.MP3Synch.Exceptions.WrongHttpReturnStateException;

/**
 * @author joachim
 *
 */
public interface DataAccessInterface {
	public List<Hashtable<String, Object>> selectAsHash(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) throws HttpPortNotValidException, WrongHttpReturnStateException;
	public long update(String table, ContentValues values, String whereClause, String[] whereArgs, boolean useInsert);
	public long insert(String table, String nullColumnHack, ContentValues values);
	public void delete(String table);
	
}
