package com.github.vvserdiuk.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by vvserdiuk on 04.03.2016.
 */

@Entity
@Table(name = "events")
public class Event {

    @GeneratedValue
    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String imageLink;

    @Column(nullable = false)
    private String vkLink;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Length(max = 10_000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="community_id")
//    @JsonBackReference
    private Community community;

    public Event() {
    }

    public Event(String imageLink, String title, String vkLink, LocalDateTime startDateTime, String description) {
        this.imageLink = imageLink;
        this.title = title;
        this.vkLink = vkLink;
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

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
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
