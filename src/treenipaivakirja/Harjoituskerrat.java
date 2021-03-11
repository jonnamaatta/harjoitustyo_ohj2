package treenipaivakirja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Harjoituskerrat joka osaa mm. lisätä uuden harjoituskerran
 *
 * @author Jonna Määttä
 * @version 11.3.2021
 */

public class Harjoituskerrat  {
    
    private static final int MAX_HARJOITUKSIA = 10;
    private int              lkm              = 0;
    private String           tiedostonNimi    = "";
    private Harjoituskerta   alkiot[]; 
    private String tiedostonPerusNimi = "";

    
    /**
     * Luodaan alustava taulukko
     */
    public Harjoituskerrat() {
        alkiot = new Harjoituskerta[MAX_HARJOITUKSIA];
    }
    
    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    

    /**
     * Lukee harjoituskerrat tiedostosta.  
     * TODO TESTIT
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String tiedNimi = hakemisto + "/harjoitukset.dat";
        File ftied = new File(tiedNimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Harjoituskerta h = new Harjoituskerta();
                h.parse(s); // voisi palauttaa jotakin?
                lisaa(h);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + tiedNimi);  
       } // catch (IOException e) {
        //    throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
  
    
    
    /**
     * Tallentaa harjoituskerrat tiedostoon.  
     * TODO Kesken.
     * @param tiednimi tallennettavan tiedoston nimi
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna(String tiednimi) throws SailoException {
        File ftied = new File(tiednimi + "/harjoitukset.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (int i = 0; i < getLkm(); i++) {
                Harjoituskerta h = anna(i);
                fo.println(h);
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + "ei aukea");
        }
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
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20);
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

        try {
            harjoitukset.lueTiedostosta("treenit");
            } catch (SailoException e) {
                System.err.println(e.getMessage());
            }
            
        
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
                System.out.println("Harjoituskerta nro: " + i);
                harjoitus.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }    
        
        try {
            harjoitukset.tallenna("treenit");
        } catch (SailoException e) {
            e.printStackTrace();
        }
    }

   }