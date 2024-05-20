package jaxp;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SAXParser {
    public static String parse(File xmlFile) {
        StringBuilder sb = new StringBuilder();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                boolean isName = false;
                boolean isCity = false;
                boolean isState = false;
                boolean isCountry = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("name")) {
                        isName = true;
                    } else if (qName.equalsIgnoreCase("city")) {
                        isCity = true;
                    } else if (qName.equalsIgnoreCase("state")) {
                        isState = true;
                    } else if (qName.equalsIgnoreCase("country")) {
                        isCountry = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (isName) {
                        sb.append("Team: ").append(new String(ch, start, length)).append("\n");
                        isName = false;
                    } else if (isCity) {
                        sb.append("City: ").append(new String(ch, start, length)).append("\n");
                        isCity = false;
                    } else if (isState) {
                        sb.append("State: ").append(new String(ch, start, length)).append("\n");
                        isState = false;
                    } else if (isCountry) {
                        sb.append("Country: ").append(new String(ch, start, length)).append("\n");
                        isCountry = false;
                    }
                }
            };
            saxParser.parse(xmlFile, handler);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        return sb.toString();
    }
}
