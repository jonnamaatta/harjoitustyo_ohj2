package treenipaivakirja;

import java.util.ArrayList;
import java.util.List;

/**
 * Harjoituskerrat joka osaa mm. lisätä uuden harjoituskerran
 *
 * @author Jonna Määttä
 * @version 6.3.2021
 */

public class Harjoituskerrat {
    
    private static final int MAX_HARJOITUKSIA = 10;
    private int              lkm              = 0;
    private String           tiedostonNimi    = "";
    private Harjoituskerta   alkiot[];         


    /**
     * Luodaan alustava taulukko
     */
    public Harjoituskerrat() {
        alkiot = new Harjoituskerta[MAX_HARJOITUKSIA];
    }

  

    /**
     * Lisää uuden harjoituskerran tietorakenteeseen.  Ottaa harjoituskerran omistukseensa.
     * @param harjoitus lisättävän harjoituskerran viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Harjoituskerrat harjoitukset = new Harjoituskerrat();
     * Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
     * harjoitukset.getLkm() === 0;
     * harjoitukset.lisaa(juoksu1); harjoitukset.getLkm() === 1;
     * harjoitukset.lisaa(juoksu2); harjoitukset.getLkm() === 2;
     * harjoitukset.lisaa(juoksu1); harjoitukset.getLkm() === 3;
     * harjoitukset.anna(0) === juoksu1;
     * harjoitukset.anna(1) === juoksu2;
     * harjoitukset.anna(2) === juoksu1;
     * harjoitukset.anna(1) === juoksu1 === false;
     * harjoitukset.anna(1) === juoksu2 === true;
     * harjoitukset.anna(3) === juoksu1; #THROWS IndexOutOfBoundsException 
     * harjoitukset.lisaa(juoksu1); harjoitukset.getLkm() === 4;
     * harjoitukset.lisaa(juoksu1); harjoitukset.getLkm() === 5;
     * harjoitukset.lisaa(juoksu1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Harjoituskerta harjoitus) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = harjoitus;
        lkm++;
    }


    /**
     * Palauttaa viitteen i:teen harjoituskertaan.
     * @param i monennenko harjoituskerran viite halutaan
     * @return viite harjoituskertaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Harjoituskerta anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Lukee harjoituskerrat tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa harjoituskerrat tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa harjoitusten lukumäärän
     * @return harjoitusten lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * Haetaan kaikki lajin harjoituskerrat 
     * @param tunnusnro lajin tunnusnumero jolle harjoituksia haetaan
     * @return tietorakenne jossa viiteet löydettyihin harjoituskertoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Lajit harrasteet = new Lajit();
     *  Laji juoksu21 = new Laji(2); harrasteet.lisaa(juoksu21);
     *  Laji juoksu11 = new Laji(1); harrasteet.lisaa(juoksu11);
     *  Laji juoksu22 = new Laji(2); harrasteet.lisaa(juoksu22);
     *  Laji juoksu12 = new Laji(1); harrasteet.lisaa(juoksu12);
     *  Laji juoksu23 = new Laji(2); harrasteet.lisaa(juoksu23);
     *  Laji juoksu51 = new Laji(5); harrasteet.lisaa(juoksu51);
     *  
     *  List<Laji> loytyneet;
     *  loytyneet = harrasteet.annaLajit(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = harrasteet.annaLajit(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == juoksu11 === true;
     *  loytyneet.get(1) == juoksu12 === true;
     *  loytyneet = harrasteet.annaLajit(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == juoksu51 === true;
     * </pre> 
     */
    public List<Harjoituskerta> annaHarjoituskerrat(int tunnusnro) {
        List<Harjoituskerta> loydetyt = new ArrayList<Harjoituskerta>();
        for (Harjoituskerta har : alkiot) {
            if (har == null) continue;
            if (har.getLajiNro() == tunnusnro) loydetyt.add(har);
        }
        return loydetyt;
        
    }


    /**
     * Testiohjelma harjoituskerroille
     * @param args ei käytössä
     * @throws SailoException plääplää
     */
    public static void main(String args[]) throws SailoException {
        Harjoituskerrat harjoitukset = new Harjoituskerrat();

        Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
        juoksu1.rekisteroi();
        juoksu1.vastaaJuoksu(0);
        juoksu2.rekisteroi();
        juoksu2.vastaaJuoksu(1);

        try {
            harjoitukset.lisaa(juoksu1);
            harjoitukset.lisaa(juoksu2);

            for (int i = 0; i < harjoitukset.getLkm(); i++) {
                Harjoituskerta harjoitus = harjoitukset.anna(i);
                System.out.println("Jäsen nro: " + i);
                harjoitus.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }      
    }
}