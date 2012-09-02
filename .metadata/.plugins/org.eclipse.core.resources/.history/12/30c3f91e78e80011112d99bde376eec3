package JGApps.MP3Synch.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnector extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "JGApps.MP3Synch";
    public static final String CONFIGURATION_TABLE_NAME = "Configuration";
    private static final String CONFIGURATION_TABLE_CREATE =
                "CREATE TABLE " + CONFIGURATION_TABLE_NAME + "(ID INTEGER NOT NULL, " +
                												"configuration CHAR(100) NOT NULL, " + 
                												"value CHAR(100) NOT NULL, " +
                												"PRIMARY KEY(ID), UNIQUE(ID))";
    

    public DBConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	this.createDBIfNotExists(db);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void createDBIfNotExists(SQLiteDatabase db){
		SQLiteDatabase checkDB = null;
    	try{
    		checkDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
    	}catch(Exception e){

    	}
	}
}
