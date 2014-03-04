import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class SizeHomeHandler extends DefaultHandler {

	private float size;
	private String pieceSize;

	public void startDocument() throws SAXException {
	}

	public void startElement(String nameSpaceURI, String localName,String rawName, Attributes attributs) throws SAXException {
		if (localName.equals("maison")) {
			System.out.println("Maison " + attributs.getValue("id")+" :");
			size = 0;
		} else if(pieceSize==null){
			String superficie = attributs.getValue("surface-m2");
			if (superficie != null) {
				size += Float.parseFloat(superficie);
				pieceSize=localName;
			}
		}
	}

	public void endElement(java.lang.String uri, java.lang.String localName,java.lang.String qName) throws SAXException {
		if (localName.equals("maison")) {
			System.out.println("\tsuperficie totale : "+size+" m2");
		}
		else if(pieceSize!=null && pieceSize.equals(localName)){
			pieceSize=null;
		}
	}
	
	public static void main(String args[]) {
		try {
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			saxReader.setContentHandler(new SizeHomeHandler());
			saxReader.parse(args[0]);
		} catch (Exception t) {
			t.printStackTrace();
		}
	}
}
