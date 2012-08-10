/**
 * 
 */
package JGApps.MP3Synch.Threads;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * @author joachim
 *
 */
public class MP3PlayerTask extends AsyncTask<View, Integer, byte[]> {
	private MediaPlayer player = null;
	
	private SeekBar seekbar = null;
	private Button replayButton = null;
	private Button pauseButton = null;
	private Button stopButton = null;
	
	private int thingsToUpdate = -1;
	
	
	public MP3PlayerTask(MediaPlayer player){
		this.player = player;
	}
	
	protected void onProgressUpdate(Integer... progress) {
		
		if (this.thingsToUpdate == 1){
			//seekbar.setProgress(progress[0]);
		}
		
		if (this.thingsToUpdate == 2){
			/*replayButton.setVisibility(View.VISIBLE);
			
			stopButton.setVisibility(View.GONE);
			pauseButton.setVisibility(View.GONE);
			seekbar.setProgress(0);*/
		}
	}
	
	@Override
	protected byte[] doInBackground(View... params) {
		// TODO Auto-generated method stub
		/*this.seekbar = (SeekBar)params[0];
		this.replayButton = (Button)params[1];
		this.pauseButton = (Button)params[2];
		this.stopButton = (Button)params[3];*/
		
		
		this.player.start();
		
		
		int fileDuration = player.getDuration();
		while(player.isPlaying()){
			
			int progressDuration = player.getCurrentPosition();
		
			float percentProgress = new Integer(progressDuration).floatValue()/new Integer(fileDuration).floatValue();
			
			int progress = (int) (percentProgress*100);
			
			this.thingsToUpdate = 1;
			this.publishProgress(progress);
		}
		
		this.thingsToUpdate = 2;
		this.publishProgress(null);
		
		
		return null;
	}
}


