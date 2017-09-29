package priv.weilinwu.codecollection.note.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//java itself comes with the org.w3c.dom.*, no need to add extra dependency
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import priv.weilinwu.codecollection.note.keyandcertificate.PrivateKeyAndCertificateUtils;;

public class XmlConstruction {
	
	public static final Logger logger = LoggerFactory.getLogger(XmlConstruction.class);
	
//	generate XML like this:
//	<?xml version="1.0"?>
//	<Fibonacci_Numbers>
//	  <Fibonacci index="0">1</fibonacci>
//	  <Fibonacci index="1">1</fibonacci>
//	  <fibonacci index="2">2</fibonacci>
//	</Fibonacci_Numbers>
	
	public static void main(String[] args) throws ParserConfigurationException, TransformerFactoryConfigurationError, 
			TransformerException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//		documentBuilderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
		XmlConstruction.logger.info("haha");
		
		// create element node and text node by using document
		Element rootElement = document.createElement("Fibonacci_Numbers");
		Element childElement1 = document.createElement("Fibonacci");
		Element childElement2 = document.createElement("Fibonacci");
		Element childElement3 = document.createElement("Fibonacci");
		
		Text text1 = document.createTextNode("1");
		Text text2 = document.createTextNode("1");
		Text text3 = document.createTextNode("2");
		
		// set attribute for each element
		childElement1.setAttribute("index", "0");
		childElement2.setAttribute("index", "1");
		childElement3.setAttribute("index", "2");
		
		// set the nodes' relation
		childElement1.appendChild(text1);
		childElement2.appendChild(text2);
		childElement3.appendChild(text3);
		rootElement.appendChild(childElement1);
		rootElement.appendChild(childElement2);
		rootElement.appendChild(childElement3);
		document.appendChild(rootElement);
		
		// output the XML to a file/as string
		// There are two ways
		// 1. using Extensible Stylesheet Language Transformations (XSLT) API
		Transformer transFormer = TransformerFactory.newInstance().newTransformer();
		transFormer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "systemIdentifier");
		transFormer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "publicIdentifier");
        transFormer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); 
		transFormer.setOutputProperty(OutputKeys.INDENT, "yes");
		transFormer.setOutputProperty(OutputKeys.METHOD, "xml");
		transFormer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource domSource = new DOMSource(document);
		// output as string
		StringWriter stringWriter = new StringWriter();
		StreamResult streamResultString = new StreamResult(stringWriter);
		transFormer.transform(domSource, streamResultString);
		System.out.println(stringWriter.toString());
		// output as a file
		String filePath1 = (new File("")).getAbsolutePath() + "/temp/fibonacci1.xml";
		FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath1));
		StreamResult streamResultFile = new StreamResult(fileOutputStream);
		transFormer.transform(domSource, streamResultFile);
		// output as follows:
//		<?xml version="1.0" encoding="UTF-8" standalone="no"?>
//		<!DOCTYPE Fibonacci_Numbers PUBLIC "publicIdentifier" "systemIdentifier">
//		<Fibonacci_Numbers>
//		  <Fibonacci index="0">1</Fibonacci>
//		  <Fibonacci index="1">1</Fibonacci>
//		  <Fibonacci index="2">2</Fibonacci>
//		</Fibonacci_Numbers>
		
		// 2. using LSSerializer interface
		DOMImplementation domImplementation = document.getImplementation();
		DOMImplementationLS domImplementationLS = (DOMImplementationLS) domImplementation.getFeature("LS", "3.0");
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		// set a flag to add spaces to make it look nicer
		lsSerializer.getDomConfig().setParameter("format-pretty-print", true);
		// output as string 
		String str = lsSerializer.writeToString(document);
		System.out.println(str);
		// output as a file
		String filePath2 = (new File("")).getAbsolutePath() + "/temp/fibonacci2.xml";
		Path path = Paths.get(filePath2);
		LSOutput out = domImplementationLS.createLSOutput();
		out.setEncoding("UTF-8");
		out.setByteStream(Files.newOutputStream(path));
		lsSerializer.write(document, out);
		// output as follows:
//		<?xml version="1.0" encoding="UTF-8"?>
//		<Fibonacci_Numbers>
//		    <Fibonacci index="0">1</Fibonacci>
//		    <Fibonacci index="1">1</Fibonacci>
//		    <Fibonacci index="2">2</Fibonacci>
//		</Fibonacci_Numbers>
	}
}
