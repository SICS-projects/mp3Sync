/**
 * 
 */
package com.sics.mp3Sync.manager;

import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTPClient;

import android.app.Activity;

import com.sics.mp3Sync.threads.DownloadDataTask;
/**
 * @author joachim
 *
 */
public class FtpServerCommunicationManager {

	private static FTPClient ftpClient = null;
	
	private static InputStream inputStream = null;
	private static FileOutputStream fos = null;
	private Activity activity;
	
	public FtpServerCommunicationManager(Activity activity){
		this.activity = activity;
	}
/**

 * @param remoteFile
 * File on FtpServer
 * 
 * @param localFile
 * local File
 */
	public DownloadDataTask downloadFileTo(String remoteFile, String localFile) {
		DownloadDataTask downloadDataTask = new DownloadDataTask(activity);
		downloadDataTask.execute(remoteFile, localFile);
		return downloadDataTask;
	}
}
