package com.sics.mp3Sync.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import com.sics.mp3Sync.container.SongData;
import com.sics.mp3Sync.eventListener.DownloadListenerInterface;
import com.sics.mp3Sync.manager.HttpMetadataManager;
import com.sics_android_sdk.Comunication.HttpMetadataController;
import com.sics_android_sdk.Exceptions.HttpPortNotValidException;
import com.sics_android_sdk.Exceptions.WrongHttpReturnStateException;
import com.sics_android_sdk.Exceptions.WrongHttpServerURLException;
import com.sics_android_sdk.Exceptions.WrongLoginnameOrPasswordException;
import com.sics_android_sdk.Helper.HttpServerConnector;

import android.app.Activity;
import android.os.AsyncTask;

public class DownloadMp3ListTask extends AsyncTask<String, Integer, byte[]>{
	private String address = "";
	private Integer port;
	private Activity activity;
	
	private DownloadListenerInterface onDownloadFinished;
	
	private List<SongData> codedMp3Files = new ArrayList<SongData>();
	
	public DownloadMp3ListTask(Activity activity){
		this.address = address;
		this.port = port;
		
		this.activity = activity;
	}
	
    protected byte[] doInBackground(String... metadata) {
    	HttpMetadataManager httpMetadataManager = new HttpMetadataManager(this.activity);
//    	HttpServerConnector httpServerConnector = new HttpServerConnector(address, port);
		
//    	while (httpServerConnector == null){
//			
//		}
//		if(httpServerConnector.getDataList())
//		{
    	try {
			codedMp3Files = httpMetadataManager.getDataListFromServer();
		} catch (HttpPortNotValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongHttpReturnStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongHttpServerURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//				xmlData = EntityUtils.toString(rp.getEntity());
//		}
//		else {
//			throw new WrongHttpReturnStateException();
//		}
//				
    	if (onDownloadFinished != null){
    		this.onDownloadFinished.onDownloadFinished(codedMp3Files);
    	}
		return new byte[4];
		
    }
    
    public void setOnMetadataDownloadFinished(DownloadListenerInterface onDownloadFinished){
    	this.onDownloadFinished = onDownloadFinished;
    }
}
