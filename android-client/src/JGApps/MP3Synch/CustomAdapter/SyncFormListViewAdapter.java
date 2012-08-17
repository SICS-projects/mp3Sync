package JGApps.MP3Synch.CustomAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.SynchronousQueue;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import android.media.AsyncPlayer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.text.Layout;
import android.view.View;
import JGApps.MP3Synch.R;
import JGApps.MP3Synch.Container.PlayerControllsContainer;
import JGApps.MP3Synch.Container.SongListOptions;
import JGApps.MP3Synch.EventListener.DownloadListener;
import JGApps.MP3Synch.Events.FinishedDownloadEvent;
import JGApps.MP3Synch.Exceptions.NoAppContextException;
import JGApps.MP3Synch.Global.Global;
import JGApps.MP3Synch.Manager.FtpServerCommunicationManager;
import JGApps.MP3Synch.Misc.Enums.ItemOptions;
import JGApps.MP3Synch.Threads.DownloadDataTask;
import JGApps.MP3Synch.Threads.MP3PlayerTask;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SyncFormListViewAdapter extends ArrayAdapter {
	private int countElements = -1;
	private static int clickedItem = -1;
	private Context context;
	private SongListOptions songlistOptions = null;
	
	
	private DownloadDataTask downloadTask = null;
	
	public SyncFormListViewAdapter(Context context, int textViewResourceId, SongListOptions songListOptions) {
		super(context, textViewResourceId);
		this.context = context;
		this.songlistOptions = songListOptions;
	}
	
	private void setGlobalPlayerControllsValue(Button playsongButton, Button pausesongButton, Button stopsongButton, Button replaySongButton,
												SeekBar seekbar, TextView connectionInfo){
		PlayerControllsContainer playerControlls = new PlayerControllsContainer(playsongButton, pausesongButton, stopsongButton, seekbar);
		
		Global.setPlayerControls(playerControlls);
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

		Button songInfoButton = (Button) rowLayout.findViewById(R.id.songInfo);
		
		//final Button syncButton = (Button) rowLayout.findViewById(R.id.doSync);
		final Button playsongButton = (Button) rowLayout.findViewById(R.id.startPlayer);
		final Button pausesongButton = (Button) rowLayout.findViewById(R.id.pausePlayer);
		final Button stopsongButton = (Button) rowLayout.findViewById(R.id.stopPlayer);
		final Button replaySongButton = (Button) rowLayout.findViewById(R.id.playPlayer);
		
		final LinearLayout playerButtons = (LinearLayout) rowLayout.findViewById(R.id.playerButtons);
		
		//syncButton.setBackgroundResource(R.drawable.hackerl_green);
		playsongButton.setBackgroundResource(R.drawable.playsong);
		pausesongButton.setBackgroundResource(R.drawable.pausesong);
		stopsongButton.setBackgroundResource(R.drawable.stopsong);
		songInfoButton.setBackgroundResource(R.drawable.info);
		replaySongButton.setBackgroundResource(R.drawable.playsong);
		

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
						int timePosition = (Global.getMediaPlayer().getDuration()/100) * progress;
						
	//					player.stop();
						
						Global.getMediaPlayer().seekTo(timePosition);
						
	//					player.start();
					}
				}
				// TODO Auto-generated method stub
				
			}
		});
		
		if (songlistOptions.getOptionForJustOneLine(ItemOptions.UNIC_PLAYSONG_STRING) == position){
			mSeekBar.setVisibility(View.VISIBLE);
			
			playerButtons.setVisibility(View.VISIBLE);
			
			playsongButton.setVisibility(View.GONE);
		}
		else{
			mSeekBar.setVisibility(View.GONE);
			
			playerButtons.setVisibility(View.GONE);
			
			playsongButton.setVisibility(View.VISIBLE);
		}

		playsongButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//Preparing the GUI To be controlled by a thread
				setGlobalPlayerControllsValue(playsongButton, pausesongButton, stopsongButton, replaySongButton,
						mSeekBar, connectionInfo);
				
				playSong(pausesongButton, stopsongButton, playsongButton, replaySongButton, position,
						mSeekBar, connectionInfo, playerButtons);
				
//				HttpServerComunication httpServer = new HttpServerComunication(pathOfFilename, 0);
//				httpServer.getFile(pathOfFilename, "test");
			}
		});
		
		replaySongButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				//Preparing the GUI To be controlled by a thread
				setGlobalPlayerControllsValue(playsongButton, pausesongButton, stopsongButton, replaySongButton,
						mSeekBar, connectionInfo);
				
				playSong(pausesongButton, stopsongButton, playsongButton, replaySongButton, position,
						mSeekBar, connectionInfo, playerButtons);
			}
			
		});
		
		stopsongButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.getMediaPlayer().stop();
				
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
				Global.getMediaPlayer().pause();
				
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
	
	private void playSong(final Button pausesongButton, final Button  stopsongButton, final Button playsongButton, Button replaySongButton, final int position,
							SeekBar mSeekbar, TextView connectionInfo, LinearLayout playerButtons){
		
		FtpServerCommunicationManager ftpServerCommunicationManager = new FtpServerCommunicationManager();
		ftpServerCommunicationManager.downloadFileTo(this.songlistOptions.getFilepathOfItemOnServer(position), 
													 this.songlistOptions.getNameOfItem(position) + "mp3").addDownloadFinishedListener(new DownloadListener());
	}
}