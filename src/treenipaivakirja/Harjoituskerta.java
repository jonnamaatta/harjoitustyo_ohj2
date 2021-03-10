package treenipaivakirja;
 
import java.io.*;
import treenipaivakirja.Laji;
import java.util.ArrayList;
import java.util.List;

/**
* Harjoituskerta joka osaa mm. itse huolehtia tunnusNro:staan.
*
* @author Jonna Määttä
* @version 6.3.2021
*/
public class Harjoituskerta {
    
    private int        tunnusNro;
    private String     pvm            = "";
    private int        lajiNro;
    private String     kesto          = "";
    private double     matka          = 0;
    private int        kuormittavuus  = 0;
    private String     kommentti      = "";

    private static int seuraavaNro    = 1;

    
    /**
     * Oletusmuodostaja
     */
    public Harjoituskerta() {
    
    }
    
    
    /**
     * Alustetaan tietyn harjoituskerran laji 
     * @param lajiNro lajin viitenumero 
     */
    public Harjoituskerta(int lajiNro) {
        this.lajiNro = lajiNro;
    }
    
    
    /**
     * @return lajiNro
     */
    public int getLajiNro() {
        return lajiNro;
    }
    
    
   /**
    * @return harjoituskerran nimi
    * @example
    * <pre name="test">
    *   Harjoituskerta tennis1 = new Harjoituskerta();
    *   tennis1.vastaaTestiarvot();
    *   tennis1.getPvm() =R= "7.12.20";
    * </pre>
    */
    public String getPvm() {
         return pvm;
    }


    /**
     * Arvotaan satunnainen kokonaisluku välille [ala, yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan ylaraja
     * @return satunnainen luku väliltä [ala, yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }

    
    /**
    * Apumetodi, jolla saadaan täytettyä testiarvot harjoituskerralle.
     * @param nro indeksi
    */
    public void vastaaJuoksu(int nro) {
        pvm = "7.12.20 " + rand(1000,9999);
        lajiNro = nro;
        kesto = "44:32";
        matka = 7.0;
        kuormittavuus = 6;
        kommentti = "Jaksoin juosta todella hyvin";
    }

    
    /**
    * Tulostetaan harjoituskerran
    * @param out tietovirta johon tulostetaan
    */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + "  " + pvm);
        out.println(kesto + " "
                 + matka + " "
                 + kuormittavuus + " ");
        out.println(kommentti);
        }
    
    
    /**
    * Tulostetaan harjoituskerran tiedot
    * @param os tietovirta johon tulostetaan
    */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
    * Antaa harjoituskerralle seuraavan id:n.
    * @return harjoituskerran uusi tunnusNro
    * @example
    * <pre name="test">
    *   Harjoituskerta juoksu1 = new Harjoituskerta();
    *   juoksu1.getTunnusNro() === 0;
    *   juoksu1.rekisteroi();
    *   Harjoituskerta juoksu2 = new Harjoituskerta();
    *   juoksu2.rekisteroi();
    *   int n1 = juoksu1.getTunnusNro();
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
    * Palauttaa harjoituskerran tunnusnumeron.
    * @return harjoituskerran tunnusnumero
    */
    public int getTunnusNro() {
         return tunnusNro;
    }
    
       
    /**
     * Testiohjelma harjoituskerralle.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
        juoksu1.rekisteroi();
        juoksu2.rekisteroi();
        juoksu1.tulosta(System.out);
        juoksu1.vastaaJuoksu(0);
        juoksu1.tulosta(System.out);
        
        juoksu2.vastaaJuoksu(0);
        juoksu2.tulosta(System.out);
        
        juoksu2.vastaaJuoksu(0);
        juoksu2.tulosta(System.out);
        }
    }
