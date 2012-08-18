/**
 * 
 */
package com.sics.mp3Sync.threads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import com.sics.mp3Sync.eventListener.DownloadListenerInterface;
import com.sics_android_sdk.Exceptions.WrongHttpServerURLException;
import com.sics_android_sdk.Exceptions.WrongLoginnameOrPasswordException;
import com.sics_android_sdk.Manager.PreferencesManager;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.SeekBar;

/**
 * @author Joachim
 *
 * Handles the download itself. This class represents the Thread that downloads the file and publics the metainfomation for updating the GUI 
 */
public class DownloadDataTask extends AsyncTask<String, Integer, byte[]> {
	private FTPClient ftpClient = null;
	private Activity activity;
	
	private InputStream inputStream = null;
	private FileOutputStream fos = null;
	private String localFile = "";
	
	private ArrayList<View> viewsToUpdate = null;
	
	private enum ViewsToUpdate{NONE, CONNECTIONTEXT, SEEKBAR, PLAYERBUTTONS};
	
	private ViewsToUpdate viewToUpdate = ViewsToUpdate.NONE;
	
	public  String nameOfFile = "";
	private MediaPlayer player = null;
	
	private boolean stopExecuting = false;
	
	private boolean isDownloading = false;
	
	private DownloadListenerInterface downloadListener;
	
/*	public DownloadDataTask(ArrayList<View> viewsToUpdate, MediaPlayer player){
		this.viewsToUpdate = viewsToUpdate;
		this.player = player;
		
		prepareControlls();
	}*/
	
	public DownloadDataTask(Activity activity){
		this.activity = activity;
	}
	
	public void setDownloadFinishedListener(DownloadListenerInterface listener){
		this.downloadListener = listener;
	}

    protected byte[] doInBackground(String... metadata) {
    	
		String remoteFile = metadata[0];
		localFile = metadata[1];
		
				
		try {
			downloadFileTo(remoteFile, localFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongHttpServerURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongLoginnameOrPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new byte[4];
    }
    
    public boolean isDownloading(){
    	return this.isDownloading;
    }
    
    public void StopExecuting(){
    	this.stopExecuting = true;
    }

    protected void onPostExecute(byte[] result) {
    	this.downloadListener.onDownloadFinished(localFile);
    }
    
    @Override
    protected void onProgressUpdate(Integer... progress) {
    	
    	if (viewToUpdate == ViewsToUpdate.SEEKBAR){
	    	if (progress != null && progress.length > 0){
	    		SeekBar seekbar = null;
				
//	    		seekbar = Global.getPlayerControlls().getSeekbar();
//	    		seekbar.setVisibility(View.VISIBLE);
//	    		seekbar.setSecondaryProgress(progress[0]);
	    	}
	    	
	    }
    	
    	if (viewToUpdate == ViewsToUpdate.CONNECTIONTEXT){
	    	/*TextView seekbar = (TextView)this.viewsToUpdate.get(1);
		    	
	    	seekbar.setVisibility(View.VISIBLE);
	    	seekbar.setText("Verbinde mit Server ...");*/
	    	
	    }
    	
    	if (viewToUpdate == ViewsToUpdate.SEEKBAR){
	    	/*TextView connectText = (TextView)this.viewsToUpdate.get(1);
		    
	    	SeekBar seekbar = (SeekBar)this.viewsToUpdate.get(0);
    		seekbar.setVisibility(View.VISIBLE);
    		
    		connectText.setVisibility(View.GONE);*/
	    }
    	
    	if (this.isDownloading){
    		if (this.downloadListener != null){
    			this.downloadListener.onDownloadProgress(progress[0]);
    		}
    	}
    	
    	
    }
    
    protected void downloadFileTo(String remoteFile, String localFile) throws NumberFormatException, SocketException, IOException, WrongHttpServerURLException, WrongLoginnameOrPasswordException{
		ftpClient = new FTPClient();
		
		this.isDownloading = true;
		
		this.viewToUpdate = ViewsToUpdate.CONNECTIONTEXT;
		this.publishProgress(0);
		connect();
		login();
		this.viewToUpdate = ViewsToUpdate.SEEKBAR;
		this.publishProgress(0);
		
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		download(remoteFile, localFile);
		
		close();
		
		this.isDownloading = false;
		//TODO reinschreiben im grlobalen speicherberech was wie upgedaten werden soll
	}
	
	protected void connect() throws NumberFormatException, SocketException, IOException, WrongHttpServerURLException{
		String hostname = PreferencesManager.getPreferenceAsString("ftp_server_preference_host_key", "ftp://", activity.getApplicationContext());
		String port = PreferencesManager.getPreferenceAsString("ftp_server_preference_port_key", "21", activity.getApplicationContext());
		try{

			if (hostname.startsWith("ftp://")){
				hostname = hostname.substring(6);
			}
			
			ftpClient.connect(hostname, Integer.parseInt(port));
			ftpClient.enterLocalPassiveMode();
		}
		catch(IOException e){
			throw new WrongHttpServerURLException();
		}
	}
	
	protected void login() throws IOException, WrongLoginnameOrPasswordException{
		String username = PreferencesManager.getPreferenceAsString("ftp_server_preference_username_key", "", activity.getApplicationContext());
		String password = PreferencesManager.getPreferenceAsString("ftp_server_preference_password_key", "", activity.getApplicationContext());
		
		try{
			ftpClient.login(username, password);
		}catch(IOException e){
			throw new WrongLoginnameOrPasswordException();
		}
	}
	
	protected void download(String remoteFile, String localFile) throws IOException{
		byte[] byteBuffer = new byte[1024];
		
		nameOfFile = localFile;
		
		this.viewToUpdate = ViewsToUpdate.SEEKBAR;
		this.publishProgress(0);
		
		//ftpClient.changeWorkingDirectory();
		FTPFile[] files = ftpClient.listFiles();
		
		float fileSize = -1;
		for(FTPFile file : files){
			if (file.getName().equals(remoteFile)){
				fileSize = file.getSize();
			}
		}
		
		inputStream = ftpClient.retrieveFileStream(remoteFile.toString());
		
		String PATH = Environment.getExternalStorageDirectory()
                + "/Download/mp3/";

		File file = new File(PATH);
        file.mkdirs();
        
        String[] pathOfFilename_parts = PATH.split("[/]");
		String filename = pathOfFilename_parts[pathOfFilename_parts.length-1];
        
        File outputFile = new File(file, localFile.toString());

        fos = new FileOutputStream(outputFile);

	    int counter=0;
	    float counterInsg = 0;
		while(((counter = inputStream.read(byteBuffer)) > 0)){
			fos.write(byteBuffer, 0, counter);
			
			counterInsg += counter;
			int percent = (int)((counterInsg/fileSize)*100);
			
			this.viewToUpdate = ViewsToUpdate.SEEKBAR;
			this.publishProgress(percent);
		}
	}
	
	protected void close() throws IOException{
		fos.close();
		inputStream.close();
		
		ftpClient.logout();
		ftpClient.disconnect();
	}
	
}
