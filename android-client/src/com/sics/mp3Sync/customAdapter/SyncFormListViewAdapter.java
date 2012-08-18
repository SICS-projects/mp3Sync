package com.sics.mp3Sync.customAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.sics.mp3Sync.container.SongListOptions;
import com.sics.mp3Sync.eventListener.DownloadListenerInterface;
import com.sics.mp3Sync.manager.FtpServerCommunicationManager;
import com.sics.mp3Sync.misc.enums.ItemOptions;
import com.sics.mp3Sync.threads.DownloadDataTask;
import com.sics.mp3Sync.threads.MP3PlayerTask;

import android.media.MediaPlayer;
import android.os.Environment;
import android.view.View;
import JGApps.MP3Synch.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SyncFormListViewAdapter extends ArrayAdapter {
	private int countElements = -1;
	private static int clickedItem = -1;
	private Context context;
	private Activity activity;
	private SongListOptions songlistOptions = null;
	
	private Button playsongButton;
	private Button pausesongButton;
	private Button stopsongButton;
	private Button replaySongButton;
	
	private LinearLayout playerButtonsLayout;
	
	private Button songInfoButton;
	
	private MediaPlayer mediaPlayer = new MediaPlayer();
	
	
	private DownloadDataTask downloadTask = null;
	
	public SyncFormListViewAdapter(Activity activity, Context context, int textViewResourceId, SongListOptions songListOptions) {
		super(context, textViewResourceId);
		this.context = context;
		this.activity = activity;
		this.songlistOptions = songListOptions;
	}
	
	public int getCount() {
		
		return songlistOptions.getCount();
	}

	public Object getItem(int position) {
		
		return new String ("oioi");
	}

	public long getItemId(int position) {
		
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		LinearLayout rowLayout;

		//final Tweet tweet = elements.get(position);
		if (convertView == null) {
			rowLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.synchformitem, parent, false);
		} else {
			rowLayout = (LinearLayout) convertView;
		}

		collectPlayerButtons(rowLayout);
		
		final SeekBar mSeekBar = (SeekBar) rowLayout.findViewById(R.id.songSeekBar);
		final TextView connectionInfo = (TextView) rowLayout.findViewById(R.id.connectionInfo);
		
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			 
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				if (downloadTask.isDownloading()){
					mSeekBar.setProgress(0);
				}
				else{
					if (fromUser){
//						int timePosition = (Global.getMediaPlayer().getDuration()/100) * progress;
						
	//					player.stop();
						
//						Global.getMediaPlayer().seekTo(timePosition);
						
	//					player.start();
					}
				}
				// TODO Auto-generated method stub
				
			}
		});
		
		if (songlistOptions.getOptionForJustOneLine(ItemOptions.UNIC_PLAYSONG_STRING) == position){
			mSeekBar.setVisibility(View.VISIBLE);
			
			playerButtonsLayout.setVisibility(View.VISIBLE);
			
			playsongButton.setVisibility(View.GONE);
		}
		else{
			mSeekBar.setVisibility(View.GONE);
			
			playerButtonsLayout.setVisibility(View.GONE);
			
			playsongButton.setVisibility(View.VISIBLE);
		}

		playsongButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				playSong(position);
			}
		});
		
		replaySongButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				playSong(position);
			}
			
		});
		
		stopsongButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Global.getMediaPlayer().stop();
				
//					downloadTask.StopExecuting();
				
				//Here i do not need a thread to update the GUI. So i can do it directly in here
				pausesongButton.setVisibility(View.GONE);
				stopsongButton.setVisibility(View.GONE);
				replaySongButton.setVisibility(View.VISIBLE);
			}
		});
		
		pausesongButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Global.getMediaPlayer().pause();
				
				//Here i do not need a thread to update the GUI. So i can do it directly in here
				pausesongButton.setVisibility(View.GONE);
				stopsongButton.setVisibility(View.GONE);
				replaySongButton.setVisibility(View.VISIBLE);
			}
			
		});
        
		
		/*syncButton.setClickable(true);
		syncButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Hashtable<String, ItemOptions.VALUE> option = songlistOptions.getOptionsForSonglistItem(position);
				
				if (option.get(ItemOptions.SYNC_STRING).equals(ItemOptions.VALUE.OFF)){
					option.remove(ItemOptions.SYNC_STRING);
					option.put(ItemOptions.SYNC_STRING,ItemOptions.VALUE.ON );
					 
					syncButton.setBackgroundResource(R.drawable.x_red);
				}
				else{
					option.remove(ItemOptions.SYNC_STRING);
					option.put(ItemOptions.SYNC_STRING,ItemOptions.VALUE.OFF );
					
					syncButton.setBackgroundResource(R.drawable.hackerl_green);
				}
				
				songlistOptions.Changed();
				
			}
		});*/
		
		TextView songName = (TextView) rowLayout.findViewById(R.id.songName);
		
		
		songName.setText(this.songlistOptions.getNameOfItem(position));
		return rowLayout;
	}
	
	private void playSong(final int position){
		FtpServerCommunicationManager ftpServerCommunicationManager = new FtpServerCommunicationManager(this.activity);
		ftpServerCommunicationManager.downloadFileTo(this.songlistOptions.getFilepathOfItemOnServer(position), 
													 this.songlistOptions.getNameOfItem(position) + "mp3").setDownloadFinishedListener(new DownloadFinished());
	}
	
	private void collectPlayerButtons(LinearLayout rowLayout){
		playsongButton = (Button) rowLayout.findViewById(R.id.startPlayer);
		pausesongButton = (Button) rowLayout.findViewById(R.id.pausePlayer);
		stopsongButton = (Button) rowLayout.findViewById(R.id.stopPlayer);
		replaySongButton = (Button) rowLayout.findViewById(R.id.playPlayer);
		
		playerButtonsLayout = (LinearLayout) rowLayout.findViewById(R.id.playerButtons);
		
		songInfoButton = (Button) rowLayout.findViewById(R.id.songInfo);
	}
	
	private class DownloadFinished implements DownloadListenerInterface{

		public void onDownloadFinished(Object... o) {
			// TODO Auto-generated method stub
			String PATH = Environment.getExternalStorageDirectory()
	                + "/Download/mp3/";
			String filenameLocal = (String)o[0];

			SyncFormListViewAdapter.this.playsongButton.setVisibility(View.VISIBLE);
			SyncFormListViewAdapter.this.pausesongButton.setVisibility(View.VISIBLE);
			SyncFormListViewAdapter.this.stopsongButton.setVisibility(View.VISIBLE);
	    	
			File input = new File(PATH, filenameLocal);
			InputStream is = null;
			try {
				is = new FileInputStream(input);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				mediaPlayer.setDataSource(input.toString());
				mediaPlayer.prepare();
			} catch (IllegalArgumentException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IllegalStateException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (IOException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
	
			MP3PlayerTask mp3PlayerTask = new MP3PlayerTask(mediaPlayer);
			mp3PlayerTask.execute();
			
		}

		public void onDownloadProgress(Integer progress) {
			// TODO Auto-generated method stub
			
		}
		
	}
}