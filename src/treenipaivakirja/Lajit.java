package treenipaivakirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Treenipäiväkirjan lajit, joka osaa mm. lisätä uuden lajin.
 *
 * @author Jonna Määttä
 * @version 9.4.2021
 * 
 */
public class Lajit implements Iterable<Laji> {

    @SuppressWarnings("unused")
    private String tiedostonNimi      = "";
    @SuppressWarnings("unused")
    private String tiedostonPerusNimi = "";
    private boolean muutettu = false;
    
    /** Taulukko lajeista */
    private final List<Laji> alkiot        = new ArrayList<Laji>();
    
    /**
     * Lajien alustaminen.
     */
    public Lajit() {
        // 
    }

    
    /**
     * Palauttaa treenipäiväkirjan lajien lukumäärän.
     * @return lajien lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }

    
    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta.
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    

    /**
     * Lisää uuden lajin tietorakenteeseen. Ottaa lajin omistukseensa.
     * @param l lisättävä laji. Huom tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
     * Lajit lajit = new Lajit();
     * Laji juoksu1 = new Laji(), juoksu2 = new Laji();
     * lajit.getLkm() === 0;
     * lajit.lisaa(juoksu1); lajit.getLkm() === 1;
     * lajit.lisaa(juoksu2); lajit.getLkm() === 2;
     * lajit.lisaa(juoksu1); lajit.getLkm() === 3;
     * lajit.anna(0) === juoksu1;
     * lajit.anna(1) === juoksu2;
     * lajit.anna(2) === juoksu1;
     * lajit.anna(1) == juoksu1 === false;
     * lajit.anna(1) == juoksu2 === true;
     * lajit.lisaa(juoksu1); lajit.getLkm() === 4;
     * lajit.lisaa(juoksu1); lajit.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Laji l) {
        alkiot.add(l);
        muutettu = true;
    }
    
    
    /**
     * Korvaa lajin tietorakenteessa. Ottaa lajin omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva laji. Jos ei löydy,
     * niin lisätään uutena lajina.
     * @param laji lisättävän lajin viitteen
     * @throws SailoException jos tietorakenne on jo täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Lajit lajit = new Lajit();
     * Laji l1 = new Laji(), l2 = new Laji();
     * l1.rekisteroi(); l2.rekisteroi();
     * lajit.getLkm() === 0;
     * lajit.korvaaTaiLisaaLaji(l1); lajit.getLkm() === 1;
     * lajit.korvaaTaiLisaaLaji(l2); lajit.getLkm() === 2;
     * Laji l3 = l1.clone();
     * Iterator<Laji> it = lajit.iterator();
     * it.next() == l1 === true;
     * lajit.korvaaTaiLisaaLaji(l3); lajit.getLkm() === 2;
     * it = lajit.iterator();
     * Laji l0 = it.next();
     * l0 === l3;
     * l0 == l3 === true;
     * l0 == l1 === false;
     * </pre>
     */
    public void korvaaTaiLisaaLaji(Laji laji) throws SailoException {
        int id = laji.getTunnusNro();
        for (int i = 0; i < alkiot.size() ; i++) {
            if ( alkiot.get(i).getTunnusNro() == id ) {
                alkiot.set(i, laji);
                muutettu = true;
                return;
            }
        }
        lisaa(laji);
    }


    /**
     * Lukee lajit tiedostosta.  
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Lajit lajit = new Lajit();
     *  Laji juoksu1 = new Laji(), juoksu2 = new Laji();
     *  juoksu1.vastaaJuoksu();
     *  juoksu2.vastaaJuoksu();
     *  String tiedNimi = "testitreenit";
     *  File ftied = new File(tiedNimi+"/lajit.dat");
     *  ftied.delete();
     *  lajit.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  lajit.lisaa(juoksu1);
     *  lajit.lisaa(juoksu2);
     *  lajit.tallenna(tiedNimi);
     *  lajit = new Lajit();   // Poistetaan vanhat luomalla uusi
     *  lajit.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Laji> i = lajit.iterator();
     *  i.hasNext() === true;
     *  lajit.lisaa(juoksu2);
     *  lajit.tallenna(tiedNimi);
     *  ftied.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String tiedNimi = hakemisto + "/lajit.dat";
        File ftied = new File(tiedNimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Laji laji = new Laji();
                laji.parse(s); 
                lisaa(laji);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + tiedNimi);
        } 
    }


    /**
     * Tallentaa lajit tiedostoon.  
     * @param tiednimi tallennettavan tiedoston nimi
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna(String tiednimi) throws SailoException {
        if ( !muutettu ) return;
        File ftied = new File(tiednimi + "/lajit.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (int i = 0; i < getLkm(); i++) {
                Laji laji = anna(i);
                fo.println(laji);
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + "ei aukea");
        }
        
        muutettu = false;
    }

    
    /**
     * Palauttaa viitteen i:teen lajiin.
     * @param i lajin indeksi
     * @return alkio
     * @throws IndexOutOfBoundsException poikkeus
     */
    public Laji anna(int i) throws IndexOutOfBoundsException {
         if ((i < 0) || (i >= alkiot.size())) {
             throw new IndexOutOfBoundsException("Laiton indeksi");
         }
         return alkiot.get(i);
    }
    
    
    /**
     * Palauttaa lajin tunnusnron perusteella, muuten palauttaa listan ekan.
     * @param tunnusNro lajin tunnusnumero
     * @return laji
     */
    public Laji annaLajiTn(int tunnusNro) {
        for (Laji l : alkiot) {
            if (l.getTunnusNro() == tunnusNro) {
                return l;
            }
        }  
        if (alkiot.size() > 0) {
            return alkiot.get(0);
        }
        return null;
    }
    
    
    /**
     * Poistaa valitun lajin.
     * @param laji poistettava laji
     * @return tosi jos löytyi poistettava tietue 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Lajit lajit = new Lajit();
     *  Laji juoksu11 = new Laji(); juoksu11.vastaaJuoksu(); 
     *  Laji juoksu12 = new Laji(); juoksu12.vastaaJuoksu(); 
     *  Laji juoksu21 = new Laji(); juoksu21.vastaaJuoksu(); 
     *  Laji juoksu22 = new Laji(); juoksu22.vastaaJuoksu(); 
     *  Laji juoksu23 = new Laji(); juoksu23.vastaaJuoksu();
     *  lajit.lisaa(juoksu11);
     *  lajit.lisaa(juoksu12);     
     *  lajit.lisaa(juoksu21); 
     *  lajit.lisaa(juoksu22);
     *  lajit.poista(juoksu23) === false; lajit.getLkm() === 4;
     *  lajit.poista(juoksu11) === true; lajit.getLkm() === 3;
     *  lajit.anna(0) === juoksu12;
     * </pre>
     */
    public boolean poista(Laji laji) {
        boolean ret = alkiot.remove(laji);
        if (ret) muutettu = true;
        return ret;
    }
    
    
    /**
     * Testiohjelma lajeille.
     * @param args ei käytössä
     * @throws SailoException poikkeus
     */
    public static void main(String args[]) throws SailoException {
        Lajit uudetLajit = new Lajit();
        
        try {
        uudetLajit.lueTiedostosta("treenit");
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
        Laji juoksu1 = new Laji(), juoksu2 = new Laji();
        juoksu1.rekisteroi();
        juoksu1.vastaaJuoksu();
        juoksu2.rekisteroi();
        juoksu2.vastaaJuoksu();
    
        uudetLajit.lisaa(juoksu1);
        uudetLajit.lisaa(juoksu2);

            for (int i = 0; i < uudetLajit.getLkm(); i++) {
                Laji l = uudetLajit.anna(i);
                System.out.println("Laji nro: " + i);
                l.tulosta(System.out);
            }
            
        try {
            uudetLajit.tallenna("treenit");
        } catch (SailoException e) {
            e.printStackTrace();
        } 
    }
    
    
    /**
     * Luokka lajien iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Lajit lajit = new Lajit();
     * Laji laji1 = new Laji(), laji2 = new Laji();
     * laji1.rekisteroi(); laji2.rekisteroi();
     *
     * lajit.lisaa(laji1); 
     * lajit.lisaa(laji2); 
     * lajit.lisaa(laji1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Laji laji:lajit)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+laji.getTunnusNro());           
     * 
     * String tulos = " " + laji1.getTunnusNro() + " " + laji2.getTunnusNro() + " " + laji1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Laji>  i=lajit.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Laji laji = i.next();
     *   ids.append(" "+laji.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Laji>  i=lajit.iterator();
     * i.next() == laji1  === true;
     * i.next() == laji2  === true;
     * i.next() == laji1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class LajitIterator implements Iterator<Laji> {
        private int kohdalla = 0;
    

   /**
    * Onko olemassa vielä seuraavaa lajia
    * @see java.util.Iterator#hasNext()
    * @return true jos on vielä lajeja
    */
    @Override
    public boolean hasNext() {
       return kohdalla < getLkm();
     }


   /**
    * Annetaan seuraava laji
    * @return seuraava laji
    * @throws NoSuchElementException jos seuraava alkiota ei enää ole
    * @see java.util.Iterator#next()
    */
    @Override
    public Laji next() throws NoSuchElementException {
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
     * Palautetaan iteraattori lajeistaan.
     * @return laji iteraattori
     */
    @Override
    public Iterator<Laji> iterator() {
        return new LajitIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien lajien viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä lajeista
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Lajit lajit = new Lajit(); 
     *   Laji laji1 = new Laji(); laji1.parse("1|juoksu"); 
     *   Laji laji2 = new Laji(); laji2.parse("2|tennis"); 
     *   Laji laji3 = new Laji(); laji3.parse("3|kävely"); 
     *   Laji laji4 = new Laji(); laji4.parse("4|kuntosali"); 
     *   Laji laji5 = new Laji(); laji5.parse("5|tennis"); 
     *   lajit.lisaa(laji1); lajit.lisaa(laji2); lajit.lisaa(laji3); lajit.lisaa(laji4); lajit.lisaa(laji5);
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Laji> etsi(String hakuehto, int k) { 
        Collection<Laji> loytyneet = new ArrayList<Laji>(); 
        for (Laji l : this) { 
            loytyneet.add(l);  
        } 
        return loytyneet; 
    }

}