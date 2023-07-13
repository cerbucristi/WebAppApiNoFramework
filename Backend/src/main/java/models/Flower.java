package models;

import java.util.Date;

public class Flower {

    private int id;
    private String name;
    private String kind;
    private Date plantingDate;
    private String ownerEmail;

    private String status;

    public Flower() {
    }

    public Flower(int id, String name, String kind, Date date, String ownerEmail, String status) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.plantingDate = date;
        this.ownerEmail = ownerEmail;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Date getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(Date plantingDate) {
        this.plantingDate = plantingDate;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
