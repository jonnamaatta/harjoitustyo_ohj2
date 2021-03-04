package treenipaivakirja;

import java.util.*;

/**
 * Treenipäiväkirjan lajit, joka osaa mm. lisätä uuden lajin
 *
 * @author Jonna Määttä
 * @version 1.3.2021
 */
public class Lajit implements Iterable<Laji> {

    private String                      tiedostonNimi = "";

    /** Taulukko lajeista */
    private final List<Laji> alkiot        = new ArrayList<Laji>();


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
     * Tallentaa harjoituskerrat tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
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
     * Testiohjelma lajeille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Lajit minunlajit = new Lajit();
        Laji juoksu1 = new Laji();
        juoksu1.rekisteroi();
        juoksu1.vastaaJuoksu(2);
        Laji juoksu2 = new Laji();
        juoksu2.rekisteroi();
        juoksu2.vastaaJuoksu(1);
        Laji juoksu3 = new Laji();
        juoksu3.rekisteroi();
        juoksu3.vastaaJuoksu(2);
        Laji juoksu4 = new Laji();
        juoksu4.rekisteroi();
        juoksu4.vastaaJuoksu(2);

        minunlajit.lisaa(juoksu1);
        minunlajit.lisaa(juoksu2);
        minunlajit.lisaa(juoksu3);
        minunlajit.lisaa(juoksu2);
        minunlajit.lisaa(juoksu4);

        System.out.println("============= Lajit testi =================");


       
    }

}