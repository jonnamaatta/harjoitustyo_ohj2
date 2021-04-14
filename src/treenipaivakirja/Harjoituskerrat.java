package treenipaivakirja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Harjoituskerrat joka osaa mm. lisätä uuden harjoituskerran.
 *
 * @author Jonna Määttä
 * @version 14.4.2021
 * 
 */
public class Harjoituskerrat implements Iterable<Harjoituskerta> {
    
    private static final int MAX_HARJOITUKSIA   = 10;
    private int              lkm                = 0;
    private Harjoituskerta   alkiot[]; 
    private String kokoNimi = "";
    private String           tiedostonPerusNimi = "harjoitukset";
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
     * Palauttaa varakopiotiedoston nimen.
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen. 
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }
    
    
    /**
     * Palauttaa Kerhon koko nimen
     * @return Kerhon koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
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
     *  Harjoituskerta har1 = new Harjoituskerta(), har2 = new Harjoituskerta();
     *  har1.rekisteroi(); har2.rekisteroi();
     *  har1.vastaaJuoksu();
     *  har2.vastaaJuoksu();
     *  String tiedosto = "testiharjoitukset";
     *  String tiedNimi = tiedosto+"/harjoitukset";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(tiedosto);
     *  dir.mkdir();
     *  ftied.delete();
     *  harjoitukset.lueTiedostosta(tiedosto); #THROWS SailoException
     *  harjoitukset.getLkm() === 0;
     *  harjoitukset.lisaa(har1);
     *  harjoitukset.lisaa(har2);
     *  harjoitukset.tallenna();
     *  harjoitukset = new Harjoituskerrat();            // Poistetaan vanhat luomalla uusi
     *  harjoitukset.lueTiedostosta(tiedosto);
     *  harjoitukset.getLkm() === 2;                 // johon ladataan tiedot tiedostosta.
     *  Iterator<Harjoituskerta> i = harjoitukset.iterator();
     *  i.next().equals(har1);
     *  i.next().equals(har2);
     *  i.hasNext() === false;
     *  harjoitukset.lisaa(har2);
     *  harjoitukset.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedosto+"/harjoitukset.bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String tiedNimi = hakemisto + "/harjoitukset";
        setTiedostonPerusNimi(tiedNimi);
        
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Harjoituskerta h = new Harjoituskerta();
                h.parse(rivi); // voisi palauttaa jotakin?
                lisaa(h);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } 
        catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
   
    /**
     * Tallentaa harjoituskerrat tiedostoon.  
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Harjoituskerta har : this) {
                fo.println(har.toString());
            }
            } catch ( FileNotFoundException ex ) {
                throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
            } catch ( IOException ex ) {
                throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
            }
            muutettu = false;
    }

    


    /**
     * Lisää uuden harjoituskerran tietorakenteeseen, ja tekee uuden taulukon jos ei mahdu. Ottaa harjoituskerran omistukseensa.
     * @param harjoitus lisättävän harjoituskerran viite. 
     * @throws SailoException jos lisäys epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * 
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


    
    /** Hakee suosituimman lajin
     * @return suosituin laji
     * @example
     * <pre name="test">
     * #THROWS SailoException
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
     * #THROWS SailoException
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
        for (Harjoituskerta har : this) {
            String kuormittavuus = har.getKuormittavuus();
            if (kuormittavuus.equals("")) {
                summa += 0;
                continue;
            } 
            summa += Double.valueOf(har.getKuormittavuus());
            maara++;
        }
        if (maara == 0) return 0;
        return summa / maara;
    }
    
    
    /** Laskee keskimääräisen matkan
     * @return keskimatka
     * @example
     * <pre name="test">
     * #THROWS SailoException
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
     * @example
     * <pre name="test">
     * #THROWS SailoException
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
     * Poistaa harjoituskerrat, jolla on tietty lajiid
     * @param tunnusNro lajin numero
     */
    public void poistaLajiTn(int tunnusNro) {
           for (Harjoituskerta har : this) {
               if (har.getLajiNro() == tunnusNro) {
                   poista(har.getTunnusNro());                   
               }
           }
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
     *   List<Harjoituskerta> loytyneet;  
     *   loytyneet = (List<Harjoituskerta>)harjoitukset.etsi("*5*",1);  
     *   loytyneet.size() === 1;  
     *   loytyneet.get(0) == har4 === true;  
     *
     *   loytyneet = (List<Harjoituskerta>)harjoitukset.etsi(null,-1);  
     *   loytyneet.size() === 5;  
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
     * @throws IOException jos epäonnistuu
     */
    public static void main(String args[]) throws SailoException, IOException {
        Harjoituskerrat harjoitukset = new Harjoituskerrat();

        try {
            harjoitukset.lueTiedostosta("treenit2");
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
        
        System.out.println("--------------------------------");
        for (int i = 0; i < harjoitukset.getLkm(); i++) {
            Harjoituskerta harjoitus = harjoitukset.anna(i);
            System.out.println("Harjoituskerta nro: " + i);
            harjoitus.tulostaOtsikko(System.out);
            harjoitus.tulostaSisalto(System.out);
        } 
        System.out.println("--------------------------------");
        for (Harjoituskerta harjoitus : harjoitukset) {
            harjoitus.tulostaOtsikko(System.out);
            harjoitus.tulostaSisalto(System.out);
        }
        System.out.println("--------------------------------");
        try {
            harjoitukset.tallenna();
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