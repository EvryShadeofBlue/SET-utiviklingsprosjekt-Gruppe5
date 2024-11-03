package org.app.core.beskjeder;

import org.app.core.models.Parorende;
import org.app.core.models.Pleietrengende;

import java.time.LocalDateTime;

public class Beskjed {
    private int beskjedId; // Brukes kun for testing
    private String beskrivelse;
    private int synligTidsenhet; // Tenker i utgangspunktet 12, 24, 36 timer osv
    private LocalDateTime datoOgTid;
    private Parorende parorende; //ekstra felt
    private Pleietrengende pleietrengende; //ekstra felt


    // konstruktøren under er til testingen.
    public Beskjed(int beskjedId, LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet) {
        this.beskjedId = beskjedId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.synligTidsenhet = synligTidsenhet;
    }

    // den faktiske konstryktøren som skal benyttes
    public Beskjed(LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet, Parorende parorende, Pleietrengende pleietrengende) {
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.synligTidsenhet = synligTidsenhet;
        this.parorende = parorende; //ekstra
        this.pleietrengende = pleietrengende; //ekstra
    }

    public Beskjed(int beskjedId, LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet, Pleietrengende pleietrengende, Parorende parorende) {
        this.beskjedId = beskjedId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.synligTidsenhet = synligTidsenhet;
        this.pleietrengende = pleietrengende;
        this.parorende = parorende;
    }

    public Beskjed() {

    }

    //get og set metoder

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