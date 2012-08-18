package com.sics.mp3Sync.threads;

import com.sics.mp3Sync.container.SongListOptions;
import com.sics.mp3Sync.interfaces.ISynchForm;

import android.os.Handler;
import android.os.Message;


public class ScanChangesList implements Runnable{
	SongListOptions listToScan = null;
	ISynchForm function;
	
	public ScanChangesList(SongListOptions listToScan, ISynchForm function){
		this.listToScan = listToScan;
		this.function = function;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
			while(true){
				if (listToScan.hasChanged() == true){

					handler.sendEmptyMessage(0);
					
					listToScan.Changed();
				}
			}
	}
	
	private Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							// Hide dialog
							//pd.dismiss();
				 
							// Set the text
							function.refreshContent();
						}
					};
}
