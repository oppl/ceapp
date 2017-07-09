package at.meroff.itproject.xml.models.lvas;

import at.meroff.itproject.domain.enumeration.Semester;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fragner on 01.06.17.
 */
@XmlRootElement(name = "lva")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlLva {
    @XmlElement
    String id;

    @XmlElement
    String name;

    @XmlElement
    String subjectType;

    @XmlElement
    String term;

    @XmlElement(name = "course-date")
    Set<CourseDate> courseDates = new HashSet<>();

    public XmlLva() {
    }

    public XmlLva(String id, String name, String subjectType) {
        this.id = id;
        this.name = name;
        this.subjectType = subjectType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public Set<CourseDate> getCourseDates() {
        return courseDates;
    }

    public void setCourseDates(Set<CourseDate> courseDates) {
        this.courseDates = courseDates;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getTermYear() {
        return Integer.parseInt(term.substring(0,4));
    }

    public Semester getTermSemester() {
        return (term.charAt(4) == 'S') ? Semester.SS : Semester.WS;
    }

    @Override
    public String toString() {
        return "Lva{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", subjectType='" + subjectType + '\'' +
                ", courseDates=" + courseDates +
                '}' + "\n";
    }

}
