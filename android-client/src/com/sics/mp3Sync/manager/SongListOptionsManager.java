/**
 * 
 */
package com.sics.mp3Sync.manager;

import java.util.List;

import com.sics.mp3Sync.container.SongData;
import com.sics.mp3Sync.container.SongListOptions;


/**
 * @author Joachim
 *
 */
public class SongListOptionsManager {
	
	public SongListOptions createSongListOptions(List<SongData> serverData){
		//TODO vereinfachung
		int i=0;
		SongListOptions slOptions = new SongListOptions();
		
		if (serverData.size() > 0){
			for(SongData sd : serverData){
				slOptions.setFilepathOfItemOnServer(sd.getDataPathOnServer(), i);
				slOptions.setNameOfItem(sd.getDataName(), i);
				i++;
			}
		}
		
		return slOptions;
	}
}
