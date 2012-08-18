/**
 * 
 */
package com.sics.mp3Sync.manager;

import java.util.Hashtable;
import java.util.List;

import android.app.Activity;

import com.sics.mp3Sync.container.SongData;
import com.sics.mp3Sync.helper.ListConverter;
import com.sics_android_sdk.Comunication.HttpMetadataController;
import com.sics_android_sdk.Exceptions.HttpPortNotValidException;
import com.sics_android_sdk.Exceptions.WrongHttpReturnStateException;
import com.sics_android_sdk.Exceptions.WrongHttpServerURLException;
import com.sics_android_sdk.Manager.PreferencesManager;

import JGApps.MP3Synch.R;

/**
 * @author joachim
 *
 */
public class HttpMetadataManager {
	private Activity activity;
	
	public HttpMetadataManager(Activity activity){
		this.activity = activity;
	}
	
	public List<SongData> getDataListFromServer() throws HttpPortNotValidException, WrongHttpReturnStateException, WrongHttpServerURLException{
    	//TODO: Besseren namen finden f�r PreferencesManagement
		String httpServerURL = PreferencesManager.getPreferenceAsString(this.activity.getApplicationContext().getString(R.string.http_server_preference_host_key), "HTTP://", this.activity.getApplicationContext());
		//Integer httpServerPort = Integer.parseInt(PreferencesManagment.getPreferenceAsString(Global.getAppContext().getString(R.string.ftp_server_preference_port_key), "0", Global.getAppContext()));
		
		HttpMetadataController httpController = new HttpMetadataController(httpServerURL);
		List<Hashtable<String, Object>> dataAsHash = httpController.selectAsHash(null, null, null, null, null, null, null);
		
		ListConverter converter = new ListConverter(dataAsHash); 
		return converter.convertToSongDataList();
	}

	
}
