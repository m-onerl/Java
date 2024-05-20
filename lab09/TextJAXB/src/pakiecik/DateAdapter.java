package pakiecik;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;


public class DateAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String marshal(Date v) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.format(v);
        }
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.parse(v);
        }
    }

}

/*
 * Then you use the @XmlJavaTypeAdapter annotation to specify that the XmlAdapter should be used for a specific field/property.

@XmlElement(name = "timestamp", required = true) 
@XmlJavaTypeAdapter(DateAdapter.class)
protected Date timestamp; 
*/
