package com.client.xdpr2;

/**
 * Classe amb tota l'informacio que ha de contenir un missatge sobre un hospital ja sigui en el cas Client a Servidor o Servidor a Client
 * En el cas Servidor a Client es retornen les mitjanes
 * L'idea d'aquesta classe es que en el cas de vulguer afegir nous camps als missatges sigui simple
 */
public class Message {
    private String sanaitaryRegion;
    private int positives, deaths, newICU, releasesICU;

    /**
     * Constructor basic d'un missatge.
     * @param sanaitaryRegion Regio sanitaria a la cual pertany l'hospital
     * @param positives Nous positius en les últimes 24 hores
     * @param deaths Defuncions en les últimes 24 hores
     * @param newICU Altes a la UCI les últimes 24 hores
     * @param releasesICU Baixes de la UCI les últimes 24 hores
     */
    public Message(String sanaitaryRegion, int positives, int deaths, int newICU, int releasesICU) {
        this.sanaitaryRegion = sanaitaryRegion;
        this.positives = positives;
        this.deaths = deaths;
        this.newICU = newICU;
        this.releasesICU = releasesICU;
    }

    /**
     * Getter del nom de la regio sanitaria on es troba l'hospital
     * @return Regio sanitaria de l'instancia
     */
    public String getSanaitaryRegion() {
        return sanaitaryRegion;
    }

    /**
     * Getter del nombre de positius
     * @return Nombre de positius de l'instancia
     */
    public int getPositives() {
        return positives;
    }

    /**
     * Getter de les morts
     * @return Morts de l'instancia
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Getter de les altes a la UCI
     * @return Altes a la UCI de l'instancia
     */
    public int getNewICU() {
        return newICU;
    }

    /**
     * Getter de les baixes a la UCI
     * @return Baixes a la UCI de l'instancia
     */
    public int getReleasesICU() {
        return releasesICU;
    }
}