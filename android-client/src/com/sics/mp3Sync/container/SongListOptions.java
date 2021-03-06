package com.sics.mp3Sync.container;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.sics.mp3Sync.misc.enums.ItemOptions;


public class SongListOptions {
	private Boolean HasChanged = false;
	
	private List<String> nameOfItem = new ArrayList<String>();
	private List<String> filepathOfItemOnServer = new ArrayList<String>();
	private Hashtable<String, Integer> optionsForJustOneLine = new Hashtable<String, Integer>();
	private List<Hashtable<String, ItemOptions.VALUE>> optionsForSonglist = new  ArrayList<Hashtable<String, ItemOptions.VALUE>>();
	
	
	
	public void addOptionForJustOneLine(Integer nrLine, String option){
		if (!this.optionsForJustOneLine.containsKey(option)){
			this.optionsForJustOneLine.put(option, nrLine);
		}
		else{
			this.optionsForJustOneLine.remove(option);
			this.optionsForJustOneLine.put(option, nrLine);
		}
		
	}
	
	public void addOptionForSonglistItem(Integer numberItem, String option, ItemOptions.VALUE optionValue){
		Hashtable<String, ItemOptions.VALUE> optionsForSonglist = new  Hashtable<String, ItemOptions.VALUE>();
		optionsForSonglist.put(option, optionValue);
		
		this.optionsForSonglist.add(numberItem, optionsForSonglist);
		
	}
	
	public Integer getOptionForJustOneLine(String option){
		Integer numberOfItem = -1;
		if (this.optionsForJustOneLine.containsKey(option)){
			numberOfItem = this.optionsForJustOneLine.get(option);
		}
		return(numberOfItem);
	}
	
	public Hashtable<String, ItemOptions.VALUE> getOptionsForSonglistItem(Integer numberItem){
		return(this.optionsForSonglist.get(numberItem));
	}
	
	public String getNameOfItem(Integer numberOfItem){
		return (this.nameOfItem.get(numberOfItem));
	}
	
	public void setNameOfItem(String nameOFItem, Integer numberOfItem){
		this.nameOfItem.add(numberOfItem, nameOFItem);
		
	}
	
	public String getFilepathOfItemOnServer(Integer numberOfItem){
		return (this.filepathOfItemOnServer.get(numberOfItem));
	}
	
	public void setFilepathOfItemOnServer(String filepathOfItem, Integer numberOfItem){
		this.filepathOfItemOnServer.add(numberOfItem, filepathOfItem);
		
	}
	
	public Integer getCount(){
		return nameOfItem.size();
	}
	
	public Boolean hasChanged(){
		synchronized(this.HasChanged){
			return this.HasChanged;
		}
	}
	
	public void Changed(){
		synchronized(this.HasChanged){
			if (this.HasChanged == true){
				this.HasChanged = false;
			}
			else{
				this.HasChanged = true;
			}
		}
	}
}
