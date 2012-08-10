/**
 * 
 */
package JGApps.MP3Synch.Global;

import JGApps.MP3Synch.Container.PlayerControllsContainer;
import JGApps.MP3Synch.Exceptions.AppContextAlreadySetException;
import JGApps.MP3Synch.Exceptions.GlobalValueIsNullException;
import JGApps.MP3Synch.Exceptions.NoAppContextException;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

/**
 * @author Joachim Gugenberger
 *
 */
public class Global {
	private static Activity activity = null;
	private static PlayerControllsContainer playerControlls = null; 
	private static MediaPlayer medialayer = new MediaPlayer();
	
	public static Activity getAppContext() throws NoAppContextException{
		if (activity == null){
			throw new NoAppContextException();
		}else{
			synchronized(activity){
				return activity;
			}
		}
	}
	
	public static void setAppContext(Activity activity) {
		
		/*if (Global.activity != null){
			throw new AppContextAlreadySetException();
		}else{*/
				Global.activity = activity;
//		}
	}
	
	public static void setAppContextToNull(){
		activity = null;
	}
	
	public static MediaPlayer getMediaPlayer(){
		return medialayer;
	}
	
	public static PlayerControllsContainer getPlayerControlls() throws GlobalValueIsNullException{
		if (playerControlls == null){
			throw new GlobalValueIsNullException();
		}
		else{
			return playerControlls;
		}
	}
	
	public static void setPlayerControls(PlayerControllsContainer playerControlls){
		Global.playerControlls = playerControlls;
	}
}
