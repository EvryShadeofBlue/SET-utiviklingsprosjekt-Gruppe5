package models;

import java.time.LocalDateTime;

public class Beskjed {
    private int beskjedId; // Brukes kun for testing
    private String beskrivelse;
    private int synligTidsenhet; // Tenker i utgangspunktet 12, 24, 36 timer osv
    private LocalDateTime datoOgTid;


    // konstruktøren under er til testingen.
    public Beskjed(int beskjedId, LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet) {
        this.beskjedId = beskjedId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.synligTidsenhet = synligTidsenhet;
    }

    // den faktiske konstryktøren som skal benyttes
    public Beskjed(LocalDateTime datoOgTid, String beskrivelse, int synligTidsenhet) {
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.synligTidsenhet = synligTidsenhet;
    }

    //get og set metoder

    public int getBeskjedId() {
        return beskjedId;
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
}
