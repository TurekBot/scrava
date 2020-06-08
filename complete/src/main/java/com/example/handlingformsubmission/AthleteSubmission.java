package com.example.handlingformsubmission;

import java.net.URI;
import java.util.Objects;


public class AthleteSubmission {

    private String name;
    private URI url;
    private Double mileage;
    private Double elevation;
    private URI avatarUrl;


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

    public Double getMileage() {
        return mileage;
    }



    public void setMileage(String mileageString) {
        this.mileage = Double.parseDouble(
                mileageString
                        .substring(0, mileageString.length() - 2)
        );
    }

    public Double getElevation() {
        return elevation;
    }


    public void setElevation(String elevationString) {
        this.elevation = Double.parseDouble(
                elevationString
                        .substring(0, elevationString.length() - 2)
                        .replace(",", "")
        );

    }

    public URI getAvatarUrl() {
        return avatarUrl;
    }


    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = URI.create(avatarUrl);
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
