package jaxp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMParser {
    public static String parse(File xmlFile) {
        StringBuilder sb = new StringBuilder();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            sb.append("Root Element: ").append(root.getNodeName()).append("\n");

            NodeList teamList = doc.getElementsByTagName("team");
            for (int i = 0; i < teamList.getLength(); i++) {
                Node teamNode = teamList.item(i);
                if (teamNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element teamElement = (Element) teamNode;
                    sb.append(parseTeam(teamElement));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        return sb.toString();
    }

    private static String parseTeam(Element teamElement) {
        StringBuilder sb = new StringBuilder();
        sb.append("Team: ").append(getTagValue("name", teamElement)).append("\n");
        sb.append("Location: ").append(getTagValue("city", teamElement)).append(", ")
                .append(getTagValue("state", teamElement)).append(", ")
                .append(getTagValue("country", teamElement)).append("\n");
        // Add more parsing details as needed
        return sb.toString();
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                return node.getTextContent();
            }
        }
        return "";
    }
}
