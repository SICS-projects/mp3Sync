/**
 * 
 */
package JGApps.MP3Synch.Comunication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import JGApps.MP3Synch.R;
import JGApps.MP3Synch.Container.SongData;
import JGApps.MP3Synch.Exceptions.HttpPortNotValidException;
import JGApps.MP3Synch.Exceptions.NoAppContextException;
import JGApps.MP3Synch.Exceptions.WrongHttpReturnStateException;
import JGApps.MP3Synch.Exceptions.WrongHttpServerURLException;
import JGApps.MP3Synch.Global.Global;
import JGApps.MP3Synch.Helper.HttpServerConnector;
import JGApps.MP3Synch.Helper.XmlParser;
import JGApps.MP3Synch.Manager.PreferencesManager;
import JGApps.MP3Synch.Misc.Enums.DataFields;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * @author Joachim
 *
 */
public class HttpMetadataController implements DataAccessInterface{
	private String httpServerURL = "";
	
	public HttpMetadataController(String httpServerUrl){
		this.httpServerURL = httpServerUrl;
	}
	
	public List<Hashtable<String, Object>> selectAsHash(String table,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) throws WrongHttpReturnStateException {
		
		List<Hashtable<String, Object>> dataValues = new ArrayList<Hashtable<String, Object>>();
		
		
		try {
			String dataList_as_string = getDataList(httpServerURL, 0);
			XmlParser xmlParser = new XmlParser();
			dataValues = xmlParser.parseFromString(dataList_as_string);
		} catch (HttpPortNotValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.showDialog();
		} catch (WrongHttpServerURLException e) {
			e.showDialog();
			e.printStackTrace();
		}
		
		return dataValues;
	}

	public long update(String table, ContentValues values, String whereClause,
			String[] whereArgs, boolean useInsert) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long insert(String table, String nullColumnHack, ContentValues values) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void delete(String table) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Calls the getDataList() method of HttpServerComunication(httpServerURL, httpServerPort)
	 * The call returns a xml structure as String
	 *   
	 * @param httpServerURL
	 * Server Url
	 * @param httpServerPort
	 * port number
	 * @return
	 * xml as String
	 * @throws HttpPortNotValidException 
	 * @throws WrongHttpReturnStateException 
	 * @throws WrongHttpServerURLException 
	 * 
	 */
	private String getDataList(String httpServerURL, Integer httpServerPort) throws HttpPortNotValidException, WrongHttpReturnStateException, WrongHttpServerURLException{
		HttpServerConnector httpServerCom = new HttpServerConnector(httpServerURL, httpServerPort);
		return httpServerCom.getDataList();
	}
	
	
}
