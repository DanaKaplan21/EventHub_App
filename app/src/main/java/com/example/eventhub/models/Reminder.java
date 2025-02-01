package com.example.eventhub.models;

import java.util.Date;

public class Reminder {
    private String id;
    private String eventId;
    private String recipientEmail;
    private Date sentAt;

    public Reminder() {
    }

    public Reminder(String id, String eventId, String recipientEmail, Date sentAt) {
        this.id = id;
        this.eventId = eventId;
        this.recipientEmail = recipientEmail;
        this.sentAt = sentAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }


    @Override
    public String toString() {
        return "Reminder{" +
                "id='" + id + '\'' +
                ", eventId='" + eventId + '\'' +
                ", recipientEmail='" + recipientEmail + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}
