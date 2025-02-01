package com.example.eventhub.models;

public class User {
    private String id; // מזהה ייחודי
    private String name;
    private String email;
    private String role; // תפקיד (לדוגמה: "מארגן", "משתמש רגיל")
    private String password;

    // בנאי ריק (נדרש על ידי ספריות כמו Retrofit/Firebase)
    public User() {
    }

    // בנאי מלא
    public User(String id, String name, String email, String role, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // פונקציה להדפסת המידע של המשתמש
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
