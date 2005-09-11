package jelc.xml;

import java.io.*;

import javax.xml.parsers.*;

import org.apache.xerces.dom.*;
import org.apache.xerces.jaxp.*;
import org.xml.sax.SAXException;

/**
 * Performs parsing of XML using apache Xerces, XPath and w3c DOM implementations.
 * @author Placid
 *
 */
public class XMLParser {

        /**
         * Constructs and parses the given XML file at location
         * @param location - The path to the xml file to parse
         */
        public XMLParser( String location ){
                this.doc = new CoreDocumentImpl();
                this.filePath = location;
                init( location );
        }

        /**
         * Basically constructs and places the document in memory. The
         * @param location - The path to the XML file to parse
         */
        private void init( String location ){
                try{
                        DocumentBuilderFactory docFactory = DocumentBuilderFactoryImpl.newInstance();
                        DocumentBuilderImpl docBuilder = (DocumentBuilderImpl)docFactory.newDocumentBuilder();
                        doc = (CoreDocumentImpl)docBuilder.parse( new File( location ) );

                } catch( ParserConfigurationException pce ) {
                        pce.printStackTrace();
                } catch( SAXException e ) {
                        e.printStackTrace();
                } catch( IOException ioe ) {
                        ioe.printStackTrace();
                }
        }


        /**
         * Parses this instance Document with the given Property.
         * (Each XMLProperty must have a string[] of xqueries
         */
        public void parse( XMLProperty prop ){

        }

        private CoreDocumentImpl doc;
        private String filePath;

        public CoreDocumentImpl getDoc() {
                return doc;
        }

        public String getFilePath() {
                return filePath;
        }

        public void setFilePath(String filePath) {
                this.filePath = filePath;
        }
}