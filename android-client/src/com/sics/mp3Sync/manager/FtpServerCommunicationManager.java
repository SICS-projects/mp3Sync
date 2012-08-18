/**
 * 
 */
package com.sics.mp3Sync.manager;

import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTPClient;

import com.sics.mp3Sync.threads.DownloadDataTask;
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
