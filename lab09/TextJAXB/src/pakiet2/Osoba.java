package pakiet2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OsobaType", propOrder = {
    "Imie"
})
@XmlRootElement(name = "Osoby")
public class Osoba {
    @XmlElement(name = "Imie", required = true)
	private String imie;
    @XmlAttribute(name = "Wiek")
    private int wiek;
public String getImie() {
	return imie;
}
public void setImie(String imie) {
	this.imie = imie;
}
public int getWiek() {
	return wiek;
}
public void setWiek(int wiek) {
	this.wiek = wiek;
}
  
}
