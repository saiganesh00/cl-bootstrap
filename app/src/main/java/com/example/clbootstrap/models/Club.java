package com.example.clbootstrap.models;

import android.net.Uri;

public class Club {
    private String name;
    private String description;
    private String category;
    private Uri logoUri;
    private String coordinatorName;
    private String coordinatorEmail;
    private String coordinatorPhone;
    private int availableSpots;
    private String creationDate;

    public Club(String name, String description, String category, Uri logoUri) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.logoUri = logoUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Uri getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(Uri logoUri) {
        this.logoUri = logoUri;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }

    public String getCoordinatorEmail() {
        return coordinatorEmail;
    }

    public void setCoordinatorEmail(String coordinatorEmail) {
        this.coordinatorEmail = coordinatorEmail;
    }

    public String getCoordinatorPhone() {
        return coordinatorPhone;
    }

    public void setCoordinatorPhone(String coordinatorPhone) {
        this.coordinatorPhone = coordinatorPhone;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    // Take a persistent copy of the URI
    public void persistLogoUri(Uri uri) {
        if (uri != null) {
            this.logoUri = uri;
        }
    }
}
