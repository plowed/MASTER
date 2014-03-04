import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class DureeTrajet extends DefaultHandler {

	private int heure,minute,duree;
	private boolean tempsParcours,itineraire;
	private String btemps,unite;

	public void startDocument() throws SAXException {
		heure=0;
		minute=0;
		duree=0;
		tempsParcours=false;
		itineraire=false;
	}

	public void startElement(String nameSpaceURI, String localName,String rawName, Attributes attributs) throws SAXException {
		if (localName.equals("tempsParcours")) {
			tempsParcours=true;
		}else if(tempsParcours){
			btemps=localName;
		}
		else if(localName.equals("itineraire")){
			itineraire=true;
		}
		else if(itineraire && localName.equals("duree")){
			unite=attributs.getValue("unite");
		}
	}
	
	public void characters(char[] ch, int start, int length){
		String s;
		if(tempsParcours && btemps!=null){
			if(btemps.equals("heure")){
				s=new String(ch, start, length);
				heure=Integer.parseInt(s);
			}
			else if(btemps.equals("minute")){
				s=new String(ch, start, length);
				minute=Integer.parseInt(s);
			}
		}
		else if(itineraire && unite!=null){
				if(unite.equals("min")){
					s=new String(ch, start, length);
					duree+=Integer.parseInt(s);
				}
		}
	}

	public void endElement(java.lang.String uri, java.lang.String localName,java.lang.String qName) throws SAXException {
		if (localName.equals("tempsParcours")) {
			tempsParcours=false;
		}
		else if(tempsParcours && (localName.equals("heure") || localName.equals("minute"))){
			btemps=null;
		}
		else if(localName.equals("duree")){
			unite=null;
		}
	}
	
	public void endDocument(){
		int dheure=duree/60;
		int dminute=duree%60;
		System.out.println("durée prévu : "+heure+"h"+minute);
		System.out.println("durée calculé : "+dheure+"h"+dminute);
		if(heure==dheure && minute==dminute){
			System.out.println("la durée indiqué est exacte");
		}
		else{
			System.out.println("la durée indiqué n'est pas exacte");
		}
	}
	
	public static void main(String args[]){
		try {
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			//saxReader.setContentHandler(new SizeHomeHandler());
			saxReader.setContentHandler(new DureeTrajet());
			saxReader.parse(args[0]);
		} catch (Exception t) {
			t.printStackTrace();
		}
	}
}
