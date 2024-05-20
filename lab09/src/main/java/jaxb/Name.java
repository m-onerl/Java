package jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Name {
    @XmlAttribute(name = "type")
    private String typeAttribute;

    @XmlElement(name = "value")
    private String valueElement;

    public String getType() {
        return typeAttribute;
    }

    public void setType(String type) {
        this.typeAttribute = type;
    }

    public String getValue() {
        return valueElement;
    }

    public void setValue(String value) {
        this.valueElement = value;
    }

    @Override
    public String toString() {
        return typeAttribute != null ? typeAttribute + ": " + valueElement : valueElement;
    }
}
