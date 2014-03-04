import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Ex3 {

	public static void main(String args[]) {
		try {
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			//saxReader.setContentHandler(new SizeHomeHandler());
			saxReader.setContentHandler(new PieceNameHandler());
			saxReader.parse(args[0]);
		} catch (Exception t) {
			t.printStackTrace();
		}
	}
}
