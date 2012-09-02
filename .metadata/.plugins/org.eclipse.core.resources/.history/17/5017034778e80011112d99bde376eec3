/**
 * 
 */
package JGApps.MP3Synch.Helper;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import JGApps.MP3Synch.Container.SongData;
import JGApps.MP3Synch.Misc.Enums.DataFields;

/**
 * @author Joachim
 *
 */
public class XmlParser {
	public XmlParser(){
		
	}
	
	public List<Hashtable<String, Object>> parseFromString(String xmlData){
		String relevantXmlString = cutWaste("<!-- START -->", xmlData);
		
		return doParsing(relevantXmlString);
	}
	
	private List<Hashtable<String, Object>> doParsing(String xmlData){
		
		//TODO: Besser aufteilen
		  InputStream xmlStream = new ByteArrayInputStream(xmlData.getBytes());

	      DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      Document doc = null;
		try {
			doc = builder.parse(xmlStream);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      Element rootNode = doc.getDocumentElement();

	      NodeList list = rootNode.getElementsByTagName("Data");
	      
	      List<Hashtable<String, Object>> serverDataList = new ArrayList<Hashtable<String, Object>>();
	      
	      for(int i=0; i<list.getLength();i++){
	    	  Hashtable<String, Object> dataLine = new Hashtable<String, Object>();
	    	  
	    	  NamedNodeMap map = list.item(i).getAttributes();
	    	  String Id = map.getNamedItem(DataFields.ID).getNodeValue();
	    	  String name = map.getNamedItem(DataFields.NAME).getNodeValue();
	    	  String filePath = map.getNamedItem(DataFields.FILEPATH).getNodeValue();

	    	  dataLine.put(DataFields.ID, Id);
	    	  dataLine.put(DataFields.NAME, name);
	    	  dataLine.put(DataFields.FILEPATH, filePath);
	    	  
	    	  serverDataList.add(dataLine);
	      }
	      
	      return serverDataList;
	}
	
	private String cutWaste(String waste, String origin){
		//TODO: Filtern auslagern in klasse
		int indexOfFoo = origin.indexOf(waste);
		if (indexOfFoo != -1){
			return origin.substring(0, indexOfFoo);
		}
		
		return origin;
	}
}
