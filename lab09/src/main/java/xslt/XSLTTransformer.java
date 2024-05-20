package xslt;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

public class XSLTTransformer {
    public static String transform(File xmlFile, String xslFilePath) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            InputStream xslFile = XSLTTransformer.class.getClassLoader().getResourceAsStream(xslFilePath);
            if (xslFile == null) {
                throw new TransformerConfigurationException("XSL file not found: " + xslFilePath);
            }
            Source xslt = new StreamSource(xslFile);
            Transformer transformer = factory.newTransformer(xslt);

            Source text = new StreamSource(xmlFile);
            StringWriter writer = new StringWriter();
            transformer.transform(text, new StreamResult(writer));

            return writer.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
