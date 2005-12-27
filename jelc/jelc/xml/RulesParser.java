package jelc.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xpath.internal.XPathAPI;
import com.sun.org.apache.xpath.internal.objects.XObject;

public class RulesParser implements XMLDocumentParser {
		public static final String XPATH_RULE = "./text/rule[%]/";
	
	
		public void parse( Document doc ) throws TransformerException {
			/*
			 * Read the rules, and add each rule to the rules list
			 * Using XPath, we can find the rules in the document 
			 * 		quite quickly and easily
			 */
			
			// String to represent each rule within the document
			// Replace the '%' with an integer to reference a different rule
			String ruleXPath = "./text/rule[%]/";
			XObject xObj = XPathAPI.eval( doc.getFirstChild(),ruleXPath.replaceAll( "%","1" ) );
			NodeList ruleList = xObj.nodelist();
			
			int c = 1;// The specific rule
			
			Node ruleNode = ruleList.item( 0 );
			
			// Get the first node from our list
			// It should be a rule
			while( ruleNode != null ) {
				Rule rule = new Rule(  );
				Node shortRule = XPathAPI.eval( ruleNode,"./short/" ).nodelist().item( 0 );
				if( shortRule != null )
					rule.setShortRule( shortRule.getTextContent() );
				
				Node longRule = XPathAPI.eval( ruleNode,"./long/" ).nodelist().item( 0 );
				if( longRule != null )
					rule.setLongRule( longRule.getTextContent() );
				
				c++;
				ruleXPath = XPATH_RULE.replaceAll( "%",""+c );
				ruleNode = ruleList.item( c );
			}
		}

		public void parse( String doc ) throws ParserConfigurationException,TransformerException {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.newDocument();
			parse( xmlDoc );
		}
}