package model;

import java.time.LocalDateTime;

public class Avtale {
    //private int avtaleId;
    private LocalDateTime datoOgTid;
    private String beskrivelse;
    private int pleietrengendeId; //usikker på om dette er nødvendig
    private int paarerendeId; //usikker på om dette er nødvendig

    //Nye instansvariabler
    private String gjentakelse;
    private LocalDateTime sluttDato;

    public Avtale(int avtaleId, LocalDateTime datoOgTid, String beskrivelse) {
        //this.avtaleId = avtaleId;
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

    /*
    public int getAvtaleId(){
        return avtaleId;
    }

     */

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

    public int getPleietrengendeId() {
        return pleietrengendeId;
    }

    public int getPaarerendeId() {
        return paarerendeId;
    }

    public void setPleietrengendeId(int pleietrengendeId) {
        this.pleietrengendeId = pleietrengendeId;
    }

    public void SetPaarerendeId(int paarerendeId) {
        this.paarerendeId = paarerendeId;
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
