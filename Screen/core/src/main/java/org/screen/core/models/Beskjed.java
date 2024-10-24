package org.screen.core.models;

public class Beskjed {

    private int id;
    private String dateCreated;
    private String description;
    private String dateTime;
    private int visibleTime;
    private int pleietrengende_id;
    private int parorende_id;

    public Beskjed(int id, String dateCreated, String description, String dateTime, int visibleTime,
                   int pleietrengende_id, int parorende_id) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.description = description;
        this.dateTime = dateTime;
        this.visibleTime = visibleTime;
        this.pleietrengende_id = pleietrengende_id;
        this.parorende_id = parorende_id;
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

    public int getVisibleTime() {
        return visibleTime;
    }

    public void setVisibleTime(int visibleTime) {
        this.visibleTime = visibleTime;
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
        return "Beskjeder{" +
                "id=" + id +
                ", dateCreated='" + dateCreated + '\'' +
                ", description='" + description + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", visibleTime=" + visibleTime +
                ", pleietrengende_id=" + pleietrengende_id +
                ", parorende_id=" + parorende_id +
                '}';
    }
}
