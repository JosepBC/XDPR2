package xdpr2.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 *  Classe amb tota l'informacio que ha de contenir un missatge sobre un hospital ja sigui en el cas Client a Servidor o Servidor a Client
 *  L'idea d'aquesta classe es que en el cas de vulguer afegir nous camps als missatges sigui simple
 * @param <T> Tipus dels positius, morts, altes i baixes de la UCI. Dissenyat per poder usar enters en la connexio Client a Servidor i floats en la connexio Servidor a Client
 */
public class Message<T> implements Serializable {
    private String sanitaryRegion;
    private T positives, deaths, newICU, releasesICU;

    /**
     * Constructor basic d'un missatge
     * @param sanaitaryRegion Regio sanitaria a la cual pertany l'hospital
     * @param positives Nous positius en les últimes 24 hores
     * @param deaths Defuncions en les ultimes 24 hores
     * @param newICU Altes a la UCI les ultimes 24 hores
     * @param releasesICU Baixes de la UCI les ultimes 24 hores
     */
    public Message(String sanaitaryRegion, T positives, T deaths, T newICU, T releasesICU) {
        this.sanitaryRegion = sanaitaryRegion;
        this.positives = positives;
        this.deaths = deaths;
        this.newICU = newICU;
        this.releasesICU = releasesICU;
    }

    /**
     * Constructor buit que ho inicialitza tot a null
     */
    public Message() {
        this.sanitaryRegion = null;
        this.positives = null;
        this.deaths = null;
        this.newICU = null;
        this.releasesICU = null;
    }

    /**
     * Metode per escriure totes les dades del missatge a un fitxer de text
     * @param fileName Nom del fitxer de text on ho volem guardar
     * @throws IOException Si ha sorgit algun problema al obrir, escriure o tancar el fitxer
     */
    public void writeToFile (String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        String info = this.sanitaryRegion + ": Positius:" + this.positives + ", Morts: " + this.deaths + ", Altes UCI: " + this.newICU + ", Baixes UCI: " + this.releasesICU + "\n";
        writer.append(info);
        writer.close();
    }

    /**
     * Getter de la regio sanitaria
     * @return Nom de la regio sanitaria del missatge
     */
    public String getSanitaryRegion() {
        return sanitaryRegion;
    }

    /**
     * Getter del numero de positius del missatge
     * @return Numero de positius
     */
    public T getPositives() {
        return positives;
    }

    /**
     * Getter del numero de morts del missatge
     * @return Numero de morts
     */
    public T getDeaths() {
        return deaths;
    }

    /**
     * Getter del numero d'altes de la UCI del missatge
     * @return Numero d'altes a la UCI
     */
    public T getNewICU() {
        return newICU;
    }

    /**
     * Getter del numero de baixes de la UCI del missatge
     * @return Numero de baixes de la UCI
     */
    public T getReleasesICU() {
        return releasesICU;
    }

    /**
     * Setter de la regio sanitaria
     * @param sanitaryRegion Nom de la regio sanitaria
     */
    public void setSanitaryRegion(String sanitaryRegion) {
        this.sanitaryRegion = sanitaryRegion;
    }

    /**
     * Setter del numero de positius
     * @param positives Numero de positius
     */
    public void setPositives(T positives) {
        this.positives = positives;
    }

    /**
     * Setter del numero de morts
     * @param deaths Numero de morts
     */
    public void setDeaths(T deaths) {
        this.deaths = deaths;
    }

    /**
     * Setter de les altes a la UCI
     * @param newICU Numero d'altes a la UCI
     */
    public void setNewICU(T newICU) {
        this.newICU = newICU;
    }

    /**
     * Setter de les baixes de la UCI
     * @param releasesICU Numero de baixes a la UCI
     */
    public void setReleasesICU(T releasesICU) {
        this.releasesICU = releasesICU;
    }

    /**
     * Metode toString de la classe
     * @return String amb totes les dades de la classe
     */
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