package org.screen.core.models;

public class Avtale {

    private int id;
    private String dateCreated;
    private String description;
    private String dateTime;
    private int pleietrengende_id;
    private int parorende_id;

    public Avtale(String description, String dateTime) {
        this.description = description;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getPleietrengende_id() {
        return pleietrengende_id;
    }

    public void setPleietrengende_id(int pleietrengende_id) {
        this.pleietrengende_id = pleietrengende_id;
    }

    public int getParorende_id() {
        return parorende_id;
    }

    public void setParorende_id(int parorende_id) {
        this.parorende_id = parorende_id;
    }

    @Override
    public String toString() {
        return "Avtale{" +
                "id=" + id +
                ", dateCreated='" + dateCreated + '\'' +
                ", description='" + description + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", pleietrengende_id=" + pleietrengende_id +
                ", parorende_id=" + parorende_id +
                '}';
    }
}
