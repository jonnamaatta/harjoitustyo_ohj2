package treenipaivakirja;
 
import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
* Harjoituskerta joka osaa mm. itse huolehtia tunnusNro:staan.
*
* @author Jonna Määttä
* @version 18.3.2021
*/
public class Harjoituskerta {
    
    private int        tunnusNro;
    private String     pvm            = "";
    private int        lajiNro;
    private String     kesto          = "";
    private String     matka          = "";
    private String     kuormittavuus  = "";
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
    * Asettaa tunnusnumeron ja samalla varmistaa että
    * seuraava numero on aina suurempi kuin tähän mennessä suurin.
    * @param nr asetettava tunnusnumero
    */
   private void setTunnusNro(int nr) {
       tunnusNro = nr;
       if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
   }
   
   
   /**
    * @param s laitettava pvm
    * @return virheilmotus, null jos ok
    */
   public String setPvm(String s) {
       this.pvm = s;
       return null;
   }

   
   /**
   * @param s laitettava kesto
   * @return virheilmoitus, null jos ok
   * */
   public String setKesto(String s) {
       this.kesto = s;
       return null;
   }
   
   
  /**
   * @param s laitettava matka
   * @return virheilmoitus, null jos ok
   * */
   public String setMatka(String s) {
       this.matka = s;
       return null;
   }
    
  /**
   * @param s laitettava kuormittavuus
   * @return virheilmoitus, null jos ok
   * */
   public String setKuormittavuus(String s) {
       this.kuormittavuus = s;
       return null;
   }
     
     
  /**
   * @param s laitettava kommentti
   * @return virheilmoitus, null jos ok
   * */
   public String setKommentti(String s) {
       this.kommentti = s;
       return null;
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
    *   Harjoituskerta juoksu1 = new Harjoituskerta();
    *   juoksu1.vastaaTestiarvot();
    *   juoksu1.getPvm() =R= "7.12.20";
    * </pre>
    */
    public String getPvm() {
         return pvm;
    }
    
    
    /**
     * Palauttaa harjoituskerran tunnusnumeron.
     * @return harjoituskerran tunnusnumero
     */
     public int getTunnusNro() {
          return tunnusNro;
     }
     
     
    /**
     * @return kesto
     */
     public String getKesto() {
        return kesto;
     }
      
      
    /**
     * @return matka
     */
     public String getMatka() {
         return matka;
     }
       
       
    /**
     * @return kuormittavuus
     */
     public String getKuormittavuus() {
         return kuormittavuus;
     }
        
    /**
     * @return kommentti
     */
     public String getKommentti() {
         return kommentti;
     }
    
    
    /**
     * @example
     * <pre name="test">
     * Harjoituskerta har = new Harjoituskerta();
     * har.rekisteroi();
     * har.vastaaJuoksu(1);
     * har.toString() === "1|7.12.20|1|44:32|7.0|6|Jaksoin juosta todella hyvin";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + pvm + "|" + lajiNro + "|" + kesto + "|" + matka +
                "|" + kuormittavuus + "|" + kommentti;
    }
    
   
    /**
     * Selvittää harjoituskerran tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta harjoituskerran tiedot otetaan
     * @example
     * <pre name="test">
     *   Harjoituskerta har = new Harjoituskerta();
     *   har.parse("   1  |  7.12.20  |   1  | 44:32 | 7.0 | 6 |Jaksoin juosta todella hyvin  ");
     *   har.getLajiNro() === 1;
     *   har.toString()    === "1|7.12.20|1|44:32|7.0|6|Jaksoin juosta todella hyvin";
     *   
     *   har.rekisteroi();
     *   int n = har.getTunnusNro();
     *   har.parse(""+(n+20));
     *   har.rekisteroi();
     *   har.getTunnusNro() === n+20+1;
     *   har.toString()     === "" + (n+20+1) + "1|7.12.20|1|44:32|7.0|6|Jaksoin juosta todella hyvin";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        pvm = Mjonot.erota(sb, '|', pvm);
        lajiNro = Mjonot.erota(sb, '|', lajiNro);
        kesto = Mjonot.erota(sb, '|', kesto);
        matka = Mjonot.erota(sb, '|', matka);
        kuormittavuus = Mjonot.erota(sb, '|', kuormittavuus);
        kommentti = Mjonot.erota(sb, '|', kommentti);    
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
        matka = "7.0";
        kuormittavuus = "6";
        kommentti = "Jaksoin juosta todella hyvin";
    }

    
    /**
    * Tulostetaan harjoituskerta
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
