package jelc.xml;

import org.apache.xerces.dom.CoreDocumentImpl;

/**
 * The Class implementing this interface is basically representing an XML document.
 * A class that does so, holds one simple property: A String[] of XPath queries relevant to a particular XML document
 * Therefore, a class that implements getXPathQueries() suggests that it knows particular properties of an XML Doc.
 *
 * @author Placid
 *
 */
public interface XMLProperty {

        public void parse( CoreDocumentImpl doc );
        
        public void parse( String doc );
}
