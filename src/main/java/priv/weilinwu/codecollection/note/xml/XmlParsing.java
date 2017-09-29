package priv.weilinwu.codecollection.note.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParsing {

	// the xml file to be parsed is located at /conf/data/xml/fibonacci.xml
	// the following is the content of it
//	<?xml version="1.0" encoding="UTF-8" standalone="no"?>
//	<!DOCTYPE Fibonacci_Numbers PUBLIC "publicIdentifier" "systemIdentifier">
//	<Fibonacci_Numbers>
//	  <Fibonacci index="0">1</Fibonacci>
//	  <Fibonacci index="1">1</Fibonacci>
//	  <Fibonacci index="2">2</Fibonacci>
//	</Fibonacci_Numbers>
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, 
			TransformerFactoryConfigurationError, TransformerException {
		
		String path = (new File("")).getAbsolutePath() + "/conf/data/xml/fibonacci.xml";
		File xmlFile = new File(path);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		// print the content of it
		System.out.println(new XmlParsing().xmlToString(document));
		
		// url or input stream also could be parsed:
//		URL url = ...
//		Document document = documentBuilder.parse(url);
//		InputStream inputStream = ...
//		Document document = documentBuilder.parse(inputStream);
		
		// get root element
		Element rootElement = document.getDocumentElement();
		System.out.println("the root element is: " + rootElement.getTagName());
		// console output
//		tagName: Fibonacci
//		index: 0
//		text: 1
		
		// use NodeList and item() to get an element
		NodeList nodeList = rootElement.getChildNodes();
		Element childElement1 = (Element) nodeList.item(1); //item index starts from 1, not 0. So the first item should be item 1 not item 0
		System.out.println("\n");
		System.out.println("tagName: " + childElement1.getTagName());
		System.out.println("index: " + childElement1.getAttribute("index"));
		System.out.println("text: " + childElement1.getTextContent());
		// console output
//		 remove first child's index:
//		 <?xml version="1.0" encoding="UTF-8" standalone="no"?><Fibonacci_Numbers>
//		   <Fibonacci>1</Fibonacci>
//		   <Fibonacci index="1">1</Fibonacci>
//		   <Fibonacci index="2">2</Fibonacci>
//		 </Fibonacci_Numbers>
		
		// remove
		childElement1.removeAttribute("index");
		System.out.println("\n remove first child's index:");
		System.out.println(new XmlParsing().xmlToString(document));
		
		rootElement.removeChild(childElement1);
		System.out.println("\n remove the first child:");
		System.out.println(new XmlParsing().xmlToString(document));
		// console output
//		 remove the first child:
//			 <?xml version="1.0" encoding="UTF-8" standalone="no"?><Fibonacci_Numbers>
//			   
//			   <Fibonacci index="1">1</Fibonacci>
//			   <Fibonacci index="2">2</Fibonacci>
//			 </Fibonacci_Numbers>
	}
	
	public String xmlToString(Document document) throws TransformerException {
		Transformer transFormer = TransformerFactory.newInstance().newTransformer();
		StringWriter stringWriter = new StringWriter();
		transFormer.transform(new DOMSource(document), new StreamResult(stringWriter));
		return stringWriter.toString();
	}
}
