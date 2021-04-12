package treenipaivakirja.test;
// Generated by ComTest BEGIN
import treenipaivakirja.*;
import java.io.File;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.12 09:49:25 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class LajitTest {



  // Generated by ComTest BEGIN
  /** testLisaa77 */
  @Test
  public void testLisaa77() {    // Lajit: 77
    Lajit lajit = new Lajit(); 
    Laji juoksu1 = new Laji(), juoksu2 = new Laji(); 
    assertEquals("From: Lajit line: 80", 0, lajit.getLkm()); 
    lajit.lisaa(juoksu1); assertEquals("From: Lajit line: 81", 1, lajit.getLkm()); 
    lajit.lisaa(juoksu2); assertEquals("From: Lajit line: 82", 2, lajit.getLkm()); 
    lajit.lisaa(juoksu1); assertEquals("From: Lajit line: 83", 3, lajit.getLkm()); 
    assertEquals("From: Lajit line: 84", juoksu1, lajit.anna(0)); 
    assertEquals("From: Lajit line: 85", juoksu2, lajit.anna(1)); 
    assertEquals("From: Lajit line: 86", juoksu1, lajit.anna(2)); 
    assertEquals("From: Lajit line: 87", false, lajit.anna(1) == juoksu1); 
    assertEquals("From: Lajit line: 88", true, lajit.anna(1) == juoksu2); 
    lajit.lisaa(juoksu1); assertEquals("From: Lajit line: 89", 4, lajit.getLkm()); 
    lajit.lisaa(juoksu1); assertEquals("From: Lajit line: 90", 5, lajit.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaaLaji105 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaaLaji105() throws SailoException,CloneNotSupportedException {    // Lajit: 105
    Lajit lajit = new Lajit(); 
    Laji l1 = new Laji(), l2 = new Laji(); 
    l1.rekisteroi(); l2.rekisteroi(); 
    assertEquals("From: Lajit line: 111", 0, lajit.getLkm()); 
    lajit.korvaaTaiLisaaLaji(l1); assertEquals("From: Lajit line: 112", 1, lajit.getLkm()); 
    lajit.korvaaTaiLisaaLaji(l2); assertEquals("From: Lajit line: 113", 2, lajit.getLkm()); 
    Laji l3 = l1.clone(); 
    Iterator<Laji> it = lajit.iterator(); 
    assertEquals("From: Lajit line: 116", true, it.next() == l1); 
    lajit.korvaaTaiLisaaLaji(l3); assertEquals("From: Lajit line: 117", 2, lajit.getLkm()); 
    it = lajit.iterator(); 
    Laji l0 = it.next(); 
    assertEquals("From: Lajit line: 120", l3, l0); 
    assertEquals("From: Lajit line: 121", true, l0 == l3); 
    assertEquals("From: Lajit line: 122", false, l0 == l1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta143 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta143() throws SailoException {    // Lajit: 143
    Lajit lajit = new Lajit(); 
    Laji laji1 = new Laji(), laji2 = new Laji(); 
    laji1.rekisteroi(); laji2.rekisteroi(); 
    laji1.vastaaJuoksu(); 
    laji2.vastaaJuoksu(); 
    String tiedosto = "testilajit"; 
    String tiedNimi = tiedosto+"/lajit"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(tiedosto); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    lajit.lueTiedostosta(tiedosto); 
    fail("Lajit: 158 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    lajit.lisaa(laji1); 
    lajit.lisaa(laji2); 
    lajit.tallenna(); 
    lajit = new Lajit();  // Poistetaan vanhat luomalla uusi
    lajit.lueTiedostosta(tiedosto);  // johon ladataan tiedot tiedostosta.
    Iterator<Laji> i = lajit.iterator(); 
    i.next().equals(laji1); 
    i.next().equals(laji2); 
    assertEquals("From: Lajit line: 167", false, i.hasNext()); 
    lajit.lisaa(laji2); 
    lajit.tallenna(); 
    assertEquals("From: Lajit line: 170", true, ftied.delete()); 
    File fbak = new File(tiedosto+"/lajit.bak"); 
    assertEquals("From: Lajit line: 172", true, fbak.delete()); 
    assertEquals("From: Lajit line: 173", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista262 
   * @throws SailoException when error
   */
  @Test
  public void testPoista262() throws SailoException {    // Lajit: 262
    Lajit lajit = new Lajit(); 
    Laji juoksu11 = new Laji(); juoksu11.vastaaJuoksu(); 
    Laji juoksu12 = new Laji(); juoksu12.vastaaJuoksu(); 
    Laji juoksu21 = new Laji(); juoksu21.vastaaJuoksu(); 
    Laji juoksu22 = new Laji(); juoksu22.vastaaJuoksu(); 
    Laji juoksu23 = new Laji(); juoksu23.vastaaJuoksu(); 
    lajit.lisaa(juoksu11); 
    lajit.lisaa(juoksu12); 
    lajit.lisaa(juoksu21); 
    lajit.lisaa(juoksu22); 
    assertEquals("From: Lajit line: 275", false, lajit.poista(juoksu23)); assertEquals("From: Lajit line: 275", 4, lajit.getLkm()); 
    assertEquals("From: Lajit line: 276", true, lajit.poista(juoksu11)); assertEquals("From: Lajit line: 276", 3, lajit.getLkm()); 
    assertEquals("From: Lajit line: 277", juoksu12, lajit.anna(0)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLajitIterator327 
   * @throws SailoException when error
   */
  @Test
  public void testLajitIterator327() throws SailoException {    // Lajit: 327
    Lajit lajit = new Lajit(); 
    Laji laji1 = new Laji(), laji2 = new Laji(); 
    laji1.rekisteroi(); laji2.rekisteroi(); 
    lajit.lisaa(laji1); 
    lajit.lisaa(laji2); 
    lajit.lisaa(laji1); 
    StringBuffer ids = new StringBuffer(30); 
    for (Laji laji:lajit) // Kokeillaan for-silmukan toimintaa
    ids.append(" "+laji.getTunnusNro()); 
    String tulos = " " + laji1.getTunnusNro() + " " + laji2.getTunnusNro() + " " + laji1.getTunnusNro(); 
    assertEquals("From: Lajit line: 346", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Laji>  i=lajit.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
    Laji laji = i.next(); 
    ids.append(" "+laji.getTunnusNro()); 
    }
    assertEquals("From: Lajit line: 354", tulos, ids.toString()); 
    Iterator<Laji>  i=lajit.iterator(); 
    assertEquals("From: Lajit line: 357", true, i.next() == laji1); 
    assertEquals("From: Lajit line: 358", true, i.next() == laji2); 
    assertEquals("From: Lajit line: 359", true, i.next() == laji1); 
    try {
    i.next(); 
    fail("Lajit: 361 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi421 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi421() throws SailoException {    // Lajit: 421
    Lajit lajit = new Lajit(); 
    Laji laji1 = new Laji(); laji1.parse("1|juoksu"); 
    Laji laji2 = new Laji(); laji2.parse("2|tennis"); 
    Laji laji3 = new Laji(); laji3.parse("3|kävely"); 
    Laji laji4 = new Laji(); laji4.parse("4|kuntosali"); 
    Laji laji5 = new Laji(); laji5.parse("5|tennis"); 
    lajit.lisaa(laji1); lajit.lisaa(laji2); lajit.lisaa(laji3); lajit.lisaa(laji4); lajit.lisaa(laji5); 
  } // Generated by ComTest END
}