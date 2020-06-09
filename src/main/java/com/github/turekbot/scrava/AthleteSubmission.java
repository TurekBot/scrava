package com.github.turekbot.scrava;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.Objects;

@XmlRootElement(name = "athlete")
@XmlType(propOrder = {"name", "url", "mileage", "elevation", "avatarUrl"})
public class AthleteSubmission {

    private String name;
    private URI url;
    private Double mileage;
    private Double elevation;
    private URI avatarUrl;

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public URI getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = URI.create(url);
    }
    @XmlElement
    public void setUrl(URI url) {
        this.url = url;
    }

    public Double getMileage() {
        return mileage;
    }

    @XmlElement
    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }


    public Double getElevation() {
        return elevation;
    }

    @XmlElement
    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }


    public URI getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = URI.create(avatarUrl);
    }
    @XmlElement
    public void setAvatarUrl(URI avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "AthleteSubmission{" +
                "name='" + name + '\'' +
                ", url=" + url +
                ", mileage=" + mileage +
                ", elevation=" + elevation +
                ", avatarUrl=" + avatarUrl +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AthleteSubmission that = (AthleteSubmission) o;
        return name.equals(that.name) &&
                url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    public double getScore() {
        return mileage + elevation/10;
    }
}
