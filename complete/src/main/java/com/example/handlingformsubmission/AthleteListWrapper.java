package com.example.handlingformsubmission;


import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 *
 * @author Marco Jakob
 */
@XmlRootElement(name = "athletes")
public class AthleteListWrapper {

    private List<AthleteSubmission> athletes;

    private LocalDateTime lastUpdated;

    @XmlElement(name = "athlete")
    public List<AthleteSubmission> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<AthleteSubmission> athletes) {
        this.athletes = athletes;
    }

    @XmlAttribute
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public static class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
        public LocalDateTime unmarshal(String v) {
            return LocalDateTime.parse(v);
        }

        public String marshal(LocalDateTime v) {
            return v.toString();
        }
    }
}