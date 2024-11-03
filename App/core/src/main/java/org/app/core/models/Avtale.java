package org.app.core.models;

import java.time.LocalDateTime;

public class Avtale {
    private int avtaleId;
    private LocalDateTime datoOgTid;
    private String beskrivelse;
    private Parorende parorende;
    private Pleietrengende pleietrengende;
    private String gjentakelse;
    private LocalDateTime sluttDato;

    // Denne konstruktøren har blitt benyttet til testing
    public Avtale(int avtaleId, LocalDateTime datoOgTid, String beskrivelse) {
        //this.avtaleId = avtaleId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
    }

    // Denne konstruktøren er hva som skal benyttes senere
    public Avtale (LocalDateTime datoOgTid, String beskrivelse) {
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
    }

    public Avtale (int avtaleId, LocalDateTime datoOgTid, String beskrivelse, String gjentakelse, LocalDateTime sluttDato){
        //this.avtaleId = avtaleId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.gjentakelse = gjentakelse;
        this.sluttDato = sluttDato;
    }

    public int getAvtaleId() {
        return avtaleId;
    }

    public void setAvtaleId(int avtaleId) {
        this.avtaleId = avtaleId;
    }

    public LocalDateTime getDatoOgTid() {
        return datoOgTid;
    }
    public void setDatoOgTid(LocalDateTime datoOgTid) {
        this.datoOgTid = datoOgTid;
    }

    public String getBeksrivelse() {
        return beskrivelse;
    }
    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
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

    public String getGjentakelse() {
        return gjentakelse;
    }
    public void setGjentakelse(String gjentakelse) {
        this.gjentakelse = gjentakelse;
    }

    public LocalDateTime getSluttDato() {
        return sluttDato;
    }
    public void setSluttDato(LocalDateTime sluttDato) {
        this.sluttDato = sluttDato;
    }



}
