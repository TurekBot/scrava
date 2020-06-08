package com.example.handlingformsubmission;


import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 *
 * @author Marco Jakob
 */
@XmlRootElement(name = "athletes")
public class AthleteListWrapper {

    private Set<AthleteSubmission> athletes;

    @XmlElement(name = "athlete")
    public Set<AthleteSubmission> getAthletes() {
        return athletes;
    }

    public void setAthletes(Set<AthleteSubmission> athletes) {
        this.athletes = athletes;
    }
}