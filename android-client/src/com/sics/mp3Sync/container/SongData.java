package com.sics.mp3Sync.container;

public class SongData{
	private int ID = -1;
	private String dataName = "";
	private String dataPathOnServer = "";
	
	public SongData(){
		
	}
	
	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataPathOnServer() {
		return dataPathOnServer;
	}

	public void setDataPathOnServer(String dataPathOnServer) {
		this.dataPathOnServer = dataPathOnServer;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}
	
