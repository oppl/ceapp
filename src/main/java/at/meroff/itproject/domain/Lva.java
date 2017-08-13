package at.meroff.itproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import at.meroff.itproject.domain.enumeration.LvaType;

import at.meroff.itproject.domain.enumeration.Semester;

/**
 * A Lva.
 */
@Entity
@Table(name = "lva")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lva")
public class Lva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "lva_nr", nullable = false)
    private String lvaNr;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "lva_type", nullable = false)
    private LvaType lvaType;

    @NotNull
    @Column(name = "jhi_year", nullable = false)
    private Integer year;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "semester", nullable = false)
    private Semester semester;

    @Column(name = "count_appointments")
    private Integer countAppointments;

    @OneToMany(mappedBy = "lva")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appointment> appointments = new HashSet<>();

    @ManyToOne
    private Subject subject;

    @ManyToMany(mappedBy = "lvas")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CurriculumSubject> curriculumsubjects = new HashSet<>();

    @ManyToOne
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLvaNr() {
        return lvaNr;
    }

    public Lva lvaNr(String lvaNr) {
        this.lvaNr = lvaNr;
        return this;
    }

    public void setLvaNr(String lvaNr) {
        this.lvaNr = lvaNr;
    }

    public LvaType getLvaType() {
        return lvaType;
    }

    public Lva lvaType(LvaType lvaType) {
        this.lvaType = lvaType;
        return this;
    }

    public void setLvaType(LvaType lvaType) {
        this.lvaType = lvaType;
    }

    public Integer getYear() {
        return year;
    }

    public Lva year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Semester getSemester() {
        return semester;
    }

    public Lva semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Integer getCountAppointments() {
        return countAppointments;
    }

    public Lva countAppointments(Integer countAppointments) {
        this.countAppointments = countAppointments;
        return this;
    }

    public void setCountAppointments(Integer countAppointments) {
        this.countAppointments = countAppointments;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public Lva appointments(Set<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public Lva addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setLva(this);
        return this;
    }

    public Lva removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setLva(null);
        return this;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Subject getSubject() {
        return subject;
    }

    public Lva subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<CurriculumSubject> getCurriculumsubjects() {
        return curriculumsubjects;
    }

    public Lva curriculumsubjects(Set<CurriculumSubject> curriculumSubjects) {
        this.curriculumsubjects = curriculumSubjects;
        return this;
    }

    public Lva addCurriculumsubject(CurriculumSubject curriculumSubject) {
        this.curriculumsubjects.add(curriculumSubject);
        curriculumSubject.getLvas().add(this);
        return this;
    }

    public Lva removeCurriculumsubject(CurriculumSubject curriculumSubject) {
        this.curriculumsubjects.remove(curriculumSubject);
        curriculumSubject.getLvas().remove(this);
        return this;
    }

    public void setCurriculumsubjects(Set<CurriculumSubject> curriculumSubjects) {
        this.curriculumsubjects = curriculumSubjects;
    }

    public Institute getInstitute() {
        return institute;
    }

    public Lva institute(Institute institute) {
        this.institute = institute;
        return this;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lva lva = (Lva) o;
        if (lva.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lva.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lva{" +
            "id=" + getId() +
            ", lvaNr='" + getLvaNr() + "'" +
            ", lvaType='" + getLvaType() + "'" +
            ", year='" + getYear() + "'" +
            ", semester='" + getSemester() + "'" +
            ", countAppointments='" + getCountAppointments() + "'" +
            "}";
    }
}
