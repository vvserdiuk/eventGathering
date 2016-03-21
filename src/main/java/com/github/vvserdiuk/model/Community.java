package com.github.vvserdiuk.model;

import javax.persistence.*;
import java.net.URI;
import java.util.Set;

/**
 * Created by vvserdiuk on 19.03.2016.
 */
@Entity
public class Community {

    @GeneratedValue
    @Id
    private Integer id;

    private String title;

    private String vkLink;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "community")
    private Set<Event> events;

    public Community() {
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
