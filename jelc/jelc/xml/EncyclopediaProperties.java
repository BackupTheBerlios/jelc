package jelc.xml;

import org.apache.xerces.dom.CoreDocumentImpl;

public class EncyclopediaProperties implements XMLProperty {
        private String[] queries;

        public String[] getXPathQueries(){
                return queries;
        }

        public void setXPathQueries( String[] queries ){
                this.queries = queries;
        }

		public void parse(CoreDocumentImpl doc) {
			/*
			 * Read the rules, and add each rule to the rules list
			 * Using XPath, we can find the rules in the document 
			 * 		quite quickly and easily
			 */
			
		}

		public void parse(String doc) {
			// TODO Auto-generated method stub
			
		}
}