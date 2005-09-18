package jelc.xml;

import org.apache.xerces.dom.CoreDocumentImpl;

/**
 * A simple class holding XPath based queries for use when the Rules.xml file is needed to be read
 * @author Placid
 *
 */
public class RulesParser implements XMLProperty {
        private String[] queries;

        public String[] getXPathQueries(){
                return null;
        }

        public void setXPathQueries( String[] query ){

        }

		public void parse(CoreDocumentImpl doc) {
			// TODO Auto-generated method stub
			
		}

		public void parse(String doc) {
			// TODO Auto-generated method stub
			
		}
}
