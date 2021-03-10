package treenipaivakirja;

import java.io.*;
import static treenipaivakirja.Harjoituskerta.rand;

/**
 * Laji joka osaa mm. itse huolehtia tunnus_nro:staan.
 *
 * @author Jonna Määttä
 * @version 1.3.2021
 */
public class Laji {
    private int tunnusNro;
    private String lajinNimi;
    private int ero;
   
    private static int seuraavaNro = 1;


    /**
     * Alustetaan laji.  Toistaiseksi ei tarvitse tehdä mitään
     */
    public Laji() {
        // Vielä ei tarvita mitään
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot lajille.
     */
    public void vastaaJuoksu() {
        lajinNimi = "juoksu";
        ero = rand(1000,9999);
    }
    
    
    /**
     * Tulostetaan lajin tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(lajinNimi + " " + ero);
    }


    /**
     * Tulostetaan harjoituksen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa lajille seuraavan rekisterinumeron.
     * @return lajin uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Laji juoksu = new Laji();
     *   juoksu.getTunnusNro() === 0;
     *   juoksu.rekisteroi();
     *   Laji juoksu2 = new Laji();
     *   juoksu2.rekisteroi();
     *   int n1 = juoksu.getTunnusNro();
     *   int n2 = juoksu2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    
    /**
     * @example
     * <pre name="test">
     * Laji har = new Laji();
     * har.rekisteroi();
     * har.vastaaJuoksu();
     * har.toString() === "1|juoksu";
     * </pre>
     */
    @Override
    public String toString() {
        return "" +
        getTunnusNro() + "|" + lajinNimi;
            
    }

    
    /**
     * Palautetaan lajin oma id
     * @return lajin id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Palautetaan lajin nimi
     * @return lajin id
     */
    public String getNimi() {
        return lajinNimi;
    }


    /**
     * Testiohjelma Lajille.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Laji har = new Laji();
        har.rekisteroi();
        har.vastaaJuoksu();
        har.tulosta(System.out);
    }

}