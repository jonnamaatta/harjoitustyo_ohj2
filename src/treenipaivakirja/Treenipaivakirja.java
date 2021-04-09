package treenipaivakirja;

import java.io.File;
import java.util.Collection;

/**
 * Treenipäiväkirja-luokka, joka huolehtii harjoituskerroista ja lajeista. Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" harjoituskertoihin.
 *
 * @author Jonna Määttä
 * @version 9.4.2021
 * 
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import treenipaivakirja.SailoException;
 *  private Treenipaivakirja treeni;
 *  private Harjoituskerta har1;
 *  private Harjoituskerta har2;
 *  private Laji l11;
 *  private Laji l12;
 *  private Laji l21; 
 *  private Laji l22; 
 *  private Laji l23;
 *  
 *  public void alustaTreenipaivakirja() {
 *  treeni = new Treenipaivakirja();
 *  har1 = new Harjoituskerta(); har1.vastaaJuoksu(); har1.rekisteroi();
 *  har2 = new Harjoituskerta(); har2.vastaaJuoksu(); har2.rekisteroi();
 *  l11 = new Laji(); l11.vastaaJuoksu();
 *  l12 = new Laji(); l12.vastaaJuoksu();
 *  l21 = new Laji(); l21.vastaaJuoksu(); 
 *  l22 = new Laji(); l22.vastaaJuoksu(); 
 *  l23 = new Laji(); l23.vastaaJuoksu();
 *  try {
 *      treeni.lisaa(har1);
 *      treeni.lisaa(har2);
 *      treeni.lisaa(l11);
 *      treeni.lisaa(l12);
 *      treeni.lisaa(l21);
 *      treeni.lisaa(l22);
 *      treeni.lisaa(l23);
 *   } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *  }
 *  }
 * </pre>
*/
public class Treenipaivakirja {
    private Harjoituskerrat harjoitukset = new Harjoituskerrat();
    private Lajit           lajit        = new Lajit(); 


    /**
     * Lukee treenipäiväkirjaan tiedot tiedostosta.
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #THROWS IOException
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  String tiedNimi = "testitreenit";
     *  File dir = new File(tiedNimi);
     *  File ftied = new File(tiedNimi+"/lajit.dat");
     *  File fhtied  = new File(tiedNimi+"/harjoitukset.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  fhtied.delete();
     *  treeni = new Treenipaivakirja();
     *  treeni.lueTiedostosta(tiedNimi); 
     *  alustaTreenipaivakirja();
     *  treeni.setTiedosto(tiedNimi);
     *  treeni.tallenna();
     *  treeni = new Treenipaivakirja();
     *  treeni.lueTiedostosta(tiedNimi);
     *  treeni.getLajit() === 5;
     *  treeni.getHarjoitukset() === 2;
     *  dir.delete();
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
            harjoitukset.tallenna();
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
     * Lisää treenipäiväkirjaan uuden harjoituskerran.
     * @param harjoitus lisättävä harjoituskerta
     * @throws SailoException jos lisäystä ei voi tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     *  alustaTreenipaivakirja();
     *  treeni.etsiHarjoitus("*",0).size() === 2;
     *  Harjoituskerta har3 = new Harjoituskerta();
     *  treeni.lisaa(har3);
     *  treeni.etsiHarjoitus("*",0).size() === 3;
     *  </pre>
     */
    public void lisaa(Harjoituskerta harjoitus) throws SailoException {
        harjoitukset.lisaa(harjoitus);
    }

    
    /**
     * Lisätään uusi laji.
     * @param laj lisättävä laji
     * @throws SailoException poikkeus
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     *  alustaTreenipaivakirja();
     *  treeni.etsiLaji("*",0).size() === 5;
     *  Laji l24 = new Laji();
     *  treeni.lisaa(l24);
     *  treeni.etsiLaji("*",0).size() === 6;
     *  </pre>
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
     * alustaTreenipaivakirja();
     * treeni.etsiHarjoitus("*",0).size() === 2;
     * treeni.poista(har1) === 1;
     * treeni.etsiHarjoitus("*",0).size() === 1;
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
     * alustaTreenipaivakirja();
     * treeni.etsiLaji("*",0).size() === 5;
     * treeni.poistaLaji(l11);
     * treeni.etsiLaji("*",0).size() === 4;
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
            tiistai1.vastaaJuoksu();
            tiistai2.rekisteroi();
            tiistai2.vastaaJuoksu();

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