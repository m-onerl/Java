package testingStaX_2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
 
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
 
public class StaXParserXMLStreamExample {
   public static void main(String[] args) {
      boolean bFirstName = false;
      boolean bLastName = false;
      boolean bRole = false;
      boolean bAge = false;
      try {
          XMLInputFactory factory = XMLInputFactory.newInstance();
          XMLStreamReader streamReader = factory.createXMLStreamReader(new FileInputStream(
                                         "employees.xml"));
          while (streamReader.hasNext()) {
                int eventType = streamReader.next();
                switch (eventType) {
                   case XMLStreamConstants.START_ELEMENT:
                        String qName = streamReader.getLocalName();
                        if (qName.equalsIgnoreCase("Employee")) {
                            System.out.println("Start Element : " + qName);
                            String id = streamReader.getAttributeValue(0);
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
                        String characters = streamReader.getText();
                        if (bAge) {
                            System.out.println("Age: " + characters);
                            bAge = false;
                        }
                        if (bFirstName) {
                            System.out.println("First Name: " + characters);
                            bFirstName = false;
                        }
                        if (bLastName) {
                            System.out.println("Last Name: " + characters);
                            bLastName = false;
                        }
                        if (bRole) {
                            System.out.println("Role: " + characters);
                            bRole = false;
                        }
                        break;
                   case XMLStreamConstants.END_ELEMENT:
                        String endName = streamReader.getName().getLocalPart();
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