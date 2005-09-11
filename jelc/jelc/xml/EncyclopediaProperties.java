package jelc.xml;

public class EncyclopediaProperties implements XMLProperty {
        private String[] queries;

        public String[] getXPathQueries(){
                return queries;
        }

        public void setXPathQueries( String[] queries ){
                this.queries = queries;
        }
}