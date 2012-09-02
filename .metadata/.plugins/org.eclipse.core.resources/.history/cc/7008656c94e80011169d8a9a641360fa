/**
 * 
 */
package JGApps.MP3Synch.Manager;

import java.util.List;

import JGApps.MP3Synch.Container.SongData;
import JGApps.MP3Synch.Container.SongListOptions;

/**
 * @author Joachim
 *
 */
public class SongListOptionsManager {
	public static SongListOptions createSongListOptions(List<SongData> serverData){
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
