/**
 * 
 */
package JGApps.MP3Synch.Threads;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventListenerProxy;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;

import JGApps.MP3Synch.R;
import JGApps.MP3Synch.EventListener.DownloadListener;
import JGApps.MP3Synch.Events.FinishedDownloadEvent;
import JGApps.MP3Synch.Exceptions.GlobalValueIsNullException;
import JGApps.MP3Synch.Exceptions.NoAppContextException;
import JGApps.MP3Synch.Exceptions.WrongHttpServerURLException;
import JGApps.MP3Synch.Exceptions.WrongLoginnameOrPasswordException;
import JGApps.MP3Synch.Global.Global;
import JGApps.MP3Synch.Helper.HttpServerConnector;
import JGApps.MP3Synch.Manager.FtpServerCommunicationManager;
import JGApps.MP3Synch.Manager.PreferencesManager;
import JGApps.MP3Synch.Mapping.GUI.OptionsContainer.SeekbarOptions;
import android.media.AsyncPlayer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author Joachim
 *
 * Handles the download itself. This class represents the Thread that downloads the file and publics the metainfomation for updating the GUI 
 */
public class DownloadDataTask extends AsyncTask<String, Integer, byte[]> {
private FTPClient ftpClient = null;
	
	private InputStream inputStream = null;
	private FileOutputStream fos = null;
	
	private ArrayList<View> viewsToUpdate = null;
	
	private enum ViewsToUpdate{NONE, CONNECTIONTEXT, SEEKBAR, PLAYERBUTTONS};
	
	private ViewsToUpdate viewToUpdate = ViewsToUpdate.NONE;
	
	public  String nameOfFile = "";
	private MediaPlayer player = null;
	
	private boolean stopExecuting = false;
	
	private boolean isDownloading = false;
	
	private FinishedDownloadEvent finishedDownloadEvent = new FinishedDownloadEvent(this);;
	
/*	public DownloadDataTask(ArrayList<View> viewsToUpdate, MediaPlayer player){
		this.viewsToUpdate = viewsToUpdate;
		this.player = player;
		
		prepareControlls();
	}*/
	
	public DownloadDataTask(){

	}
	
	public void addDownloadFinishedListener(DownloadListener listener){
		this.finishedDownloadEvent.addDownloadFinishedListener(listener);
	}

    protected byte[] doInBackground(String... metadata) {
    	
		String remoteFile = metadata[0];
		String localFile = metadata[1];
		
				
		try {
			downloadFileTo(remoteFile, localFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoAppContextException e) {
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
    	
    	this.finishedDownloadEvent.actionPerformed(finishedDownloadEvent);    	
    	
    }
    
    @Override
    protected void onProgressUpdate(Integer... progress) {
    	
    	if (viewToUpdate == ViewsToUpdate.SEEKBAR){
	    	if (progress != null && progress.length > 0){
	    		SeekBar seekbar = null;
				try {
					seekbar = Global.getPlayerControlls().getSeekbar();
				} catch (GlobalValueIsNullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		seekbar.setVisibility(View.VISIBLE);
	    		seekbar.setSecondaryProgress(progress[0]);
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
    	
    	
    }
    
    protected void downloadFileTo(String remoteFile, String localFile) throws NumberFormatException, SocketException, IOException, NoAppContextException, WrongHttpServerURLException, WrongLoginnameOrPasswordException{
		ftpClient = new FTPClient();
		
		this.isDownloading = true;
		
		this.viewToUpdate = ViewsToUpdate.CONNECTIONTEXT;
		this.publishProgress();
		connect();
		login();
		this.viewToUpdate = ViewsToUpdate.SEEKBAR;
		this.publishProgress();
		
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		download(remoteFile, localFile);
		
		close();
		//TODO reinschreiben im grlobalen speicherberech was wie upgedaten werden soll
	}
	
	protected void connect() throws NumberFormatException, SocketException, IOException, NoAppContextException, WrongHttpServerURLException{
		String hostname = PreferencesManager.getPreferenceAsString("ftp_server_preference_host_key", "ftp://", Global.getAppContext());
		String port = PreferencesManager.getPreferenceAsString("ftp_server_preference_port_key", "21", Global.getAppContext());
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
	
	protected void login() throws NoAppContextException, IOException, WrongLoginnameOrPasswordException{
		String username = PreferencesManager.getPreferenceAsString("ftp_server_preference_username_key", "", Global.getAppContext());
		String password = PreferencesManager.getPreferenceAsString("ftp_server_preference_password_key", "", Global.getAppContext());
		
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
