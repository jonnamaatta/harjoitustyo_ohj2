package treenipaivakirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * Treenipäiväkirjan lajit, joka osaa mm. lisätä uuden lajin.
 *
 * @author Jonna Määttä
 * @version 20.3.2021
 * 
 */
public class Lajit implements Iterable<Laji> {

    private String tiedostonNimi      = "";
    private String tiedostonPerusNimi = "";
    private boolean muutettu = false;
    
    /** Taulukko lajeista */
    private final List<Laji> alkiot        = new ArrayList<Laji>();

    
    /**
     * Lajien alustaminen.
     */
    public Lajit() {
        // toistaiseksi ei tarvitse tehdä mitään
    }

    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta.
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    

    /**
     * Lisää uuden lajin tietorakenteeseen. Ottaa lajin omistukseensa.
     * @param l lisättävä laji. Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Laji l) {
        alkiot.add(l);
        muutettu = true;
    }


    /**
     * Lukee lajit tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String tiedNimi = hakemisto + "/lajit.dat";
        File ftied = new File(tiedNimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Laji laji = new Laji();
                laji.parse(s); // voisi palauttaa jotakin?
                lisaa(laji);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + tiedNimi);
       // } catch (IOException e) {
        //    throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
  
    }


    /**
     * Tallentaa lajit tiedostoon.  
     * TODO Kesken.
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
     * Palauttaa treenipäiväkirjan lajien lukumäärän.
     * @return lajien lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }

    
    /**
     * @param i lajin indeksi
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
     * @param i monennenko lajin viite halutaan
     * @return viite lajiin, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Laji anna(int i) throws IndexOutOfBoundsException {
        return alkiot.get(i);
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
     *   // TODO: toistaiseksi palauttaa kaikki harjoituskerrat.
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