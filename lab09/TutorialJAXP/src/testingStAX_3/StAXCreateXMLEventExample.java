package testingStAX_3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StAXCreateXMLEventExample {
    public static void main(String[] args) {
       // create an XMLOutputFactory
       XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
       // create XMLEventWriter
       XMLEventWriter eventWriter;
       try {
           eventWriter = outputFactory.createXMLEventWriter(
                              new FileOutputStream("D://employees1.xml"));
           // create an EventFactory
           XMLEventFactory eventFactory = XMLEventFactory.newInstance();
           XMLEvent newLine = eventFactory.createDTD("\n");
           XMLEvent tab = eventFactory.createDTD("\t");
           // create and write Start Tag
           StartDocument startDocument = eventFactory.createStartDocument();
           eventWriter.add(startDocument);
           eventWriter.add(newLine);

           // create Employees open tag
           StartElement startElement = eventFactory.createStartElement("", "", "Employees");
           eventWriter.add(startElement);
           eventWriter.add(newLine);
           StartElement employeeElement = eventFactory.createStartElement("","", "Employee");
           eventWriter.add(tab);
           eventWriter.add(employeeElement);
           eventWriter.add(newLine);
           createElement(eventWriter, "age", "28");
           createElement(eventWriter, "firstname", "Arun");
           createElement(eventWriter, "lastname", "Kumar");
           createElement(eventWriter, "role", "Developer");

           EndElement employeeEnd = eventFactory.createEndElement("", "","Employee");
           eventWriter.add(tab);
           eventWriter.add(employeeEnd);
           eventWriter.add(newLine);

           eventWriter.add(tab);
           eventWriter.add(employeeElement);
           eventWriter.add(newLine);
           createElement(eventWriter, "age", "30");
           createElement(eventWriter, "firstname", "Vijay");
           createElement(eventWriter, "lastname", "Kumar");
           createElement(eventWriter, "role", "Test Lead");
           eventWriter.add(tab);
           eventWriter.add(employeeEnd);
           eventWriter.add(newLine);

           EndElement endElement = eventFactory.createEndElement("", "","Employees");
           EndDocument endDocument = eventFactory.createEndDocument();
           eventWriter.add(endElement);
           eventWriter.add(newLine);
           eventWriter.add(endDocument);
           eventWriter.close();
         } catch (FileNotFoundException | XMLStreamException e) {
              e.printStackTrace();
       }
    }

    private static void createElement(XMLEventWriter eventWriter, String name,String value) 
                               throws XMLStreamException {
         XMLEventFactory eventFactory = XMLEventFactory.newInstance();
         XMLEvent newLine = eventFactory.createDTD("\n");
         XMLEvent tab = eventFactory.createDTD("\t");

         // create Start node
         StartElement sElement = eventFactory.createStartElement("", "", name);
         eventWriter.add(tab);
         eventWriter.add(tab);
         eventWriter.add(sElement);
         // create Content
         Characters characters = eventFactory.createCharacters(value);
         eventWriter.add(characters);
         // create End node
         EndElement eElement = eventFactory.createEndElement("", "", name);
         eventWriter.add(eElement);
         eventWriter.add(newLine);
    }
}