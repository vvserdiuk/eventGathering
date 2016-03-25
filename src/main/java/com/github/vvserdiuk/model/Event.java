package com.github.vvserdiuk.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime startDateTime;

    @Length(max = 10_000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="community_id")
    private Community community;

    public Event() {
    }

    public Event(String imageLink, String title, LocalDateTime startDateTime, String description) {
        this.imageLink = imageLink;
        this.title = title;
        this.startDateTime = startDateTime;
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
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
                ", startDateTime=" + startDateTime +
                ", description='" + description + '\'' +
                ", community=" + community +
                '}';
    }
}
