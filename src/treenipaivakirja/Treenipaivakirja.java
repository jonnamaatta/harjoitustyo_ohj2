package treenipaivakirja;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Treenipäiväkirja-luokka, joka huolehtii harjoituskerroista. Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" harjoituskertoihin.
 *
 * @author Jonna Määttä
 * @version 7.4.2021
 * 
 */
public class Treenipaivakirja {
    private Harjoituskerrat harjoitukset = new Harjoituskerrat();
    private Lajit           lajit        = new Lajit(); 


    /**
     * Lukee treenipäiväkirjaan tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Treenipaivakirja treeni = new Treenipaivakirja();
     *  
     *  Laji Laji1 = new Laji(); laji1.vastaaJuoksu(); laji1.rekisteroi();
     *  Laji laji2 = new Laji(); laji2.vastaaJuoksu(); laji2.rekisteroi();
     *  Harjoituskerta har21 = new Harjoituskerta(); har21.vastaaJuoksu(laji2.getTunnusNro());
     *  Harjoituskerta har11 = new Harjoituskerta(); har11.vastaaJuoksu(laji1.getTunnusNro());
     *  Harjoituskerta har22 = new Harjoituskerta(); har22.vastaaJuoksu(laji2.getTunnusNro()); 
     *  Harjoituskerta har12 = new Harjoituskerta(); har12.vastaaJuoksu(laji1.getTunnusNro()); 
     *  Harjoituskerta har23 = new Harjoituskerta(); har23.vastaaJuoksu(laji2.getTunnusNro());
     *   
     *  String hakemisto = "testitreenit";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/lajit.dat");
     *  File fhtied = new File(hakemisto+"/harjoitukset.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  fhtied.delete();
     *  treenipaivakirja.lueTiedostosta(hakemisto); #THROWS SailoException
     *  treenipaivakirja.lisaa(laji1);
     *  treenipaivakirja.lisaa(laji2);
     *  treenipaivakirja.lisaa(har21);
     *  treenipaivakirja.lisaa(har11);
     *  treenipaivakirja.lisaa(har22);
     *  treenipaivakirja.lisaa(har12);
     *  treenipaivakirja.lisaa(har23);
     *  treenipaivakirja.tallenna();
     *  treenipaivakirja = new Treenipaivakirja();
     *  treenipaivakirja.lueTiedostosta(hakemisto);
     *  Collection<Laji> kaikki = treenipaivakirja.etsi("",-1); 
     *  Iterator<Laji> it = kaikki.iterator();
     *  it.next() === laji1;
     *  it.next() === laji2;
     *  it.hasNext() === false;
     *  List<Harjoituskerta> loytyneet = treenipaivakirja.annaHarjoituskerrat(laji1);
     *  Iterator<Harjoituskerta> ih = loytyneet.iterator();
     *  ih.next() === har11;
     *  ih.next() === har12;
     *  ih.hasNext() === false;
     *  loytyneet = treenipaivakirja.annaHarjoituskerra(laji2);
     *  ih = loytyneet.iterator();
     *  ih.next() === har21;
     *  ih.next() === har22;
     *  ih.next() === har23;
     *  ih.hasNext() === false;
     *  treenipaivakirja.lisaa(laji2);
     *  treenipaivakirja.lisaa(har23);
     *  treenipaivakirja.tallenna();
     *  ftied.delete()  === true;
     *  fhtied.delete() === true;
     *  File fbak = new File(hakemisto+"/lajit.bak");
     *  File fhbak = new File(hakemisto+"/harjoitukset.bak");
     *  fbak.delete() === true;
     *  fhbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        harjoitukset = new Harjoituskerrat(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        lajit = new Lajit();
        setTiedosto(nimi);
        harjoitukset.lueTiedostosta("treenit");
        lajit.lueTiedostosta("treenit");
    }
    
    
    /**
    * Tallentaa treenipäiväkirjan tiedot tiedostoon.  
    * @throws SailoException jos tallettamisessa ongelmia
    */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            harjoitukset.tallenna("treenit");
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            lajit.tallenna("treenit");
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }


    /**
     * Palauttaa lajien määrän.
     * @return lajien määrä
     */
    public int getLajit() {
        return lajit.getLkm();
    }
    
    
    /**
     * Palauttaa harjoitusten määrän.
     * @return harjoitusten määrä
     */
    public int getHarjoitukset() {
        return harjoitukset.getLkm();
    }
    
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien lajien viitteet.
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä lajeista
     */ 
    public Collection<Laji> etsiLaji(String hakuehto, int k) { 
        return lajit.etsi(hakuehto, k); 
    } 
    


    /**
     * Poistaa harjoituskerroista ja lajeista ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako harjoitusta poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }


    /**
     * Lisää treenipäiväkirjaan uuden harjoituskerran.
     * @param harjoitus lisättävä harjoituskerta
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Treenipaivakirja treeni = new Treenipaivakirja();
     * Harjoituskerta tennis = new Harjoituskerta(), tennis2 = new Harjoituskerta();
     * tennis1.rekisteroi(); tennis2.rekisteroi();
     * treenipaivakirja.getHarjoitukset() === 0;
     * treenipaivakirja.lisaa(tennis1); treenipaivakirja.getHarjoitukset() === 1;
     * treenipaivakirja.lisaa(tennis2); treenipaivakirja.getHarjoitukset() === 2;
     * treenipaivakirja.lisaa(tennis1); treenipaivakirja.getHarjoitukset() === 3;
     * treenipaivakirja.getHarjoitukset() === 3;
     * treenipaivakirja.annaHarjoituskerta(0) === tennis1;
     * treenipaivakirja.annaHarjoituskerta(1) === tennis2;
     * treenipaivakirja.annaHarjoituskerta(2) === tennis1;
     * treenipaivakirja.annaHarjoituskerta(3) === tennis1; #THROWS IndexOutOfBoundsException 
     * treenipaivakirja.lisaa(tennis1); treenipaivakirja.getHarjoitukset() === 4;
     * treenipaivakirja.lisaa(tennis1); treenipaivakirja.getHarjoitukset() === 5;
     * treenipaivakirja.lisaa(tennis1);            #THROWS SailoException
     * </pre>
     */
    public void lisaa(Harjoituskerta harjoitus) throws SailoException {
        harjoitukset.lisaa(harjoitus);
    }

    
    /**
     * Lisätään uusi laji.
     * @param laj lisättävä laji
     * @throws SailoException poikkeus
     */
    public void lisaa(Laji laj) throws SailoException {
        lajit.lisaa(laj);
    }

    
    /**
     * Palauttaa i:n harjoituskerran.
     * @param i monesko harjoituskerta palautetaan
     * @return viite i:teen harjoitukseen
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Harjoituskerta annaHarjoituskerta(int i) throws IndexOutOfBoundsException {
        return harjoitukset.anna(i);
    }

    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien harjoituskertojen viitteet.
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä harjoituskerroista
     * @throws SailoException Jos jotakin menee väärin
     */ 
    public Collection<Harjoituskerta> etsiHarjoitus(String hakuehto, int k) throws SailoException { 
        return harjoitukset.etsi(hakuehto, k); 
    } 


    /**
     * @param i lajin indeksi
     * @return laji
     */
    public Laji anna(int i) {
        return lajit.anna(i);
    }
    
    /**
     * Palauttaa lajin lajinumeron perusteella
     * @param lajiNro lajin lajinumero
     * @return lajinumero tunnusnumeron perusteella
     */
    public Laji annaLajiTn(int lajiNro) {
        return lajit.annaLajiTn(lajiNro);
    }
    

    /**
     * Tallettaa treenipäiväkirjan tiedot tiedostoon.
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        harjoitukset.tallenna("treenit");
        // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
    }
   
    
    /**
     * @param laji haluttava laji
     * @return lajin harjoituskerrat
     */
    public List<Harjoituskerta> annaHarjoituskerrat(Laji laji) {
        return harjoitukset.annaHarjoituskerrat(laji.getTunnusNro());
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet.
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        harjoitukset.setTiedostonPerusNimi(hakemistonNimi + "harjoitukset");
        lajit.setTiedostonPerusNimi(hakemistonNimi + "lajit");
    }
    
    
    /** 
     * Korvaa harjoituskerran tietorakenteessa. Ottaa harjoituskerran omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva harjoituskerta. Jos ei löydy, 
     * niin lisätään uutena harjoituskertana. 
     * @param harjoitus lisättävän harjoituskerran viite. Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(Harjoituskerta harjoitus) throws SailoException { 
        harjoitukset.korvaaTaiLisaa(harjoitus); 
    } 
    
    
    /** 
     * Korvaa lajin tietorakenteessa. Ottaa lajin omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva laji. Jos ei löydy, 
     * niin lisätään uutena lajina.
     * @param laji lisättävän lajin viite. Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaaLaji(Laji laji) throws SailoException { 
        lajit.korvaaTaiLisaaLaji(laji); 
    } 
    
    
    /**
     * Poistaa harjoituskerran tiedot
     * @param har harjoituskerta joka poistetaan
     * @return montako harjoituskertaa poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   treenipaivakirja.etsi("*",0).size() === 2;
     *   treenipaivakirja.annaHarjoituskerrat(tiistai1).size() === 2;
     *   treenipaivakirja.poista(tiistai1) === 1;
     *   treenipaivakirja.etsi("*",0).size() === 1;
     *   treenipaivakirja.annaHarjoituskerrat(tiistai1).size() === 0;
     *   treenipaivakirja.annaHarjoituskerrat(tiistai2).size() === 3;
     * </pre>
     */
    public int poista(Harjoituskerta har) {
        if ( har == null ) return 0;
        int ret = harjoitukset.poista(har.getTunnusNro()); 
        return ret; 
    }
    
    /** 
     * Poistaa tämän lajin 
     * @param laji poistettava laji
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   treenipaivakirja.annaLajit(juoksu11).size() === 2;
     *   treenipaivakirja.poistaHarrastus(pitsi11);
     *   treenipaivakirja.annaHarrastukset(juoksu12).size() === 1;
     */ 
    public void poistaLaji(Laji laji) { 
        lajit.poista(laji); 
    } 
    
    
    /**
     * Hakee suosituimman lajin
     * @return suosituin laji
     */
    public int haeSuosituinLaji() {
        return harjoitukset.haeSuosituinLaji();
    }
    
    
    
    /**
     * Laskee keskimääräisen kuormittavuuden
     * @return keskikuormittavuus
     */
    public double laskeKeskikuormittavuus() {
        return harjoitukset.laskeKeskiKuormittavuus();
    }
    
    
    /**
     * Laskee keskimääräisen matkan
     * @return keskimatka
     */
    public double laskeKeskimatka() {
        return harjoitukset.laskeKeskimatka();
    } 
    
    
   /**
    * Hakee pisimmän matkan
    * @return pisin matka
    */
    public double haePisinmatka() {
        return harjoitukset.haePisinmatka();
    }
    
    
    /**
     * Testiohjelma treenipäiväkirjasta.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Treenipaivakirja treenipaivakirja = new Treenipaivakirja();

        try {
            Harjoituskerta tiistai1 = new Harjoituskerta(), tiistai2 = new Harjoituskerta();
            tiistai1.rekisteroi();
            tiistai1.vastaaJuoksu(0);
            tiistai2.rekisteroi();
            tiistai2.vastaaJuoksu(1);

            treenipaivakirja.lisaa(tiistai1);
            treenipaivakirja.lisaa(tiistai2);
            
            
            Laji juoksu11 = new Laji(); juoksu11.rekisteroi(); juoksu11.vastaaJuoksu(); treenipaivakirja.lisaa(juoksu11);
            Laji juoksu12 = new Laji(); juoksu12.rekisteroi(); juoksu12.vastaaJuoksu(); treenipaivakirja.lisaa(juoksu12);
            Laji juoksu21 = new Laji(); juoksu21.rekisteroi(); juoksu21.vastaaJuoksu(); treenipaivakirja.lisaa(juoksu21);
            Laji juoksu22 = new Laji(); juoksu22.rekisteroi(); juoksu22.vastaaJuoksu(); treenipaivakirja.lisaa(juoksu22);
            Laji juoksu23 = new Laji(); juoksu23.rekisteroi(); juoksu23.vastaaJuoksu(); treenipaivakirja.lisaa(juoksu23);

            System.out.println("============= Treenipäiväkirjan testi =================");

            for (int i = 0; i < treenipaivakirja.getHarjoitukset(); i++) {
               
                Harjoituskerta esim = treenipaivakirja.annaHarjoituskerta(i);
                System.out.println("Harjoituskerta paikassa: " + i);
                
                Laji l = treenipaivakirja.lajit.anna(esim.getLajiNro());
                System.out.println(l);
                esim.tulostaOtsikko(System.out);
                esim.tulostaSisalto(System.out);
                            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}