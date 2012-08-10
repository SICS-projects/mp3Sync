/**
 * 
 */
package JGApps.MP3Synch.Helper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import JGApps.MP3Synch.Container.SongData;
import JGApps.MP3Synch.Misc.Enums.DataFields;

/**
 * @author joachim
 *
 */
public class ListConverter {
	private List<Hashtable<String, Object>> data = new ArrayList<Hashtable<String, Object>>();
	
	public ListConverter(List<Hashtable<String, Object>> data){
		this.data = data;
	}
	
	public List<SongData> convertToSongDataList(){
		List<SongData> songdataList = new ArrayList<SongData>();
		
		for(Hashtable<String, Object> dataPerLine : data){
			SongData serverData = new SongData();
			
			serverData.setID(Integer.parseInt((String)dataPerLine.get(DataFields.ID)));
			serverData.setDataName((String)dataPerLine.get(DataFields.NAME));
			serverData.setDataPathOnServer((String)dataPerLine.get(DataFields.FILEPATH));
			
			songdataList.add(serverData);
		}
		
		return songdataList;
	}
}
