package JGApps.MP3Synch.Helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.CRC32;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import JGApps.MP3Synch.Exceptions.HttpPortNotValidException;
import JGApps.MP3Synch.Exceptions.WrongHttpReturnStateException;
import JGApps.MP3Synch.Exceptions.WrongHttpServerURLException;
import JGApps.MP3Synch.Helper.StringCheck;
import JGApps.MP3Synch.Misc.Enums.DataFields;

public class HttpServerConnector {
	
	private String mHttpServerURL = "";
	private Integer mPort = -1;
	
	public HttpServerConnector(String httpServerURL, Integer port){
		this.mHttpServerURL = httpServerURL;
	}
	
	/**
	 * Gets the datalist from the server. The datalist is the list of files on the server.
	 * It means not the data from file file1.mp3 itself.
	 * 
	 * for example: file1.mp3, file2.mp3, file3.mp3.
	 * @return
	 * @throws HttpPortNotValidException 
	 * @throws WrongHttpReturnStateException 
	 * @throws WrongHttpServerURLException 
	 */
	public String getDataList() throws HttpPortNotValidException, WrongHttpReturnStateException, WrongHttpServerURLException{
		
		String xmlData = "";
		try{
			HttpResponse rp = getHttpResponse(mHttpServerURL);// + ":" + mPort
			
			while (rp.getStatusLine() == null){
				
			}
			if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
					xmlData = EntityUtils.toString(rp.getEntity());
			}
			else {
				throw new WrongHttpReturnStateException();
			}
		}
    	catch(IllegalArgumentException e){
    		e.printStackTrace();
    		throw new HttpPortNotValidException();
    	} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return xmlData;
	}
	
	public void getFile(String pathOfFilename, String pathOfFilenameOnStorage){
		//TODO: Auslagern/Unterscheiden von datei holen und metadaten holen.  
		/*java.io.BufferedInputStream in = new java.io.BufferedInputStream(new
				 
				java.net.URL("http://bdonline.sqe.com/documents/testplans.pdf").openStream());
				java.io.FileOutputStream fos = new java.io.FileOutputStream("testplans.pdf");
				java.io.BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
				byte[] data = new byte[1024];
				int x=0;
				while((x=in.read(data,0,1024))>=0)
				{
				bout.write(data,0,x);
				}
				bout.close();
				in.close();*/
		
		Boolean isHttpS = false;
		String[] es = pathOfFilename.split("[/]");
		
		if (es[0].toLowerCase().startsWith("https")){
			isHttpS = true;
		}
		else{
			isHttpS = false;
		}
		
		String PATH = Environment.getExternalStorageDirectory() + "/" + pathOfFilenameOnStorage;
		
		URLConnection urlCon = null;
		FileOutputStream f = null;
		 
		URL u = null;
		try {
			u = new URL(pathOfFilename);
			if (isHttpS == true){
				urlCon =(HttpsURLConnection) u.openConnection();
			}
			else{
				urlCon =(HttpURLConnection) u.openConnection();
				
			}
			urlCon.connect();
		}
		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
	    
	    
		String[] pathOfFilename_parts = pathOfFilename.split("[/]");
		String filename = pathOfFilename_parts[pathOfFilename_parts.length-1];
		
		File file = null;
		InputStream in = null;
		try {
			file = new File(PATH, filename);
			
			if (!file.exists()){
				f = new FileOutputStream(new File(PATH, filename));
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (f != null){
			try {
				in = urlCon.getInputStream();
				
				byte[] buffer = new byte[1024];
			    int len1 = 0;
			    
			    Integer i = in.available();
			    
			    CRC32 crc32 = new CRC32(); 
				while ( (len1 = in.read(buffer)) != -1 ) {
				  f.write(buffer,0, len1);
				  crc32.update(buffer); 
				}
				
				f.close();
				
				long crc32Client = crc32.getValue(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	private HttpResponse getHttpResponse(String server) throws ClientProtocolException, IOException, WrongHttpServerURLException{
		
		HttpClient hc = new DefaultHttpClient();
		
		server = StringCheck.correctStringForHttp(server);
		
		HttpPost post = new HttpPost(server);
		
		HttpResponse rp = null;
		try {
			 rp = hc.execute(post);
		} catch (IllegalStateException e) {
			throw new WrongHttpServerURLException();
		}

		return rp;
	}
}
