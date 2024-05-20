package testingStaX_2;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class StaXCreateXMLStreamExample {
    public static void main(String[] args) {
        XMLOutputFactory writerFactory = XMLOutputFactory.newInstance();
        try {
            XMLStreamWriter xmlWriter = writerFactory.createXMLStreamWriter(
                                           new FileOutputStream("employees_2.xml"));
            /*
             * Write the XML Declaration. Defaults the XML version to 1.0, and
             * the encoding to utf-8
             */
            xmlWriter.writeStartDocument();
            xmlWriter.writeStartElement("Employees");
            xmlWriter.writeStartElement("Employee");
            xmlWriter.writeAttribute("id", "1");
            xmlWriter.writeStartElement("age");
            xmlWriter.writeCharacters("28");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("firstname");
            xmlWriter.writeCharacters("Arun");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("lastname");
            xmlWriter.writeCharacters("Kumar");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("role");
            xmlWriter.writeCharacters("Developer");
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("Employee");
            xmlWriter.writeAttribute("id", "2");
            xmlWriter.writeStartElement("age");
            xmlWriter.writeCharacters("30");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("firstname");
            xmlWriter.writeCharacters("Vijay");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("lastname");
            xmlWriter.writeCharacters("Kumar");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("role");
            xmlWriter.writeCharacters("Test Lead");
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("Employee");
            xmlWriter.writeAttribute("id", "3");
            xmlWriter.writeStartElement("age");
            xmlWriter.writeCharacters("35");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("firstname");
            xmlWriter.writeCharacters("Senthil");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("lastname");
            xmlWriter.writeCharacters("Kumar");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("role");
            xmlWriter.writeCharacters("Manager");
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();

            xmlWriter.writeStartElement("Employee");
            xmlWriter.writeAttribute("id", "4");
            xmlWriter.writeStartElement("age");
            xmlWriter.writeCharacters("25");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("firstname");
            xmlWriter.writeCharacters("Nandhini");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("lastname");
            xmlWriter.writeCharacters("Sekar");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("role");
            xmlWriter.writeCharacters("Developer");
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();

            // Close any start tags and writes corresponding end tags.
            xmlWriter.writeEndDocument();
            xmlWriter.flush();
            xmlWriter.close();
       } catch (XMLStreamException | IOException e) {
             e.printStackTrace();
    }
  }
}