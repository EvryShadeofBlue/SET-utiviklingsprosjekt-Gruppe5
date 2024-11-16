package org.app.core.models;


import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Avtale {
    private int avtaleId;
    private LocalDateTime datoOgTid;
    private String beskrivelse;
    private Parorende parorende;
    private Pleietrengende  pleietrengende;
    private String gjentakelse;
    private LocalDateTime sluttDato;

    public Avtale(int avtaleId, LocalDateTime datoOgTid, String beskrivelse) {
        //this.avtaleId = avtaleId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
    }

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

    public Avtale(LocalDateTime datoOgTid, String beskrivelse, Parorende parorende, Pleietrengende pleietrengende) {
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
    }

    public Avtale(LocalDateTime datoOgTid, String beskrivelse, String gjentakelse, LocalDateTime sluttDato, Parorende parorende, Pleietrengende pleietrengende) {
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.gjentakelse = gjentakelse;
        this.sluttDato = sluttDato;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
    }

    public Avtale(int avtaleId, LocalDateTime datoOgTid, String beskrivelse, String gjentakelse, LocalDateTime sluttdato, Pleietrengende pleietrengende, Parorende parorende) {
        this.avtaleId = avtaleId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.gjentakelse = gjentakelse;
        this.sluttDato = sluttdato;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
    }

    public Avtale(int avtaleId, LocalDateTime datoOgTid, String beskrivelse, Pleietrengende pleietrengende, Parorende parorende) {
        this.avtaleId = avtaleId;
        this.datoOgTid = datoOgTid;
        this.beskrivelse = beskrivelse;
        this.parorende = parorende;
        this.pleietrengende = pleietrengende;
    }

    public Avtale() {

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

    public String getBeskrivelse() {
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
