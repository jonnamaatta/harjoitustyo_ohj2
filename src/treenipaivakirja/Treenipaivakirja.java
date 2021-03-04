package treenipaivakirja;

import java.util.List;

/**
 * Treenipäiväkirja-luokka, joka huolehtii harjoituskerroista.  Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" harjoituskertoihin..
 *
 * @author Jonna Määttä
 * @version 22.2.2021
 */
public class Treenipaivakirja {
    
    private final Harjoituskerrat harjoitukset = new Harjoituskerrat();
    private final Lajit lajit = new Lajit(); 


    /**
     * Palauttaa harjoitusten määrän
     * @return harjoitusten määrä
     */
    public int getLajit() {
        return lajit.getLkm();
    }
    
    
    /**
     * Palauttaa harjoitusten määrän
     * @return harjoitusten määrä
     */
    public int getHarjoitukset() {
        return harjoitukset.getLkm();
    }



    /**
     * Poistaa harjoituskerroista ja lajeista ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako harjoitusta poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }


    /**
     * Lisää treenipäiväkirjaan uuden harjoituksen
     * @param harjoitus lisättävä harjoituskerta
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Treenipaivakirja treeni = new Treenipaivakirja();
     * Harjoituskerta tennis = new Harjoituskerta(), tennis2 = new Harjoituskerta();
     * tennis1.rekisteroi(); tennis2.rekisteroi();
     * treenipaivakirja.getHarjoitukset() === 0;
     * treenipaivakirja.lisaa(tennis1); treenipaivakirja.getHarjoitukset() === 1;
     * treenipaivakirja.lisaa(tennis2); treenipaivakirja.getHarjoitukset() === 2;
     * treenipaivakirja.lisaa(tennis1); treenipaivakirja.getHarjoitukset() === 3;
     * treenipaivakirja.getHarjoitukset() === 3;
     * treenipaivakirja.annaHarjoituskerta(0) === tennis1;
     * treenipaivakirja.annaHarjoituskerta(1) === tennis2;
     * treenipaivakirja.annaHarjoituskerta(2) === tennis1;
     * treenipaivakirja.annaHarjoituskerta(3) === tennis1; #THROWS IndexOutOfBoundsException 
     * treenipaivakirja.lisaa(tennis1); treenipaivakirja.getHarjoitukset() === 4;
     * treenipaivakirja.lisaa(tennis1); treenipaivakirja.getHarjoitukset() === 5;
     * treenipaivakirja.lisaa(tennis1);            #THROWS SailoException
     * </pre>
     */
    public void lisaa(Harjoituskerta harjoitus) throws SailoException {
        harjoitukset.lisaa(harjoitus);
    }

    /**
     * Listään uusi laji 
     * @param laj lisättävä laji
     * @throws SailoException poikkeus
     */
    public void lisaa(Laji laj) throws SailoException {
        lajit.lisaa(laj);
    }

    /**
     * Palauttaa i:n harjoituskerran
     * @param i monesko harjoituskerta palautetaan
     * @return viite i:teen harjoitukseen
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Harjoituskerta annaHarjoituskerta(int i) throws IndexOutOfBoundsException {
        return harjoitukset.anna(i);
    }


    
    /**
     * @param i indeksi
     * @return laji
     */
    public Laji annaLaji(int i) {
        return lajit.annaLaji(i);
    }
    
    
    /**
     * Lukee treenipäiväkirjan tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        harjoitukset.lueTiedostosta(nimi);
    }


    /**
     * Tallettaa treenipäiväkirjan tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        harjoitukset.talleta();
        // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
    }


    /**
     * Testiohjelma treenipäiväkirjasta
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Treenipaivakirja treenipaivakirja = new Treenipaivakirja();

        try {
            Harjoituskerta tiistai1 = new Harjoituskerta(), tiistai2 = new Harjoituskerta();
            tiistai1.rekisteroi();
            tiistai1.taytaTestiarvot();
            tiistai2.rekisteroi();
            tiistai2.taytaTestiarvot();

            treenipaivakirja.lisaa(tiistai1);
            treenipaivakirja.lisaa(tiistai2);
            
            int id1 = tiistai1.getLajiNro();
            int id2 = tiistai2.getLajiNro();
            
            Laji juoksu11 = new Laji(); juoksu11.rekisteroi(); juoksu11.vastaaJuoksu(id1); treenipaivakirja.lisaa(juoksu11);
            Laji juoksu12 = new Laji(); juoksu12.rekisteroi(); juoksu12.vastaaJuoksu(id1); treenipaivakirja.lisaa(juoksu12);
            Laji juoksu21 = new Laji(); juoksu21.rekisteroi(); juoksu21.vastaaJuoksu(id2); treenipaivakirja.lisaa(juoksu21);
            Laji juoksu22 = new Laji(); juoksu22.rekisteroi(); juoksu22.vastaaJuoksu(id2); treenipaivakirja.lisaa(juoksu22);
            Laji juoksu23 = new Laji(); juoksu23.rekisteroi(); juoksu23.vastaaJuoksu(id2); treenipaivakirja.lisaa(juoksu23);

            System.out.println("============= Treenipäiväkirjan testi =================");

            for (int i = 0; i < treenipaivakirja.getHarjoitukset(); i++) {
               
                Harjoituskerta esim = treenipaivakirja.annaHarjoituskerta(i);
                System.out.println("Harjoituskerta paikassa: " + i);
                
                Laji l = treenipaivakirja.lajit.annaLaji(esim.getLajiNro());
                System.out.println(l);
                esim.tulosta(System.out);
                            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}