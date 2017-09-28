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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
		
		File xmlFile = new File((new File("")).getAbsolutePath() + "/conf/data/xml/fibonacci1.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		// print the content of it
		Transformer transFormer = TransformerFactory.newInstance().newTransformer();
		StringWriter stringWriter = new StringWriter();
		transFormer.transform(new DOMSource(document), new StreamResult(stringWriter));
		System.out.println(stringWriter.toString());
		
		// url or input stream also could be parsed:
//		URL url = ...
//		Document document = documentBuilder.parse(url);
//		InputStream inputStream = ...
//		Document document = documentBuilder.parse(inputStream);
		
		// get root element
		Element rootElement = document.getDocumentElement();
		System.out.println("the root element is: " + rootElement.getTagName());
		
		// 
	}
}
