package com.findit.model;

public class Item {
    private int id;
    private String title;
    private String description;
    private String category;
    private String location;
    private String dateFound;
    private String status;
    private int postedBy;

    public Item() {}

    public Item(String title, String description, String category,
                String location, String dateFound, int postedBy) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.location = location;
        this.dateFound = dateFound;
        this.status = "available";
        this.postedBy = postedBy;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getDateFound() { return dateFound; }
    public String getStatus() { return status; }
    public int getPostedBy() { return postedBy; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setLocation(String location) { this.location = location; }
    public void setDateFound(String dateFound) { this.dateFound = dateFound; }
    public void setStatus(String status) { this.status = status; }
    public void setPostedBy(int postedBy) { this.postedBy = postedBy; }

    @Override
    public String toString() {
        return title + " | " + category + " | " + location + " | " + status;
    }
}