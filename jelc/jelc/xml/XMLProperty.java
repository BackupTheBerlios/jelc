package jelc.xml;

/**
 * The Class implementing this interface is basically representing an XML document.
 * A class that does so, holds one simple property: A String[] of XPath queries relevant to a particular XML document
 * Therefore, a class that implements getXPathQueries() suggests that it knows particular properties of an XML Doc.
 *
 * @author Placid
 *
 */
public interface XMLProperty {

        /**
         * Returns a String[] of XPath queries relevant to the particular object.
         *
         * @returns String[] - containing String's of XPath queries for the particular object
         */
        public String[] getXPathQueries();
}
