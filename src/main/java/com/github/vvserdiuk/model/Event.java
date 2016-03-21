package com.github.vvserdiuk.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by vvserdiuk on 04.03.2016.
 */

@Entity
public class Event {

    @GeneratedValue
    @Id
    private Integer id;

    private String imageLink;

    private String title;

    private LocalDate startDate;

    private LocalTime startTime;

    @Length(max = 10_000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="community_id")
    private Community community;

    public Event() {
    }

    public Event(String imageLink, String title, LocalDate startDate, LocalTime startTime, String description) {
        this.imageLink = imageLink;
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", imageLink='" + imageLink + '\'' +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", startTime=" + startTime +
                ", description='" + description + '\'' +
                ", community=" + community +
                '}';
    }
}
