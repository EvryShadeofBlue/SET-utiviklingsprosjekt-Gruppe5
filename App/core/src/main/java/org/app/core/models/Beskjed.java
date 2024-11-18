package org.app.core.models;

import java.time.LocalDateTime;

public class Beskjed {
    private int beskjedId;
    private String beskrivelse;
    private int synligTidsenhet;
    private LocalDateTime datoOgTid;
    private Parorende parorende;
    private Pleietrengende pleietrengende;


    public Beskjed(int beskjedId, LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet) {
        this.beskjedId = beskjedId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.synligTidsenhet = synligTidsenhet;
    }

    public Beskjed(LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet, Parorende parorende, Pleietrengende pleietrengende) {
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.synligTidsenhet = synligTidsenhet;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
    }

    public Beskjed(int beskjedId, LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet, Pleietrengende pleietrengende, Parorende parorende) {
        this.beskjedId = beskjedId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.synligTidsenhet = synligTidsenhet;
        this.pleietrengende = pleietrengende;
        this.parorende = parorende;
    }

    public Beskjed(int beskjedId, LocalDateTime datoOgTid, int synligTidsenhet) {
        this.beskjedId = beskjedId;
        this.datoOgTid = datoOgTid;
        this.synligTidsenhet = synligTidsenhet;
    }

    public Beskjed() {

    }


    public int getBeskjedId() {
        return beskjedId;
    }
    public void setBeskjedId (int beskjedId) {
        this.beskjedId = beskjedId;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }
    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public int getSynligTidsenhet() {
        return synligTidsenhet;
    }
    public void setSynligTidsenhet(int synligTidsenhet) {
        this.synligTidsenhet = synligTidsenhet;
    }

    public LocalDateTime getDatoOgTid() {
        return datoOgTid;
    }

    public void setDatoOgTid(LocalDateTime datoOgTid) {
        this.datoOgTid = datoOgTid;
    }

    public Parorende getParorende() {
        return parorende;
    }

    public void setParorende(Parorende parorende) {
        this.parorende = parorende;
    }

    public Pleietrengende getPleietrengende() {
        return pleietrengende;
    }

    public void setPleietrengende(Pleietrengende pleietrengende) {
        this.pleietrengende = pleietrengende;
    }
}