package JGApps.MP3Synch.Threads;

import java.util.List;

import android.os.Handler;
import android.os.Message;

import JGApps.MP3Synch.Container.SongListOptions;
import JGApps.MP3Synch.Interfaces.ISynchForm;

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
