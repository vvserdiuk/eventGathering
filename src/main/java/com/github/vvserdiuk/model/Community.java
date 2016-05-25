package com.github.vvserdiuk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.net.URI;
import java.util.Set;

/**
 * Created by vvserdiuk on 19.03.2016.
 */
@Entity
@Table(name = "communities")
public class Community {

    @GeneratedValue
    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String vkLink;

//    @Column(nullable = false)
    private String imageLink;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "community")
    @JsonIgnore
    private Set<Event> events;

    public Community() {
    }

    public Community(Integer id, String title, String vkLink) {
        this.id = id;
        this.title = title;
        this.vkLink = vkLink;
    }

    public Community(String title, String vkLink) {
        this.title = title;
        this.vkLink = vkLink;
    }
    public Integer getId() {
        return id;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", vkLink='" + vkLink + '\'' +
                '}';
    }
}
