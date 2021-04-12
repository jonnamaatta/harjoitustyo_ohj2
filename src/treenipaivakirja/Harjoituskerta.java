package treenipaivakirja;
 
import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;
import static tarkistus.PvmTarkistus.*;

/**
* Harjoituskerta joka osaa mm. itse huolehtia tunnusNro:staan.
*
* @author Jonna Määttä
* @version 9.4.2021
* 
*/
public class Harjoituskerta implements Cloneable, Comparable<Harjoituskerta> {
    
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
     * Palauttaa harjoituskerran kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 7;
    }


    /**
     * Eka kenttä joka on mielekäs kysyttäväksi.
     * @return ekan kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }


    
    /**
     * Alustetaan tietyn harjoituskerran laji.
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
    * Asettaa lajinumeron
    * @param i asetettavanumero
    * @return virheilmoitus, null jos ok
    */
   public String setLajiNro(int i) {
       this.lajiNro = i;
       return null;
   }
   
   
   /**
    * Asettaa harjoituskerralle päivämäärän.
    * @param s laitettava pvm
    * @return virheilmotus, null jos ok 
    */
   public String setPvm(String s) {
       if(!s.matches("[0-9]{1,2}(\\.)([0-9]|1[012])(\\.)[0-9]{2}")) return "Päivämäärän on oltava muotoa d.m.yy";
       StringBuilder sb = new StringBuilder(s);
       if (tarkista(sb) == false) return "Päivämäärä ei ole mahdollinen";
       this.pvm = s; 
       return null;
   }
   
  /**
   * Asettaa harjoituskerralle keston.
   * @param s laitettava kesto
   * @return virheilmoitus, null jos ok
   * */
   public String setKesto(String s) {
       if (s == "") return null;
       if (!s.matches("[0-9]*:[0-5][0-9]")) return "Keston oltava muodossa 00:00";
       this.kesto = s;
       return null;
   }
   
   
  /**
   * Asettaa harjoituskerralle matkan.
   * @param s laitettava matka
   * @return virheilmoitus, null jos ok
   * */
   public String setMatka(String s) {
       if (s == "") return null;
       if (!s.matches("^\\d*\\.\\d+$")) return "Matkan oltava desimaaliluku";
       this.matka = s;
       return null;
   }
    
   
  /**
   * Asettaa harjoituskerralle kuormittavuuden.
   * @param s laitettava kuormittavuus
   * @return virheilmoitus, null jos ok
   * */
   public String setKuormittavuus(String s) {
       if (s == "") return null;
       if (!s.matches("[1-9]|10*")) return "Kuormittavuuden on oltava luku väliltä 1-10";
       this.kuormittavuus = s;
       return null;
   }
     
     
  /**
   * Asettaa harjoituskerralle kommentin.
   * @param s laitettava kommentti
   * @return virheilmoitus, null jos ok
   * */
   public String setKommentti(String s) {
       this.kommentti = s;
       return null;
   }

  
    /**
     * Palauttaa harjoituskerran lajinumeron.
     * @return lajiNro
     */
    public int getLajiNro() {
        return lajiNro;
    }
    
    
   /**
    * Palauttaa harjoituskerran päivämäärän.
    * @return harjoituskerran nimi
    * @example
    * <pre name="test">
    *   Harjoituskerta juoksu1 = new Harjoituskerta();
    *   juoksu1.vastaaJuoksu();
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
     * Palauttaa harjoituskerran keston.
     * @return kesto
     * @example
     * <pre name="test">
     *   Harjoituskerta juoksu1 = new Harjoituskerta();
     *   juoksu1.vastaaJuoksu();
     *   juoksu1.getKesto() =R= "44:32";
     * </pre>
     */
     public String getKesto() {
        return kesto;
     }
      
      
    /**
     * Palauttaa harjoituskerran matkan.
     * @return matka
     * @example
     * <pre name="test">
     *   Harjoituskerta juoksu1 = new Harjoituskerta();
     *   juoksu1.vastaaJuoksu();
     *   juoksu1.getMatka() =R= "7.0";
     * </pre>
     */
     public String getMatka() {
         return matka;
     }
       
       
    /**
     * Palauttaa harjoituskerran kuormittavuuden.
     * @return kuormittavuus
     * @example
     * <pre name="test">
     *   Harjoituskerta juoksu1 = new Harjoituskerta();
     *   juoksu1.vastaaJuoksu();
     *   juoksu1.getKuormittavuus() =R= "6";
     * </pre>
     */
     public String getKuormittavuus() {
         return kuormittavuus;
     }
        
     
    /**
     * Palauttaa harjoituskerran kommentin.
     * @return kommentti
     * @example
     * <pre name="test">
     *   Harjoituskerta juoksu1 = new Harjoituskerta();
     *   juoksu1.vastaaJuoksu();
     *   juoksu1.getKommentti() =R= "Jaksoin juosta todella hyvin";
     * </pre>
     */
     public String getKommentti() {
         return kommentti;
     }
    
    
    /**
     * @example
     * <pre name="test">
     * Harjoituskerta har = new Harjoituskerta();
     * har.rekisteroi();
     * har.vastaaJuoksu();
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
     *   har.toString()     === "" + (n+20+1) + "|7.12.20|1|44:32|7.0|6|Jaksoin juosta todella hyvin";
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
    * Apumetodi, jolla saadaan täytettyä testiarvot harjoituskerralle.
    */
    public void vastaaJuoksu() {
        pvm = "7.12.20";
        lajiNro = 1;
        kesto = "44:32";
        matka = "7.0";
        kuormittavuus = "6";
        kommentti = "Jaksoin juosta todella hyvin";
    }

    
    /**
    * Tulostetaan harjoituskerta.
    * @param out tietovirta johon tulostetaan
    */
    public void tulostaOtsikko(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + " " + pvm);
        }
    
    
    /**
     * Tulostetaan harjoituskerta.
     * @param out tietovirta johon tulostetaan
     */
     public void tulostaSisalto(PrintStream out) {
         out.println(kesto + " "
                  + matka + " "
                  + kuormittavuus + " ");
         out.println(kommentti);
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
     * Tehdään identtinen klooni harjoituskerrasta
     * @return Object kloonattu harjoituskerta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Harjoituskerta har = new Harjoituskerta();
     *   har.parse("   1  |  7.12.20  |   1  | 44:32 | 7.0 | 6 |Jaksoin juosta todella hyvin  ");
     *   Harjoituskerta kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse(" 2  |  9.12.20  |   2 | 45:21 | 5.0 | 7 | Ihan OK meni ");
     *   kopio.toString().equals(har.toString()) === false;
     * </pre>
     */
    @Override
    public Harjoituskerta clone() throws CloneNotSupportedException {
        Harjoituskerta uusi;
        uusi = (Harjoituskerta) super.clone();
        return uusi;
    }
    

    /**
     * Antaa k:n kentän sisällön merkkijonona.
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + pvm;
        case 2: return "" + String.valueOf(lajiNro);
        case 3: return "" + kesto;
        case 4: return "" + matka;
        case 5: return "" + kuormittavuus;
        case 6: return "" + kommentti;
        default: return "...";
        }
    }


    @Override
    public int compareTo(Harjoituskerta harjoituskerta) {
        return String.valueOf(tunnusNro).compareTo(String.valueOf(harjoituskerta.tunnusNro));
    }

       
    /**
     * Testiohjelma harjoituskerralle.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta();
        juoksu1.rekisteroi();
        juoksu2.rekisteroi();
        juoksu1.tulostaOtsikko(System.out);
        juoksu1.tulostaSisalto(System.out);

        juoksu1.vastaaJuoksu();
        juoksu1.tulostaOtsikko(System.out);
        juoksu1.tulostaSisalto(System.out);
        
        juoksu2.vastaaJuoksu();
        juoksu2.tulostaOtsikko(System.out);
        juoksu2.tulostaSisalto(System.out);
        
        juoksu2.vastaaJuoksu();
        juoksu2.tulostaOtsikko(System.out);
        juoksu2.tulostaSisalto(System.out);
    }

}