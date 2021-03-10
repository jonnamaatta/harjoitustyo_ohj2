package treenipaivakirja;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Treenipäiväkirjan lajit, joka osaa mm. lisätä uuden lajin
 *
 * @author Jonna Määttä
 * @version 9.3.2021
 */
public class Lajit implements Iterable<Laji> {

    private String                      tiedostonNimi = "";

    /** Taulukko lajeista */
    private final List<Laji> alkiot        = new ArrayList<Laji>();
    private int              lkm              = 0;

    /**
     * Lajien alustaminen
     */
    public Lajit() {
        // toistaiseksi ei tarvitse tehdä mitään
    }


    /**
     * Lisää uuden lajin tietorakenteeseen.  Ottaa lajin omistukseensa.
     * @param har lisättävä laji.  Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Laji har) {
        alkiot.add(har);
    }


    /**
     * Lukee harjoituskerrat tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".har";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa lajit tiedostoon.  
     * TODO Kesken.
     * @param tiednimi tiedoston nimi
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna(String tiednimi) throws SailoException {
        File ftied = new File(tiednimi + "/nimet.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (int i = 0; i < getLkm(); i++) {
                Laji laji = anna(i);
                fo.println(laji);
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + "ei aukea");
        }
    }


    /**
     * Palauttaa treenipäiväkirjan lajien lukumäärän
     * @return lajien lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien lajien läpikäymiseen
     * @return laji-iteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Lajit uudet = new Lajit();
     *  Laji juoksu21 = new Laji(2); uudet.lisaa(juoksu21);
     *  Laji juoksu11 = new Laji(1); uudet.lisaa(juoksu11);
     *  Laji juoksu22 = new Laji(2); uudet.lisaa(juoksu22);
     *  Laji juoksu12 = new Laji(1); uudet.lisaa(juoksu12);
     *  Laji juoksu23 = new Laji(2); uudet.lisaa(juoksu23);
     * 
     *  Iterator<Laji> i2=uudet.iterator();
     *  i2.next() === juoksu21;
     *  i2.next() === juoksu11;
     *  i2.next() === juoksu22;
     *  i2.next() === juoksu12;
     *  i2.next() === juoksu23;
     *  i2.next() === juoksu12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Laji esim:uudet ) { 
     *    esim.getHarjoituskertaNro() === jnrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Laji> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * @param i indeksi
     * @return alkio
     * @throws IndexOutOfBoundsException poikkeus
     */
    public Laji annaLaji(int i) throws IndexOutOfBoundsException {
        if ((i < 0) || (i >= alkiot.size())) {
            throw new IndexOutOfBoundsException("Laiton indeksi");
        }
        
        return alkiot.get(i); 
    }

    
    
    /**
     * Palauttaa viitteen i:teen lajiin.
     * @param i monennenko harjoituskerran viite halutaan
     * @return viite harjoituskertaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Laji anna(int i) throws IndexOutOfBoundsException {
        return alkiot.get(i);
    }
    
    /**
     * Testiohjelma lajeille
     * @param args ei käytössä
     * @throws SailoException kiinnostaa
     */
    public static void main(String args[]) throws SailoException {
        Lajit uudet = new Lajit();
        
        Laji tennis1 = new Laji(), tennis2 = new Laji();
        tennis1.rekisteroi();
        tennis1.vastaaJuoksu();
        tennis2.rekisteroi();
        tennis2.vastaaJuoksu();
    
        uudet.lisaa(tennis1);
        uudet.lisaa(tennis2);

            for (int i = 0; i < uudet.getLkm(); i++) {
                Laji l = uudet.anna(i);
                System.out.println("Laji nro: " + i);
                l.tulosta(System.out);
            }
        
    }
}