package xdpr2.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Classe amb tota l'informacio que ha de contenir un missatge sobre un hospital ja sigui en el cas Client a Servidor o Servidor a Client
 * En el cas Servidor a Client es retornen les mitjanes
 * L'idea d'aquesta classe es que en el cas de vulguer afegir nous camps als missatges sigui simple
 */
public class Message<T> implements Serializable {
    private String sanitaryRegion;
    private T positives, deaths, newICU, releasesICU;

    /**
     * Constructor basic d'un missatge.
     * @param sanaitaryRegion Regio sanitaria a la cual pertany l'hospital
     * @param positives Nous positius en les últimes 24 hores
     * @param deaths Defuncions en les últimes 24 hores
     * @param newICU Altes a la UCI les últimes 24 hores
     * @param releasesICU Baixes de la UCI les últimes 24 hores
     */
    public Message(String sanaitaryRegion, T positives, T deaths, T newICU, T releasesICU) {
        this.sanitaryRegion = sanaitaryRegion;
        this.positives = positives;
        this.deaths = deaths;
        this.newICU = newICU;
        this.releasesICU = releasesICU;
    }

    public Message() {
        this.sanitaryRegion = null;
        this.positives = null;
        this.deaths = null;
        this.newICU = null;
        this.releasesICU = null;
    }

    public void writeToFile (String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        String info = this.sanitaryRegion + ": Positius:" + this.positives + ", Morts: " + this.deaths + ", Altes UCI: " + this.newICU + ", Baixes UCI: " + this.releasesICU + "\n";
        writer.append(info);
        writer.close();
    }

    public String getSanitaryRegion() {
        return sanitaryRegion;
    }

    public T getPositives() {
        return positives;
    }

    public T getDeaths() {
        return deaths;
    }

    public T getNewICU() {
        return newICU;
    }

    public T getReleasesICU() {
        return releasesICU;
    }

    public void setSanitaryRegion(String sanitaryRegion) {
        this.sanitaryRegion = sanitaryRegion;
    }

    public void setPositives(T positives) {
        this.positives = positives;
    }

    public void setDeaths(T deaths) {
        this.deaths = deaths;
    }

    public void setNewICU(T newICU) {
        this.newICU = newICU;
    }

    public void setReleasesICU(T releasesICU) {
        this.releasesICU = releasesICU;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sanitaryRegion='" + sanitaryRegion + '\'' +
                ", positives=" + positives +
                ", deaths=" + deaths +
                ", newICU=" + newICU +
                ", releasesICU=" + releasesICU +
                '}';
    }
}