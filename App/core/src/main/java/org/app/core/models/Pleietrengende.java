package org.app.core.models;

import java.util.List;

public class Pleietrengende {
    private int pleietrengendeId;
    private String fornavn;
    private String etternavn;
    private List<Parorende> parorende;

    public Pleietrengende(String fornavn, String etternavn, List<Parorende> parorende) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.parorende = parorende;
    }

    public Pleietrengende(String fornavn, String etternavn) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
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

    public List<Parorende> getParorende() {
        return parorende;
    }
    public void setParorende(List<Parorende> parorende) {
        this.parorende = parorende;
    }
}
