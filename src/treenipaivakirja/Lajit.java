package treenipaivakirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * Treenipäiväkirjan lajit, joka osaa mm. lisätä uuden lajin
 *
 * @author Jonna Määttä
 * @version 11.3.2021
 */
public class Lajit implements Iterable<Laji> {

    private String                      tiedostonNimi = "";
    private String tiedostonPerusNimi = "";
    
    /** Taulukko lajeista */
    private final List<Laji> alkiot        = new ArrayList<Laji>();

    
    /**
     * Lajien alustaminen
     */
    public Lajit() {
        // toistaiseksi ei tarvitse tehdä mitään
    }

    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
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
        File ftied = new File(tiednimi + "/lajit.dat");
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
     * @param i harjotuksen indeksi
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
     * Testiohjelma lajeille
     * @param args ei käytössä
     * @throws SailoException kiinnostaa
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
     * Luokka jäsenten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Jasenet jasenet = new Jasenet();
     * Jasen aku1 = new Jasen(), aku2 = new Jasen();
     * aku1.rekisteroi(); aku2.rekisteroi();
     *
     * jasenet.lisaa(aku1); 
     * jasenet.lisaa(aku2); 
     * jasenet.lisaa(aku1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Jasen jasen:jasenet)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+jasen.getTunnusNro());           
     * 
     * String tulos = " " + aku1.getTunnusNro() + " " + aku2.getTunnusNro() + " " + aku1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Jasen>  i=jasenet.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Jasen jasen = i.next();
     *   ids.append(" "+jasen.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Jasen>  i=jasenet.iterator();
     * i.next() == aku1  === true;
     * i.next() == aku2  === true;
     * i.next() == aku1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class LajitIterator implements Iterator<Laji> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
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
     * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    @Override
    public Iterator<Laji> iterator() {
        return new LajitIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Jasenet jasenet = new Jasenet(); 
     *   Jasen jasen1 = new Jasen(); jasen1.parse("1|Ankka Aku|030201-115H|Paratiisitie 13|"); 
     *   Jasen jasen2 = new Jasen(); jasen2.parse("2|Ankka Tupu||030552-123B|"); 
     *   Jasen jasen3 = new Jasen(); jasen3.parse("3|Susi Sepe|121237-121V||131313|Perämetsä"); 
     *   Jasen jasen4 = new Jasen(); jasen4.parse("4|Ankka Iines|030245-115V|Ankkakuja 9"); 
     *   Jasen jasen5 = new Jasen(); jasen5.parse("5|Ankka Roope|091007-408U|Ankkakuja 12"); 
     *   jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); jasenet.lisaa(jasen4); jasenet.lisaa(jasen5);
     *   // TODO: toistaiseksi palauttaa kaikki jäsenet 
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