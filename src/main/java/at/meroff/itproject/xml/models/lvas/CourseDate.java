package at.meroff.itproject.xml.models.lvas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fragner on 01.06.17.
 */
@XmlRootElement(name = "course-date")
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseDate {
    String date;
    @XmlElement(name = "time-begin")
    String timebegin;
    @XmlElement(name = "time-end")
    String timeend;
    String location;
    String theme;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimebegin() {
        return timebegin;
    }

    public void setTimebegin(String timebegin) {
        this.timebegin = timebegin;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "CourseDate{" +
                "date='" + date + '\'' +
                ", timebegin='" + timebegin + '\'' +
                ", timeend='" + timeend + '\'' +
                ", location='" + location + '\'' +
                ", theme='" + theme + '\'' +
                '}' + "\n";
    }
}
