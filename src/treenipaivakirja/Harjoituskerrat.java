package treenipaivakirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Harjoituskerrat joka osaa mm. lisätä uuden harjoituskerran.
 *
 * @author Jonna Määttä
 * @version 9.4.2021
 * 
 */
public class Harjoituskerrat implements Iterable<Harjoituskerta> {
    
    private static final int MAX_HARJOITUKSIA   = 10;
    private int              lkm                = 0;
    private Harjoituskerta   alkiot[]; 
    @SuppressWarnings("unused")
    private String           tiedostonNimi      = "";
    @SuppressWarnings("unused")
    private String           tiedostonPerusNimi = "";
    private boolean          muutettu           = false;

    
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
     * Palauttaa harjoituskertojen lukumäärän.
     * @return harjoituskertojen lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
 
    /**
     * Lukee harjoituskerrat tiedostosta.  
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Harjoituskerrat harjoitukset = new Harjoituskerrat();
     *  Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
     *  juoksu1.vastaaJuoksu();
     *  juoksu2.vastaaJuoksu();
     *  String tiedNimi = "testitreenit";
     *  File ftied = new File(tiedNimi+"/harjoitukset.dat");
     *  ftied.delete();
     *  harjoitukset.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  harjoitukset.lisaa(juoksu1);
     *  harjoitukset.lisaa(juoksu2);
     *  harjoitukset.tallenna(tiedNimi);
     *  harjoitukset = new Harjoituskerrat();   // Poistetaan vanhat luomalla uusi
     *  harjoitukset.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Harjoituskerta> i = harjoitukset.iterator();
     *  i.hasNext() === true;
     *  harjoitukset.lisaa(juoksu2);
     *  harjoitukset.tallenna(tiedNimi);
     *  ftied.delete() === true;
     * </pre>
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
        } 
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
     * Lisää uuden harjoituskerran tietorakenteeseen, ja tekee uuden taulukon jos ei mahdu. Ottaa harjoituskerran omistukseensa.
     * @param harjoitus lisättävän harjoituskerran viite. 
     * @example
     * <pre name="test">
     * Harjoituskerrat harjoitukset = new Harjoituskerrat();
     * Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
     * harjoitukset.getLkm() === 0;
     * harjoitukset.lisaa(juoksu1); harjoitukset.getLkm() === 1;
     * harjoitukset.lisaa(juoksu2); harjoitukset.getLkm() === 2;
     * harjoitukset.lisaa(juoksu1); harjoitukset.getLkm() === 3;
     * harjoitukset.anna(0) === juoksu1;
     * harjoitukset.anna(1) === juoksu2;
     * harjoitukset.anna(2) === juoksu1;
     * harjoitukset.anna(1) == juoksu1 === false;
     * harjoitukset.anna(1) == juoksu2 === true;
     * harjoitukset.anna(3) === juoksu1; #THROWS IndexOutOfBoundsException 
     * harjoitukset.lisaa(juoksu1); harjoitukset.getLkm() === 4;
     * harjoitukset.lisaa(juoksu1); harjoitukset.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Harjoituskerta harjoitus) {
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


    
    /** Hakee suosituimman lajin
     * @return suosituin laji
     * @example
     * <pre name="test">
     * Harjoituskerrat harjoitukset = new Harjoituskerrat();
     * Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
     * juoksu1.rekisteroi();
     * juoksu1.vastaaJuoksu();
     * juoksu2.rekisteroi();
     * juoksu2.vastaaJuoksu();
     * juoksu2.setLajiNro(2);
     * harjoitukset.lisaa(juoksu1);
     * harjoitukset.lisaa(juoksu2);
     * harjoitukset.haeSuosituinLaji() === 1;
     * </pre>
     */
    public int haeSuosituinLaji() {
        List<Integer> lajinumerot = new ArrayList<Integer>();
        for (Harjoituskerta har : alkiot) {
            if (har == null) continue;
            lajinumerot.add(har.getLajiNro());
        }
        
        Collections.sort(lajinumerot);

        int suosituin = 1;
        int max_maara = 1; 
        int maara = 1;
        
        for (int i = 1; i < lajinumerot.size() ; i++) {
            if(lajinumerot.get(i) == lajinumerot.get(i-1))
                maara++;
            else{
                if(maara > max_maara){
                    max_maara = maara;
                    suosituin = lajinumerot.get(i-1);
                } 
                maara = 1;
            }
        }
        return suosituin;
    }
    
    
    /** Laskee keskimääräisen kuormittavuuden
     * @return keskikuormittauuvs
     * @example
     * <pre name="test">
     * Harjoituskerrat harjoitukset = new Harjoituskerrat();
     * Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
     * juoksu1.rekisteroi();
     * juoksu1.vastaaJuoksu();
     * juoksu2.rekisteroi();
     * juoksu2.vastaaJuoksu();
     * harjoitukset.lisaa(juoksu1);
     * harjoitukset.lisaa(juoksu2);
     * assertEquals(harjoitukset.laskeKeskiKuormittavuus(), 6, 0.01);
     * </pre>
     */
    public double laskeKeskiKuormittavuus() {
        double summa = 0;
        double maara = 0;
        for (Harjoituskerta har : alkiot) {
            if (har == null) continue;
            String kuormittavuus = har.getKuormittavuus();
            summa += Double.valueOf(kuormittavuus);
            maara++;
        }
        return summa / maara;
    }
    
    
    /** Laskee keskimääräisen matkan
     * @return keskimatka
     * <pre name="test">
     * Harjoituskerrat harjoitukset = new Harjoituskerrat();
     * Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
     * juoksu1.rekisteroi();
     * juoksu1.vastaaJuoksu();
     * juoksu2.rekisteroi();
     * juoksu2.vastaaJuoksu();
     * harjoitukset.lisaa(juoksu1);
     * harjoitukset.lisaa(juoksu2);
     * assertEquals(harjoitukset.laskeKeskimatka(), 7.0, 0.01);
     * </pre>
     */
    public double laskeKeskimatka() {
        double summa = 0;
        double maara = 0;
        for (Harjoituskerta har : alkiot) {
            if (har == null) continue;
            if (har.getMatka() == "") continue;
            String matka = har.getMatka();
            summa += Double.valueOf(matka);
            maara++;
        }
        double keskimatka = summa / maara;
        return Math.round(keskimatka * 100.0) / 100.0;
    }

    
    /**
     * Hakee pisimmän matkan
     * @return pisin matka
     * <pre name="test">
     * Harjoituskerrat harjoitukset = new Harjoituskerrat();
     * Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
     * juoksu1.rekisteroi();
     * juoksu1.vastaaJuoksu();
     * juoksu2.rekisteroi();
     * juoksu2.vastaaJuoksu();
     * juoksu2.setMatka("8.8");
     * harjoitukset.lisaa(juoksu1);
     * harjoitukset.lisaa(juoksu2);
     * assertEquals(harjoitukset.haePisinmatka(), 8.8, 0.01);
     * </pre>
     */
    public double haePisinmatka() {
        double pisin = 0;
        for (Harjoituskerta har : alkiot) {
            if (har == null) continue;
            if (har.getMatka() == "") continue;
            double matkanpituus = Double.valueOf(har.getMatka());
            if (matkanpituus > pisin) {
                pisin = matkanpituus;
            }
        }
        return pisin;
    }
   
    
    /**
     * Korvaa harjoituskerran tietorakenteessa. Ottaa harjoituskerran omistukseensa.
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
     * Poistaa harjoituskerran jolla on valittu tunnusnumero  
     * @param id poistettavan harjoituskerran tunnusnumero 
     * @return 1 jos poistettiin, 0 jos ei löydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
     * Harjoituskerta tiistai1 = new Harjoituskerta(), tiistai2 = new Harjoituskerta(), tiistai3 = new Harjoituskerta(); 
     * tiistai1.rekisteroi(); tiistai2.rekisteroi(); tiistai3.rekisteroi(); 
     * int id1 = tiistai1.getTunnusNro(); 
     * harjoitukset.lisaa(tiistai1); harjoitukset.lisaa(tiistai2); harjoitukset.lisaa(tiistai3); 
     * harjoitukset.poista(id1+1) === 1; 
     * harjoitukset.etsiId(id1+1) === -1; harjoitukset.getLkm() === 2; 
     * harjoitukset.poista(id1) === 1; harjoitukset.getLkm() === 1; 
     * harjoitukset.poista(id1+3) === 0; harjoitukset.getLkm() === 1; 
     * </pre> 
     *  
     */ 
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    } 
    
    
    /** 
     * Etsii harjoituskerran id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return löytyneen harjoituskerran indeksi tai -1 jos ei löydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
     * Harjoituskerta har1 = new Harjoituskerta(), har2 = new Harjoituskerta(), har3 = new Harjoituskerta(); 
     * har1.rekisteroi(); har2.rekisteroi(); har3.rekisteroi(); 
     * int id1 = har1.getTunnusNro(); 
     * harjoitukset.lisaa(har1); harjoitukset.lisaa(har2); harjoitukset.lisaa(har3); 
     * harjoitukset.etsiId(id1+1) === 1; 
     * harjoitukset.etsiId(id1+2) === 2; 
     * </pre> 
     */ 
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
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
     * </pre> 
     */ 
    public Collection<Harjoituskerta> etsi(String hakuehto, int k) { 
        String ehto = "*"; 
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
        int hk = k; 
        if ( hk < 0 ) hk = 1;
        List<Harjoituskerta> loytyneet = new ArrayList<Harjoituskerta>(); 
        for (Harjoituskerta har : this) { 
            if (WildChars.onkoSamat(har.anna(hk), ehto)) loytyneet.add(har);   
        } 
        Collections.sort(loytyneet, Collections.reverseOrder());
        return loytyneet; 
    }
  
    
    /**
     * Testiohjelma harjoituskerroille.
     * @param args ei käytössä
     * @throws SailoException jos epäonnistuu
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
        juoksu1.vastaaJuoksu();
        juoksu2.rekisteroi();
        juoksu2.vastaaJuoksu();

        harjoitukset.lisaa(juoksu1);
        harjoitukset.lisaa(juoksu2);
        
        for (int i = 0; i < harjoitukset.getLkm(); i++) {
            Harjoituskerta harjoitus = harjoitukset.anna(i);
            System.out.println("Harjoituskerta nro: " + i);
            harjoitus.tulostaOtsikko(System.out);
            harjoitus.tulostaSisalto(System.out);
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
    
}