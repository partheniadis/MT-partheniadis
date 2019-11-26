package com.example.mt_v01.helperclasses;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

public class Challenge {
    public double latitude,longitude;
    private Integer image;
    private String question;
    private String mission;
    private String area;
    private Integer experience_points;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Challenge(Integer image, String question, String mission, String area, Integer experience_points, double latitude, double longitude){
        this.image=image;
        this.question=question;
        this.mission=mission;
        this.area=area;
        this.experience_points=experience_points;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getExperience_points() {
        return experience_points;
    }

    public void setExperience_points(Integer experience_points) {
        this.experience_points = experience_points;
    }
}
