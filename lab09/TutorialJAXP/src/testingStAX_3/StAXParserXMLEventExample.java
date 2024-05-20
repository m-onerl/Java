package testingStAX_3;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
 
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
 
public class StAXParserXMLEventExample {
    public static void main(String[] args) {
        boolean bFirstName = false;
        boolean bLastName = false;
        boolean bRole = false;
        boolean bAge = false;
        try {
             XMLInputFactory factory = XMLInputFactory.newInstance();
             XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream(
                                          "employees.xml"));
             while (eventReader.hasNext()) {
                   XMLEvent event = eventReader.nextEvent();
                   switch (event.getEventType()) {
                      case XMLStreamConstants.START_ELEMENT:
                           StartElement startElement = event.asStartElement();
                           String qName = startElement.getName().getLocalPart();
                           if (qName.equalsIgnoreCase("Employee")) {
                               System.out.println("Start Element : " + qName);
                               Iterator<Attribute> attributes = startElement.getAttributes();
                               String id = attributes.next().getValue();
                               System.out.println("Id : " + id);
                           } else if (qName.equalsIgnoreCase("age")) {
                               bAge = true;
                           } else if (qName.equalsIgnoreCase("firstname")) {
                               bFirstName = true;
                           } else if (qName.equalsIgnoreCase("lastname")) {
                               bLastName = true;
                           } else if (qName.equalsIgnoreCase("role")) {
                               bRole = true;
                           }
                      break;
                      case XMLStreamConstants.CHARACTERS:
                           Characters characters = event.asCharacters();
                           if (bAge) {
                               System.out.println("Age: " + characters.getData());
                               bAge = false;
                           }
                           if (bFirstName) {
                               System.out.println("First Name: " + characters.getData());
                               bFirstName = false;
                           }
                           if (bLastName) {
                               System.out.println("Last Name: " + characters.getData());
                               bLastName = false;
                           }
                           if (bRole) {
                               System.out.println("Role: " + characters.getData());
                               bRole = false;
                           }
                      break;
                      case XMLStreamConstants.END_ELEMENT:
                           EndElement endElement = event.asEndElement();
                           String endName = endElement.getName().getLocalPart();
                           if (endName.equalsIgnoreCase("Employee")) {
                               System.out.println("End Element :" + endName);
                               System.out.println();
                           }
                      break;
                   }
              }
          } catch (FileNotFoundException | XMLStreamException e) {
                e.printStackTrace();
       }
    }
}