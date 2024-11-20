package org.app.core.models;

public class Parorende {
    private int parorendeId;
    private String fornavn;
    private String etternavn;
    private String tlf;
    private String epost;
    private Pleietrengende pleietrengende;

    public Parorende(String fornavn, String etternavn, String tlf, String epost) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.tlf = tlf;
        this.epost = epost;
    }
    public Parorende(int parorendeId, String fornavn, String etternavn, String tlf, String epost) {
        this.parorendeId = parorendeId;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.tlf = tlf;
        this.epost = epost;
    }

    public Parorende(int parorendeId, String parorendeFornavn, String parorendeEtternavn) {
        this.parorendeId = parorendeId;
        this.fornavn = parorendeFornavn;
        this.etternavn = parorendeEtternavn;
    }

    public Parorende() {

    }

    public int getParorendeId() {
        return parorendeId;
    }
    public void setParorendeId(int parorendeId) {
        this.parorendeId = parorendeId;
    }

    public String getFornavn() {
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

    public String getTlf() {
        return tlf;
    }
    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getEpost() {
        return epost;
    }
    public void setEpost(String epost) {
        this.epost = epost;
    }

    public void setPleietrengende(Pleietrengende pleietrengende) {
        this.pleietrengende = pleietrengende;
    }

    public Pleietrengende getPleietrengende() {
        return pleietrengende;
    }
}
