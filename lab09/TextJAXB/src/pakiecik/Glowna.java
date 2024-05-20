package pakiecik;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
 
 
public class Glowna {
 
    public static void main(String args[]) throws Exception {
 
        Glowna g = new Glowna();
	    g.fromXMLtoJava("dane01.xml");
	    g.fromJavaToXML("Nowy.xml");
    }
 
    public void fromXMLtoJava(String fileName) throws Exception {
	File xmlFile = new File(fileName);
	JAXBContext jaxbContext = JAXBContext.newInstance(ImieninyType.class);
	Unmarshaller unmarshaller =  jaxbContext.createUnmarshaller();
	ImieninyType i = (ImieninyType) unmarshaller.unmarshal(xmlFile);
	System.out.println(i.getData());
	for(String s: i.getImie()){
		System.out.println(s);
	}
    }
 
    
    public void fromJavaToXML(String fileName) throws Exception {
 
	ImieninyType i = new ImieninyType();
	GregorianCalendar gregorianCalendar = new GregorianCalendar();
    DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
//    XMLGregorianCalendar now = 
//        datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
    
    XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendarDate(
    		gregorianCalendar.get(Calendar.YEAR), 
    		gregorianCalendar.get(Calendar.MONTH)+1, gregorianCalendar.get(Calendar.DAY_OF_MONTH), 
    		DatatypeConstants.FIELD_UNDEFINED);
    
    i.setData(now);
    		
    i.getImie().add("Sylwester01");  
    i.getImie().add("Sylwestra01");  
  
	JAXBContext jaxbContext = JAXBContext.newInstance(ImieninyType.class);
	Marshaller marshaller =  jaxbContext.createMarshaller();
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
	marshaller.marshal(i, new File(fileName));
 
    }
    
}