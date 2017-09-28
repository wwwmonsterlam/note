package priv.weilinwu.codecollection.note.xml;

import java.lang.reflect.GenericArrayType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// java itself comes with the org.w3c.dom.*, no need to add extra dependency
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;;

public class XmlConstruction {
	
//	generate XML like this:
//	<?xml version="1.0"?>
//	<Fibonacci_Numbers>
//	  <Fibonacci index=0>1</fibonacci>
//	  <Fibonacci index=1>1</fibonacci>
//	  <fibonacci index=2>2</fibonacci>
//	</Fibonacci_Numbers>
	
	public static void main(String[] args) throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//		documentBuilderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
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
		
	}
}
