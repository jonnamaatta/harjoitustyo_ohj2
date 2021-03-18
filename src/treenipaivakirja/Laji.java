package treenipaivakirja;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

import static treenipaivakirja.Harjoituskerta.rand;

/**
 * Laji joka osaa mm. itse huolehtia tunnus_nro:staan.
 * @author Jonna Määttä
 * @version 18.3.2021
 */
public class Laji {
    private int tunnusNro;
    private String lajinNimi;
   
    private static int seuraavaNro = 1;

    
    /**
    * Asettaa tunnusnumeron ja samalla varmistaa että
    * seuraava numero on aina suurempi kuin tähän mennessä suurin.
    * @param nr asetettava tunnusnumero
    */
   private void setTunnusNro(int nr) {
       tunnusNro = nr;
       if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
   }

   
    /**
     * Alustetaan laji. Toistaiseksi ei tarvitse tehdä mitään.
     */
    public Laji() {
        // Vielä ei tarvita mitään
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot lajille.
     */
    public void vastaaJuoksu() {
        lajinNimi = "juoksu " + rand(1000,9999);
    }
    
    
    /**
     * Tulostetaan lajin tiedot.
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(lajinNimi);
    }


    /**
     * Tulostetaan harjoituksen tiedot.
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
     * Laji l = new Laji();
     * l.rekisteroi();
     * l.vastaaJuoksu();
     * l.toString() === "1|juoksu";
     * </pre>
     */
    @Override
    public String toString() {
        return "" +
        this.getTunnusNro() + "|" + lajinNimi;   
    }
    
    
    /**
     * Selvitää lajin tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta lajin tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Laji laji = new Laji();
     *   laji.parse("2  |  juoksu");
     *   laji.getTunnusNro() === 2;
     *   laji.toString().startsWith("2|juoksu") === true; 
     *   laji.rekisteroi();
     *   int n = laji.getTunnusNro();
     *   laji.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   laji.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   laji.getTunnusNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        lajinNimi = Mjonot.erota(sb, '|', lajinNimi);
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
     * @param s lajille laitettava nimi
     * @return virheilmoitus, null jos ok
     */
    public String setNimi(String s) {
        lajinNimi = s;
        return null;
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