package tarkistus;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Luokka päivämäärän tarkastusta varten.
 * 
 * @author Jonna Määttä
 * @version 9.4.2021
 *
 */
public class PvmTarkistus {
    
    
    /**
     * Kuukausien pituudet kaksiulotteisessa taulukossa, tavallinen ja karkausvuosi.
     */
    public static final int KPITUUDET[][] = {
            // 1   2   3   4   5   6   7   8   9  10  11  12
            { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
            { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }
    };

    
    /**
     * Palautetaan tieto siitä onko tutkittava vuosi karkausvuosi vai ei.
     * @param vv tutkittava vuosi
     * @return 1 jos on karkausvuosi ja 0 jos ei ole
     * @example
     * <pre name="test">
     *   karkausvuosi(1900) === 0
     *   karkausvuosi(1900) === 0
     *   karkausvuosi(1901) === 0
     *   karkausvuosi(1996) === 1
     *   karkausvuosi(2000) === 1
     *   karkausvuosi(2001) === 0
     *   karkausvuosi(2004) === 1
     * </pre>
     */
    public static int karkausvuosi(int vv) {
        if ( vv % 400 == 0 ) return 1;
        if ( vv % 100 == 0 ) return 0;
        if ( vv % 4 == 0 ) return 1;
        return 0;
    }
    
    
    /**
     * Tarkistaa päivämäärän oikeellisuuden karkausvuoden näkökulmasta.
     * @param sb päivämäärä StringBuilderina
     * @return true tai false
     * @example
     * <pre name="test">
     * StringBuilder sb1 = new StringBuilder("29.2.04");
     * StringBuilder sb2 = new StringBuilder("29.2.05");
     * StringBuilder sb3 = new StringBuilder("30.10.21");
     * StringBuilder sb4 = new StringBuilder("1.12.44");
     * StringBuilder sb5 = new StringBuilder("29.2.41");
     * tarkista(sb1) === true;
     * tarkista(sb2) === false;
     * tarkista(sb3) === true;
     * tarkista(sb4) === true;
     * tarkista(sb5) === false;
     * </pre>
     */
    public static boolean tarkista(StringBuilder sb) {
        String p = Mjonot.erota(sb, '.');
        String k = Mjonot.erota(sb, '.');
        String v = Mjonot.erota(sb, '.');
        int pp = Integer.valueOf(p);
        int kk = Integer.valueOf(k);
        int vv = Integer.valueOf(v);
        int kv = karkausvuosi(vv);
        int pv_lkm = KPITUUDET[kv][kk - 1];
        if ( pp > pv_lkm ) return false;
        return true;
    }
    
}