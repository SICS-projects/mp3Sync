/**
 * 
 */
package com.sics.mp3Sync.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

import com.sics.mp3Sync.container.SongData;
import com.sics.mp3Sync.misc.enums.DataFields;


/**
 * @author Joachim
 *
 */
public class XmlParser_old {
	public XmlParser_old(){
		
	}
	
	public List<SongData> parseFromString(String xmlData){
		String relevantXmlString = cutWaste("<!-- START -->", xmlData);
		
		return doParsing(relevantXmlString);
	}
	
	private List<SongData> doParsing(String xmlData){
		
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
	      
	      List<SongData> serverDataList = new ArrayList<SongData>();
	      
	      for(int i=0; i<list.getLength();i++){
	    	  NamedNodeMap map = list.item(i).getAttributes();
//	    	  ServerData items = new ServerData();
	    	  Node node = null;
	    	  
	    	  node = map.getNamedItem(DataFields.ID);
	    	  String Id = node.getNodeValue();
	    	  
	    	  node = map.getNamedItem(DataFields.NAME);
	    	  String name = node.getNodeValue();

	    	  node = map.getNamedItem(DataFields.FILEPATH);
	    	  String filepath = node.getNodeValue();
	    	  
//	    	  SongData serverdata = ListConverter.MappToServerData(Integer.parseInt(Id), name, filepath);
	    	  
//	    	  serverDataList.add(serverdata);
	      }
	      
	      return serverDataList;
	}
	
	private String cutWaste(String waste, String origin){
		//TODO: Filtern auslagern in klasse
		int indexOfFoo = origin.indexOf(waste);
		return origin.substring(0, indexOfFoo);
	}
}
