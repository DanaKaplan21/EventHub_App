package com.example.eventhub.models;

import java.util.List;

public class Event {
    private String _id;
    private String date;
    private String description;
    private String location;
    private String organizerEmail;
    private String title;
    private List<Guest> invitees;

    public Event() {}

    public Event(String id, String date, String description, String location, String organizerEmail, String title, List<Guest> invitees) {
        this._id = id;
        this.date = date;
        this.description = description;
        this.location = location;
        this.organizerEmail = organizerEmail;
        this.title = title;
        this.invitees = invitees;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Guest> getInvitees() {
        return invitees;
    }

    public void setInvitees(List<Guest> invitees) {
        this.invitees = invitees;
    }
}
