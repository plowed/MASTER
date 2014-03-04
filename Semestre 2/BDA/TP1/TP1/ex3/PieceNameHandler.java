import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class PieceNameHandler extends DefaultHandler {

		private SortedSet<String> piece;
		private boolean etage;

		public void startDocument() throws SAXException {
			etage=false;
			piece=new TreeSet<String>(new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					// TODO Auto-generated method stub
					String lo1=o1.toLowerCase();
					String lo2=o2.toLowerCase();
					return lo1.compareTo(lo2);
				}
			});
		}

		public void startElement(String nameSpaceURI, String localName,String rawName, Attributes attributs) throws SAXException {
			if(localName.equals("étage") || localName.equals("RDC")){
				etage=true;
			}
			else if (etage) {
				piece.add(localName);
			}
		}

		public void endElement(String uri, String localName,String qName) throws SAXException {
			if(localName.equals("étage") || localName.equals("RDC")){
				etage=false;
			}
		}

		public void endDocument() throws SAXException {
			Iterator<String> it=piece.iterator();
			int i=0;
			while(it.hasNext()){
				if(i++!=0){
					System.out.print(", ");
				}
				System.out.print(it.next());
			}
		}

		public static void main(String args[]) {
			try {
				XMLReader saxReader = XMLReaderFactory.createXMLReader();
				saxReader.setContentHandler(new PieceNameHandler());
				saxReader.parse(args[0]);
			} catch (Exception t) {
				t.printStackTrace();
			}
		}	
}