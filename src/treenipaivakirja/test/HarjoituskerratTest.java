package treenipaivakirja.test;
// Generated by ComTest BEGIN
import java.io.File;
import treenipaivakirja.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.12 09:48:37 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class HarjoituskerratTest {



  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta95 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta95() throws SailoException {    // Harjoituskerrat: 95
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta har1 = new Harjoituskerta(), har2 = new Harjoituskerta(); 
    har1.rekisteroi(); har2.rekisteroi(); 
    har1.vastaaJuoksu(); 
    har2.vastaaJuoksu(); 
    String tiedosto = "testiharjoitukset"; 
    String tiedNimi = tiedosto+"/harjoitukset"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(tiedosto); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    harjoitukset.lueTiedostosta(tiedosto); 
    fail("Harjoituskerrat: 110 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    assertEquals("From: Harjoituskerrat line: 111", 0, harjoitukset.getLkm()); 
    harjoitukset.lisaa(har1); 
    harjoitukset.lisaa(har2); 
    harjoitukset.tallenna(); 
    harjoitukset = new Harjoituskerrat();  // Poistetaan vanhat luomalla uusi
    harjoitukset.lueTiedostosta(tiedosto); 
    assertEquals("From: Harjoituskerrat line: 117", 2, harjoitukset.getLkm());  // johon ladataan tiedot tiedostosta.
    Iterator<Harjoituskerta> i = harjoitukset.iterator(); 
    i.next().equals(har1); 
    i.next().equals(har2); 
    assertEquals("From: Harjoituskerrat line: 121", false, i.hasNext()); 
    harjoitukset.lisaa(har2); 
    harjoitukset.tallenna(); 
    assertEquals("From: Harjoituskerrat line: 124", true, ftied.delete()); 
    File fbak = new File(tiedosto+"/harjoitukset.bak"); 
    assertEquals("From: Harjoituskerrat line: 126", true, fbak.delete()); 
    assertEquals("From: Harjoituskerrat line: 127", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa185 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa185() throws SailoException {    // Harjoituskerrat: 185
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta(); 
    assertEquals("From: Harjoituskerrat line: 190", 0, harjoitukset.getLkm()); 
    harjoitukset.lisaa(juoksu1); assertEquals("From: Harjoituskerrat line: 191", 1, harjoitukset.getLkm()); 
    harjoitukset.lisaa(juoksu2); assertEquals("From: Harjoituskerrat line: 192", 2, harjoitukset.getLkm()); 
    harjoitukset.lisaa(juoksu1); assertEquals("From: Harjoituskerrat line: 193", 3, harjoitukset.getLkm()); 
    assertEquals("From: Harjoituskerrat line: 194", juoksu1, harjoitukset.anna(0)); 
    assertEquals("From: Harjoituskerrat line: 195", juoksu2, harjoitukset.anna(1)); 
    assertEquals("From: Harjoituskerrat line: 196", juoksu1, harjoitukset.anna(2)); 
    assertEquals("From: Harjoituskerrat line: 197", false, harjoitukset.anna(1) == juoksu1); 
    assertEquals("From: Harjoituskerrat line: 198", true, harjoitukset.anna(1) == juoksu2); 
    try {
    assertEquals("From: Harjoituskerrat line: 199", juoksu1, harjoitukset.anna(3)); 
    fail("Harjoituskerrat: 199 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    harjoitukset.lisaa(juoksu1); assertEquals("From: Harjoituskerrat line: 200", 4, harjoitukset.getLkm()); 
    harjoitukset.lisaa(juoksu1); assertEquals("From: Harjoituskerrat line: 201", 5, harjoitukset.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testHaeSuosituinLaji229 
   * @throws SailoException when error
   */
  @Test
  public void testHaeSuosituinLaji229() throws SailoException {    // Harjoituskerrat: 229
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta(); 
    juoksu1.rekisteroi(); 
    juoksu1.vastaaJuoksu(); 
    juoksu2.rekisteroi(); 
    juoksu2.vastaaJuoksu(); 
    juoksu2.setLajiNro(2); 
    harjoitukset.lisaa(juoksu1); 
    harjoitukset.lisaa(juoksu2); 
    assertEquals("From: Harjoituskerrat line: 240", 1, harjoitukset.haeSuosituinLaji()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLaskeKeskiKuormittavuus274 
   * @throws SailoException when error
   */
  @Test
  public void testLaskeKeskiKuormittavuus274() throws SailoException {    // Harjoituskerrat: 274
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta(); 
    juoksu1.rekisteroi(); 
    juoksu1.vastaaJuoksu(); 
    juoksu2.rekisteroi(); 
    juoksu2.vastaaJuoksu(); 
    harjoitukset.lisaa(juoksu1); 
    harjoitukset.lisaa(juoksu2); 
    assertEquals(harjoitukset.laskeKeskiKuormittavuus(), 6, 0.01); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLaskeKeskimatka303 
   * @throws SailoException when error
   */
  @Test
  public void testLaskeKeskimatka303() throws SailoException {    // Harjoituskerrat: 303
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta(); 
    juoksu1.rekisteroi(); 
    juoksu1.vastaaJuoksu(); 
    juoksu2.rekisteroi(); 
    juoksu2.vastaaJuoksu(); 
    harjoitukset.lisaa(juoksu1); 
    harjoitukset.lisaa(juoksu2); 
    assertEquals(harjoitukset.laskeKeskimatka(), 7.0, 0.01); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testHaePisinmatka335 
   * @throws SailoException when error
   */
  @Test
  public void testHaePisinmatka335() throws SailoException {    // Harjoituskerrat: 335
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta(); 
    juoksu1.rekisteroi(); 
    juoksu1.vastaaJuoksu(); 
    juoksu2.rekisteroi(); 
    juoksu2.vastaaJuoksu(); 
    juoksu2.setMatka("8.8"); 
    harjoitukset.lisaa(juoksu1); 
    harjoitukset.lisaa(juoksu2); 
    assertEquals(harjoitukset.haePisinmatka(), 8.8, 0.01); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa369 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa369() throws SailoException,CloneNotSupportedException {    // Harjoituskerrat: 369
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta harjoitus1 = new Harjoituskerta(), harjoitus2 = new Harjoituskerta(); 
    harjoitus1.rekisteroi(); harjoitus2.rekisteroi(); 
    assertEquals("From: Harjoituskerrat line: 375", 0, harjoitukset.getLkm()); 
    harjoitukset.korvaaTaiLisaa(harjoitus1); assertEquals("From: Harjoituskerrat line: 376", 1, harjoitukset.getLkm()); 
    harjoitukset.korvaaTaiLisaa(harjoitus2); assertEquals("From: Harjoituskerrat line: 377", 2, harjoitukset.getLkm()); 
    Harjoituskerta harjoitus3 = harjoitus1.clone(); 
    harjoitus3.setKuormittavuus("3"); 
    Iterator<Harjoituskerta> it = harjoitukset.iterator(); 
    assertEquals("From: Harjoituskerrat line: 381", true, it.next() == harjoitus1); 
    harjoitukset.korvaaTaiLisaa(harjoitus3); assertEquals("From: Harjoituskerrat line: 382", 2, harjoitukset.getLkm()); 
    it = harjoitukset.iterator(); 
    Harjoituskerta j0 = it.next(); 
    assertEquals("From: Harjoituskerrat line: 385", harjoitus3, j0); 
    assertEquals("From: Harjoituskerrat line: 386", true, j0 == harjoitus3); 
    assertEquals("From: Harjoituskerrat line: 387", false, j0 == harjoitus1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista408 
   * @throws SailoException when error
   */
  @Test
  public void testPoista408() throws SailoException {    // Harjoituskerrat: 408
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta tiistai1 = new Harjoituskerta(), tiistai2 = new Harjoituskerta(), tiistai3 = new Harjoituskerta(); 
    tiistai1.rekisteroi(); tiistai2.rekisteroi(); tiistai3.rekisteroi(); 
    int id1 = tiistai1.getTunnusNro(); 
    harjoitukset.lisaa(tiistai1); harjoitukset.lisaa(tiistai2); harjoitukset.lisaa(tiistai3); 
    assertEquals("From: Harjoituskerrat line: 415", 1, harjoitukset.poista(id1+1)); 
    assertEquals("From: Harjoituskerrat line: 416", -1, harjoitukset.etsiId(id1+1)); assertEquals("From: Harjoituskerrat line: 416", 2, harjoitukset.getLkm()); 
    assertEquals("From: Harjoituskerrat line: 417", 1, harjoitukset.poista(id1)); assertEquals("From: Harjoituskerrat line: 417", 1, harjoitukset.getLkm()); 
    assertEquals("From: Harjoituskerrat line: 418", 0, harjoitukset.poista(id1+3)); assertEquals("From: Harjoituskerrat line: 418", 1, harjoitukset.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiId438 
   * @throws SailoException when error
   */
  @Test
  public void testEtsiId438() throws SailoException {    // Harjoituskerrat: 438
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta har1 = new Harjoituskerta(), har2 = new Harjoituskerta(), har3 = new Harjoituskerta(); 
    har1.rekisteroi(); har2.rekisteroi(); har3.rekisteroi(); 
    int id1 = har1.getTunnusNro(); 
    harjoitukset.lisaa(har1); harjoitukset.lisaa(har2); harjoitukset.lisaa(har3); 
    assertEquals("From: Harjoituskerrat line: 445", 1, harjoitukset.etsiId(id1+1)); 
    assertEquals("From: Harjoituskerrat line: 446", 2, harjoitukset.etsiId(id1+2)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi462 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi462() throws SailoException {    // Harjoituskerrat: 462
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta har1 = new Harjoituskerta(); har1.parse("1|7.12.20|1|44:32|7.0|6|Jaksoin juosta todella hyvin"); 
    Harjoituskerta har2 = new Harjoituskerta(); har2.parse("2|9.12.20|1|27:32|4.0|2|Nyt ei kulkenut oikein"); 
    Harjoituskerta har3 = new Harjoituskerta(); har3.parse("3|11.12.20|1|44:27|7.0|2|Nyt ei kulkenut oikein"); 
    Harjoituskerta har4 = new Harjoituskerta(); har4.parse("4|15.12.20|1|44:32|7.0|6|Jaksoin juosta todella hyvin"); 
    Harjoituskerta har5 = new Harjoituskerta(); har5.parse("5|20.12.20|1|27:32|4.0|2|Nyt ei kulkenut oikein"); 
    harjoitukset.lisaa(har1); harjoitukset.lisaa(har2); harjoitukset.lisaa(har3); harjoitukset.lisaa(har4); harjoitukset.lisaa(har5); 
    List<Harjoituskerta> loytyneet; 
    loytyneet = (List<Harjoituskerta>)harjoitukset.etsi("*5*",1); 
    assertEquals("From: Harjoituskerrat line: 473", 1, loytyneet.size()); 
    assertEquals("From: Harjoituskerrat line: 474", true, loytyneet.get(0) == har4); 
    loytyneet = (List<Harjoituskerta>)harjoitukset.etsi(null,-1); 
    assertEquals("From: Harjoituskerrat line: 477", 5, loytyneet.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testHarjoituskerratIterator543 
   * @throws SailoException when error
   */
  @Test
  public void testHarjoituskerratIterator543() throws SailoException {    // Harjoituskerrat: 543
    Harjoituskerrat harjoitukset = new Harjoituskerrat(); 
    Harjoituskerta juoksu1 = new Harjoituskerta(), juoksu2 = new Harjoituskerta(); 
    juoksu1.rekisteroi(); juoksu2.rekisteroi(); 
    harjoitukset.lisaa(juoksu1); 
    harjoitukset.lisaa(juoksu2); 
    harjoitukset.lisaa(juoksu1); 
    StringBuffer ids = new StringBuffer(30); 
    for (Harjoituskerta har:harjoitukset) // Kokeillaan for-silmukan toimintaa
    ids.append(" "+har.getTunnusNro()); 
    String tulos = " " + juoksu1.getTunnusNro() + " " + juoksu2.getTunnusNro() + " " + juoksu1.getTunnusNro(); 
    assertEquals("From: Harjoituskerrat line: 562", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Harjoituskerta>  i=harjoitukset.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
    Harjoituskerta har = i.next(); 
    ids.append(" "+har.getTunnusNro()); 
    }
    assertEquals("From: Harjoituskerrat line: 570", tulos, ids.toString()); 
    Iterator<Harjoituskerta>  i=harjoitukset.iterator(); 
    assertEquals("From: Harjoituskerrat line: 573", true, i.next() == juoksu1); 
    assertEquals("From: Harjoituskerrat line: 574", true, i.next() == juoksu2); 
    assertEquals("From: Harjoituskerrat line: 575", true, i.next() == juoksu1); 
    try {
    i.next(); 
    fail("Harjoituskerrat: 577 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END
}