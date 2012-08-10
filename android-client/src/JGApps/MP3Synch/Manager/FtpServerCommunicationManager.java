/**
 * 
 */
package JGApps.MP3Synch.Manager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import javazoom.jl.player.advanced.AdvancedPlayer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import JGApps.MP3Synch.Exceptions.NoAppContextException;
import JGApps.MP3Synch.Global.Global;
import JGApps.MP3Synch.Threads.DownloadDataTask;
import android.media.AsyncPlayer;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.View;
/**
 * @author joachim
 *
 */
public class FtpServerCommunicationManager {

	private static FTPClient ftpClient = null;
	
	private static InputStream inputStream = null;
	private static FileOutputStream fos = null;
	
/**

 * @param remoteFile
 * File on FtpServer
 * 
 * @param localFile
 * local File
 */
	public DownloadDataTask downloadFileTo(String remoteFile, String localFile) {
		DownloadDataTask downloadDataTask = new DownloadDataTask();
		downloadDataTask.execute(remoteFile, localFile);
		return downloadDataTask;
	}
}
