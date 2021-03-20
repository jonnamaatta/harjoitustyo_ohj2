package treenipaivakirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import treenipaivakirja.Lajit.LajitIterator;

/**
 * Harjoituskerrat joka osaa mm. lisätä uuden harjoituskerran
 *
 * @author Jonna Määttä
 * @version 20.3.2021
 */

public class Harjoituskerrat implements Iterable<Harjoituskerta> {
    
    private static final int MAX_HARJOITUKSIA   = 10;
    private int              lkm                = 0;
    private String           tiedostonNimi      = "";
    private Harjoituskerta   alkiot[]; 
    private String           tiedostonPerusNimi = "";
    private boolean muutettu = false;

    
    /**
     * Luodaan alustava taulukko harjoituskerroille.
     */
    public Harjoituskerrat() {
        alkiot = new Harjoituskerta[MAX_HARJOITUKSIA];
    }
    
    
    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta.
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    

    /**
     * Lukee harjoituskerrat tiedostosta.  
     * TODO KESKEN
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
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + tiedNimi);  
       } // catch (IOException e) {
        //    throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
  
    
    /**
     * Tallentaa harjoituskerrat tiedostoon.  
     * @param tiednimi tallennettavan tiedoston nimi
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna(String tiednimi) throws SailoException {
        if ( !muutettu ) return;
        File ftied = new File(tiednimi + "/harjoitukset.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (int i = 0; i < getLkm(); i++) {
                Harjoituskerta h = anna(i);
                fo.println(h);
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + "ei aukea");
        }
        
        muutettu = false;
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
        muutettu = true;
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
     *  loytyneet = harrasteet.annaHarjoituskerrat(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = harrasteet.annaHarjoituskerrat(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == juoksu11 === true;
     *  loytyneet.get(1) == juoksu12 === true;
     *  loytyneet = harrasteet.annaHarjoituskerrat(5);
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
     * Korvaa harjoituskerran tietorakenteessa.  Ottaa harjoituskerran omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva harjoituskerta.  Jos ei löydy,
     * niin lisätään uutena harjoituskertana.
     * @param harjoitus lisättävän harjoituskerran viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Harjoituskerrat harjoitukset = new Harjoituskerrat();
     * Harjoituskerta harjoitus1 = new Harjoituskerta(), harjoitus2 = new Harjoituskerta();
     * harjoitus1.rekisteroi(); harjoitus2.rekisteroi();
     * harjoitukset.getLkm() === 0;
     * harjoitukset.korvaaTaiLisaa(harjoitus1); harjoitukset.getLkm() === 1;
     * harjoitukset.korvaaTaiLisaa(harjoitus2); harjoitukset.getLkm() === 2;
     * Harjoituskerta harjoitus3 = harjoitus1.clone();
     * harjoitus3.setKuormittavuus("3");
     * Iterator<Harjoituskerta> it = harjoitukset.iterator();
     * it.next() == harjoitus1 === true;
     * harjoitukset.korvaaTaiLisaa(harjoitus3); harjoitukset.getLkm() === 2;
     * it = harjoitukset.iterator();
     * Harjoituskerta j0 = it.next();
     * j0 === harjoitus3;
     * j0 == harjoitus3 === true;
     * j0 == harjoitus1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Harjoituskerta harjoitus) throws SailoException {
        int id = harjoitus.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = harjoitus;
                muutettu = true;
                return;
            }
        }
        lisaa(harjoitus);
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
    
    
    /**
     * Luokka harjoituskertojen iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Harjoituskerrat harjoitukset = new Harjoituskerrat();
     * Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
     * juoksu1.rekisteroi(); juoksu2.rekisteroi();
     *
     * harjoitukset.lisaa(juoksu1); 
     * harjoitukset.lisaa(juoksu2); 
     * harjoitukset.lisaa(juoksu1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Harjoituskerta har:harjoitukset)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+har.getTunnusNro());           
     * 
     * String tulos = " " + juoksu1.getTunnusNro() + " " + juoksu2.getTunnusNro() + " " + juoksu1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Harjoituskerta>  i=harjoitukset.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Harjoituskerta har = i.next();
     *   ids.append(" "+har.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Harjoituskerta>  i=harjoitukset.iterator();
     * i.next() == juoksu1  === true;
     * i.next() == juoksu2  === true;
     * i.next() == juoksu1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class HarjoituskerratIterator implements Iterator<Harjoituskerta> {
        private int kohdalla = 0;


    /**
    * Onko olemassa vielä seuraavaa harjoituskertaa
    * @see java.util.Iterator#hasNext()
    * @return true jos on vielä harjoituskertoja
    */
    @Override
    public boolean hasNext() {
        return kohdalla < getLkm();
    }


    /**
     * Annetaan seuraava harjoituskerta
     * @return seuraava harjoituskerta
     * @throws NoSuchElementException jos seuraava alkiota ei enää ole
     * @see java.util.Iterator#next()
     */
     @Override
     public Harjoituskerta next() throws NoSuchElementException {
         if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
         return anna(kohdalla++);
     }


    /**
     * Tuhoamista ei ole toteutettu
     * @throws UnsupportedOperationException aina
     * @see java.util.Iterator#remove()
     */
     @Override
     public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori harjoituskerroistaan.
     * @return harjoituskerta iteraattori
     */
    @Override
    public Iterator<Harjoituskerta> iterator() {
        return new HarjoituskerratIterator();
    }
    

    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien harjoituskertojen viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä harjoituskerroista
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Harjoituskerrat harjoitukset = new Harjoituskerrat();  
     *   Harjoituskerta har1 = new Harjoituskerta(); har1.parse("1|7.12.20|1|44:32|7.0|6|Jaksoin juosta todella hyvin"); 
     *   Harjoituskerta har2 = new Harjoituskerta(); har2.parse("2|9.12.20|1|27:32|4.0|2|Nyt ei kulkenut oikein"); 
     *   Harjoituskerta har3 = new Harjoituskerta(); har3.parse("3|11.12.20|1|44:27|7.0|2|Nyt ei kulkenut oikein"); 
     *   Harjoituskerta har4 = new Harjoituskerta(); har4.parse("4|15.12.20|1|44:32|7.0|6|Jaksoin juosta todella hyvin"); 
     *   Harjoituskerta har5 = new Harjoituskerta(); har5.parse("5|20.12.20|1|27:32|4.0|2|Nyt ei kulkenut oikein"); 
     *   harjoitukset.lisaa(har1); harjoitukset.lisaa(har2); harjoitukset.lisaa(har3); harjoitukset.lisaa(har4); harjoitukset.lisaa(har5);
     *   // TODO: toistaiseksi palauttaa kaikki harjoituskerrat
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Harjoituskerta> etsi(String hakuehto, int k) { 
        Collection<Harjoituskerta> loytyneet = new ArrayList<Harjoituskerta>(); 
        for (Harjoituskerta h : this) { 
            loytyneet.add(h);  
        } 
        return loytyneet; 
    }

    
   }