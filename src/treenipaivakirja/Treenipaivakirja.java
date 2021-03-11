package treenipaivakirja;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Treenipäiväkirja-luokka, joka huolehtii harjoituskerroista.  Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" harjoituskertoihin..
 *
 * @author Jonna Määttä
 * @version 11.3.2021
 */
public class Treenipaivakirja {
    
    private Harjoituskerrat harjoitukset = new Harjoituskerrat();
    private Lajit lajit = new Lajit(); 


    /**
     * Lukee kerhon tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Kerho kerho = new Kerho();
     *  
     *  Jasen aku1 = new Jasen(); aku1.vastaaAkuAnkka(); aku1.rekisteroi();
     *  Jasen aku2 = new Jasen(); aku2.vastaaAkuAnkka(); aku2.rekisteroi();
     *  Harrastus pitsi21 = new Harrastus(); pitsi21.vastaaPitsinNyplays(aku2.getTunnusNro());
     *  Harrastus pitsi11 = new Harrastus(); pitsi11.vastaaPitsinNyplays(aku1.getTunnusNro());
     *  Harrastus pitsi22 = new Harrastus(); pitsi22.vastaaPitsinNyplays(aku2.getTunnusNro()); 
     *  Harrastus pitsi12 = new Harrastus(); pitsi12.vastaaPitsinNyplays(aku1.getTunnusNro()); 
     *  Harrastus pitsi23 = new Harrastus(); pitsi23.vastaaPitsinNyplays(aku2.getTunnusNro());
     *   
     *  String hakemisto = "testikelmit";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/nimet.dat");
     *  File fhtied = new File(hakemisto+"/harrastukset.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  fhtied.delete();
     *  kerho.lueTiedostosta(hakemisto); #THROWS SailoException
     *  kerho.lisaa(aku1);
     *  kerho.lisaa(aku2);
     *  kerho.lisaa(pitsi21);
     *  kerho.lisaa(pitsi11);
     *  kerho.lisaa(pitsi22);
     *  kerho.lisaa(pitsi12);
     *  kerho.lisaa(pitsi23);
     *  kerho.tallenna();
     *  kerho = new Kerho();
     *  kerho.lueTiedostosta(hakemisto);
     *  Collection<Jasen> kaikki = kerho.etsi("",-1); 
     *  Iterator<Jasen> it = kaikki.iterator();
     *  it.next() === aku1;
     *  it.next() === aku2;
     *  it.hasNext() === false;
     *  List<Harrastus> loytyneet = kerho.annaHarrastukset(aku1);
     *  Iterator<Harrastus> ih = loytyneet.iterator();
     *  ih.next() === pitsi11;
     *  ih.next() === pitsi12;
     *  ih.hasNext() === false;
     *  loytyneet = kerho.annaHarrastukset(aku2);
     *  ih = loytyneet.iterator();
     *  ih.next() === pitsi21;
     *  ih.next() === pitsi22;
     *  ih.next() === pitsi23;
     *  ih.hasNext() === false;
     *  kerho.lisaa(aku2);
     *  kerho.lisaa(pitsi23);
     *  kerho.tallenna();
     *  ftied.delete()  === true;
     *  fhtied.delete() === true;
     *  File fbak = new File(hakemisto+"/nimet.bak");
     *  File fhbak = new File(hakemisto+"/harrastukset.bak");
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
    * Tallenttaa kerhon tiedot tiedostoon.  
    * Vaikka jäsenten tallettamien epäonistuisi, niin yritetään silti tallettaa
    * harrastuksia ennen poikkeuksen heittämistä.
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
     * Palauttaa harjoitusten määrän
     * @return harjoitusten määrä
     */
    public int getLajit() {
        return lajit.getLkm();
    }
    
    
    /**
     * Palauttaa harjoitusten määrän
     * @return harjoitusten määrä
     */
    public int getHarjoitukset() {
        return harjoitukset.getLkm();
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
     * Lisää treenipäiväkirjaan uuden harjoituksen
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
     * Listään uusi laji 
     * @param laj lisättävä laji
     * @throws SailoException poikkeus
     */
    public void lisaa(Laji laj) throws SailoException {
        lajit.lisaa(laj);
    }

    
    /**
     * Palauttaa i:n harjoituskerran
     * @param i monesko harjoituskerta palautetaan
     * @return viite i:teen harjoitukseen
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Harjoituskerta annaHarjoituskerta(int i) throws IndexOutOfBoundsException {
        return harjoitukset.anna(i);
    }

    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @throws SailoException Jos jotakin menee väärin
     */ 
    public Collection<Laji> etsi(String hakuehto, int k) throws SailoException { 
        return lajit.etsi(hakuehto, k); 
    } 


    /**
     * @param i indeksi
     * @return laji
     */
    public Laji annaLaji(int i) {
        return lajit.annaLaji(i);
    }


    /**
     * Tallettaa treenipäiväkirjan tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        harjoitukset.tallenna("treenit");
        // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
    }
   
    
    /**
     * @param laji haluttava laji
     * @return lajin harjoitukskerrat
     */
    public List<Harjoituskerta> annaHarjoituskerrat(Laji laji) {
        return harjoitukset.annaHarjoituskerrat(laji.getTunnusNro());
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
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
     * Testiohjelma treenipäiväkirjasta
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
                
                Laji l = treenipaivakirja.lajit.annaLaji(esim.getLajiNro());
                System.out.println(l);
                esim.tulosta(System.out);
                            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}