package at.meroff.itproject.xml.models.lvas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by fragner on 01.06.17.
 */
@XmlRootElement(name = "lvas")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlLvas {
    @XmlElement(name = "lva")
    private List<XmlLva> lvas = null;

    public List<XmlLva> getLvas() {
        return lvas;
    }

    public void setLvas(List<XmlLva> lvas) {
        this.lvas = lvas;
    }

    @Override
    public String toString() {
        return "Lvas{" +
                "lvas=" + lvas +
                '}';
    }
}
