package model;

public class Beskjed {
    private int beskjedId; // Brukes kun for testing
    private String beskrivelse;
    private int synligTidsenhet; // Tenker i utgangspunktet 12, 24, 36 timer osv

    // konstruktøren under er til testingen.
    public Beskjed(int beskjedId, String beskrivelse, int synligTidsenhet) {
        this.beskjedId = beskjedId;
        this.beskrivelse = beskrivelse;
        this.synligTidsenhet = synligTidsenhet;
    }

    // den faktiske konstryktøren som skal benyttes
    public Beskjed(String beskrivelse, int synligTidsenhet) {
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
}
