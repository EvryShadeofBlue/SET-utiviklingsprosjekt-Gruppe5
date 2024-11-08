package org.app.core.models;

public class Pleietrengende {
    private int pleietrengendeId;
    private String fornavn;
    private String etternavn;
    private Parorende parorende;

    public Pleietrengende(String fornavn, String etternavn, Parorende parorende) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.parorende = parorende;
    }

    public Pleietrengende(String fornavn, String etternavn) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
    }

    public Pleietrengende(int pleietrengendeId, String fornavn, String etternavn, Parorende parorende) {
        this.pleietrengendeId = pleietrengendeId;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.parorende = parorende;
    }

    public Pleietrengende(int pleietrengendeId, String fornavn, String etternavn) {
        this.pleietrengendeId = pleietrengendeId;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
    }

    public Pleietrengende() {

    }

    public int getPleietrengendeId() {
        return pleietrengendeId;
    }

    public void setPleietrengendeId(int pleietrengendeId) {
        this.pleietrengendeId = pleietrengendeId;
    }

    public String getFornavn(){
        return fornavn;
    }
    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }
    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public Parorende getParorende() {
        return parorende;
    }
    public void setParorende(Parorende parorende) {
        this.parorende = parorende;
    }
}
