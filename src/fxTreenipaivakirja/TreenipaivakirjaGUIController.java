package fxTreenipaivakirja;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import treenipaivakirja.Harjoituskerta;
import treenipaivakirja.Laji;
import treenipaivakirja.SailoException;
import treenipaivakirja.Treenipaivakirja;

import java.util.Collection;
import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;


import fi.jyu.mit.fxgui.*;

/**
 * Luokka treenipäiväkirjan käyttöliittymän tapahtumien hoitamiseksi.
 * 
 * @author Jonna Määttä
 * @version 9.4.2021
 */
public class TreenipaivakirjaGUIController implements Initializable {
    
    @FXML private TextField                   hakuehto;
    @FXML private TextField                   editPvm; 
    @FXML private TextField                   editLaji;
    @FXML private TextField                   editKesto; 
    @FXML private TextField                   editMatka;
    @FXML private TextField                   editKuormittavuus;
    @FXML private TextField                   editKommentti;
    @FXML private ComboBoxChooser<String>     cbKentat;
    @FXML private ScrollPane                  panelHarjoituskerta;
    @FXML private ListChooser<Harjoituskerta> chooserHarjoitukset;
    @FXML private StringGrid<Laji>            tableLajit;
    @FXML private Label                       labelVirhe;

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    @FXML private void handleAvaa() throws IOException {
        avaa();
    }
    
    
    /**
     * Käsitellään hakuehto.
     */
    @FXML private void handleHakuehto() {
        hae(0); 
    }

   
    /**
     * Käsitellään uuden harjoituskerran lisääminen.
     */
    @FXML private void handleUusiHarjoituskerta() {
        uusiHarjoituskerta();
    }
    
    
    /**
     * Käsitellään harjoituskerran muokkaaminen.
     */
    @FXML private void handleMuokkaaHarjoituskerta() {
        muokkaaHarjoitusta();
    }
    
    
    /**
     * Käsitellään harjoituskerran poistaminen.
     */
    @FXML private void handlePoistaHarjoituskerta() {
        poistaHarjoituskerta();
    }
    
    
    /**
     * Käsitellään uuden lajin lisääminen.
     */
    @FXML private void handleUusiLaji() {
        uusiLaji();
    }
    
    
    /**
     * Käsitellään lajin muokkaaminen.
     */
    @FXML private void handleMuokkaaLajia() {
        muokkaaLajia();
    }
    
    
    /**
     * Käsitellään lajin poistaminen.
     * @throws SailoException poikkeus
     */
    @FXML private void handlePoistaLaji() throws SailoException {
        poistaLaji();
    }
    
    
    /**
     * Käsitellään tallentaminen.
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * Käsitellään lopettaminen.
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
     
    
    /**
     * Käsitellään tietojen näyttäminen.
     */
    @FXML private void handleTietoja() {
        ModalController.showModal(TreenipaivakirjaGUIController.class.getResource("TietojaView.fxml"), "Treenipäiväkirja", null, "");
    }
    
    
    /**
     * Käsitellään tilastojen näyttäminen.
     */
    @FXML private void handleTilastoja() {
        tilastoja();
    }
    
    
    /**
     * Käsitellään harjoituskertojen tulostaminen.
     */
    @FXML private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null); 
        tulostaValitut(tulostusCtrl.getTextArea()); 
    }
    
    
    /**
     * Käsitellään "Apua"-osion näyttäminen.
     */
    @FXML private void handleApua() {
        avustus();
    }
      
   
    //======================================================================================================
    // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    private Treenipaivakirja treenipaivakirja;
    private Harjoituskerta   harjoitusKohdalla;
    private Harjoituskerta   apuharjoitus = new Harjoituskerta();
    private String           tiednimi = "treenit";  
    private TextField        edits[];
    
    
    /**
     * Tekee muut tarvittavat alustukset.
     */
      protected void alusta()  {
          chooserHarjoitukset.clear();
          chooserHarjoitukset.addSelectionListener(e -> naytaHarjoituskerta());
         
          edits = new TextField[]{editPvm, editLaji, editKesto, editMatka, editKuormittavuus, editKommentti}; 
          nollaaKentat();
      }
      
      
    /**
     * Nollataan harjoituskerran tiedot kentissä
     */
    protected void nollaaKentat() {
          for (TextField edit : edits) {
              edit.setText("");
          }
      }
      
      
      /**
       * Alustaa treenipäiväkirjan lukemalla sen valitun nimisestä tiedostosta.
       * @param nimi tiedosto josta treenipäiväkirjan tiedot luetaan
       * @return null jos onnistuu, muuten virhe tekstinä
       * @throws IOException jos epäonnistuu
       */
      protected String lueTiedosto(String nimi) throws IOException {
          tiednimi = nimi;
          try {
              treenipaivakirja.lueTiedostosta(nimi);
              hae(0);
              haeLaji(0);
              return null;
          } catch (SailoException e) {
              hae(0);
              haeLaji(0);
              String virhe = e.getMessage(); 
              if ( virhe != null ) Dialogs.showMessageDialog(virhe);
              return virhe;
          }
      }


      /**
       * Näyttää ohjelman alkuvalikon
       * @return true jos onnistui, false jos ei
      * @throws IOException jos epäonnistuu
       */
      public boolean avaa() throws IOException {
          ModalController.showModal(TreenipaivakirjaGUIController.class.getResource("AlkuvalikkoView.fxml"), "Treenipäiväkirja", null, "");
          String uusinimi = tiednimi;
          if (uusinimi == null) return false;
          lueTiedosto(uusinimi);
          return true;
      }
      
      
      /**
       * Tietojen tallennus.
       * @return null jos onnistuu, muuten virhe tekstinä
       */
      private String tallenna() {
          try {
              treenipaivakirja.tallenna();
              Dialogs.showMessageDialog("Tiedot tallennettu!");
              return null;
          } catch (SailoException ex) {
              Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
              return ex.getMessage();
          }
      }
        
      
      /**
       * Tarkistetaan onko tallennus tehty.
       * @return true jos saa sulkea sovelluksen, false jos ei
       */
      public boolean voikoSulkea() {
          tallenna();
          return true;
      }
      
      
      /**
       * Näyttää listasta valitun harjoituskeran tiedot tekstikenttiin. 
       */
      protected void naytaHarjoituskerta() {
          harjoitusKohdalla = chooserHarjoitukset.getSelectedObject();
          Laji l = treenipaivakirja.annaLajiTn(harjoitusKohdalla.getLajiNro());
          if (harjoitusKohdalla == null) {
              nollaaKentat();
              return;
          }
          HarjoitusDialogController.naytaHarjoitus(edits, harjoitusKohdalla, l);
      }
      
      
      /**
       * Näyttää lajit taulukkoon
       */
      private void naytaLajit() {
          tableLajit.clear();
          Collection<Laji> lajit = treenipaivakirja.etsiLaji("", 0);
          if ( lajit.size() == 0 ) return;
          haeLaji(0);
      }
      

      /**
       * Hakee harjoituskerran tiedot listaan.
       * @param hnro harjotuksen numero, joka aktivoidaan haun jälkeen
       */
      protected void hae(int hnro) {
          nollaaKentat();
          
          int harnro = hnro; // harnro harjoituksen numero, joka aktivoidaan haun jälkeen 
          if ( harnro <= 0 ) { 
              Harjoituskerta kohdalla = harjoitusKohdalla; 
              if ( kohdalla != null ) harnro = kohdalla.getTunnusNro(); 
          }
          
          int k = cbKentat.getSelectionModel().getSelectedIndex() + apuharjoitus.ekaKentta(); 
          String ehto = hakuehto.getText(); 
          if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
          
          chooserHarjoitukset.clear(); 

          int index = 0; 
          Collection<Harjoituskerta> harjoitukset; 
          try { 
              harjoitukset = treenipaivakirja.etsiHarjoitus(ehto, k); 
              int i = 0; 
              for (Harjoituskerta harjoitus:harjoitukset) { 
                  if (harjoitus.getTunnusNro() == hnro) index = i; 
                  chooserHarjoitukset.add(harjoitus.getPvm(), harjoitus); 
                  i++; 
              } 
          } catch (SailoException ex) { 
              Dialogs.showMessageDialog("Harjoituksen hakemisessa ongelmia! " + ex.getMessage()); 
          }        
          chooserHarjoitukset.getSelectionModel().select(index); // tästä tulee muutosviesti joka näyttää harjoituksen 
      }
      
      
     /** Hakee lajin tiedot listaan.
      * @param lnro lajin numero
      */
      protected void haeLaji(int lnro) {
          tableLajit.clear();
         
          int index = 0; 
          Collection<Laji> lajit; 

              lajit = treenipaivakirja.etsiLaji("", 1); 
              int i = 0; 
              for (Laji laji:lajit) { 
                  if (laji.getTunnusNro() == lnro) index = i; 
                  tableLajit.add(laji, laji.getNimi()); 
                  i++; 
              }
                
          tableLajit.getSelectionModel().select(index); // tästä tulee muutosviesti joka näyttää lajin
      }
      
      
      /**
       * Uuden harjoituskerran lisääminen.
       */
      private void uusiHarjoituskerta() {
          try {
              Harjoituskerta uusi = new Harjoituskerta();
              uusi = HarjoitusDialogController.kysyHarjoitus(null, uusi, treenipaivakirja); 
              if ( uusi == null ) return; 
              uusi.rekisteroi(); 
              treenipaivakirja.lisaa(uusi);
              hae(uusi.getTunnusNro()); 
          } catch (SailoException e) {
              Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
              return;
          }
      }

      
      /**
       * Harjoituskerran poistaminen.
       */
      private void poistaHarjoituskerta() {
          Harjoituskerta har = harjoitusKohdalla;
          if ( har == null ) return;
          if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko harjoituskerta: " + har.getPvm() +"?", "Kyllä", "Ei") )
              return;
          treenipaivakirja.poista(har);
          int index = chooserHarjoitukset.getSelectedIndex();
          hae(0);
          chooserHarjoitukset.setSelectedIndex(index);
      }
      
      
      /**
       * Harjoituskerran muokkaus.
       */
      private void muokkaaHarjoitusta() {
          if ( harjoitusKohdalla == null ) return; 
          try { 
             Harjoituskerta uusi;
             uusi = HarjoitusDialogController.kysyHarjoitus(null, harjoitusKohdalla.clone(), treenipaivakirja); 
             if ( uusi == null ) return; 
             treenipaivakirja.korvaaTaiLisaa(uusi); 
             hae(uusi.getTunnusNro()); 
         } catch (CloneNotSupportedException e) { 
           //
         } catch (SailoException e) { 
             Dialogs.showMessageDialog(e.getMessage()); 
         } 
     }
            
      
     /**
      * Näytetään virhe.
      * @param virhe virhe, joka näytetään
      */
    @SuppressWarnings("unused")
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }  
  
    
    /**
     * Uuden lajin lisääminen.
     */
    private void uusiLaji() {
        try {
            Laji uusi = new Laji();
            uusi = LajiDialogController.kysyLaji(null, uusi, 0);
            if ( uusi == null ) return;
            uusi.rekisteroi();
            treenipaivakirja.lisaa(uusi);
            haeLaji(uusi.getTunnusNro()); 
            tableLajit.selectRow(1000);  // järjestetään viimeinen rivi valituksi
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Lisääminen epäonnistui: " + e.getMessage());
        }
    }
    
    
    /**
     * Lajin poistaminen. Poistamista ei tehdä jos lajilla on harjoituskertoja.
     * @throws SailoException poikkeus
     */
    private void poistaLaji() throws SailoException {
        int rivi = tableLajit.getRowNr();
        if ( rivi < 0 ) return;
        Laji laji = tableLajit.getObject();
        if ( laji == null ) return;
        int lajinro = laji.getTunnusNro();
        Collection<Harjoituskerta> harjoitukset = treenipaivakirja.etsiHarjoitus("", 0); 
        for (Harjoituskerta har : harjoitukset) {
            if (har.getLajiNro() == lajinro) {
                Dialogs.showMessageDialog("Ei voida poistaa lajia, jolla on harjoituskertoja!");
                return;
            }
        }
        treenipaivakirja.poistaLaji(laji);
        naytaLajit();
        int harrastuksia = tableLajit.getItems().size(); 
        if ( rivi >= harrastuksia ) rivi = harrastuksia -1;
        tableLajit.getFocusModel().focus(rivi);
        tableLajit.getSelectionModel().select(rivi);
    }
    
    
    /**
     * Lajin muokkaus.
     */
    private void muokkaaLajia() {
        int r = tableLajit.getRowNr();
        if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        Laji laji = tableLajit.getObject();
        if ( laji == null ) return;
        int k = tableLajit.getColumnNr()+laji.ekaKentta();
        try {
            laji = LajiDialogController.kysyLaji(null, laji.clone(), k);
            if ( laji == null ) return;
            treenipaivakirja.korvaaTaiLisaaLaji(laji); 
            tableLajit.selectRow(r);  // järjestetään sama rivi takaisin valituksi
        } catch (CloneNotSupportedException  e) { /* clone on tehty */  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }
    
    
    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2021k/ht/jtmaatta");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    
    
    /**
     * Tulostaa harjoituskerran tiedot
     * @param os tietovirta johon tulostetaan
     * @param har tulostettava harjoituskerta
     * @param l tulostettava laji
     */
    public void tulosta(PrintStream os, final Harjoituskerta har, final Laji l) {
        os.println("----------------------------------------------");
        har.tulostaOtsikko(os);
        l.tulosta(os);
        har.tulostaSisalto(os);
    }
    
    
    /**
     * Tulostaa listassa olevat harjoituskerrat tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki harjoituskerrat");
            for (Harjoituskerta har: chooserHarjoitukset.getObjects()) { 
                Laji l = treenipaivakirja.annaLajiTn(har.getLajiNro());
                tulosta(os, har, l);
                os.println("\n");
            }
        }
    }
        
    
    /**
     * Tilastojen näyttäminen.
     */
    private void tilastoja() {
        TilastotController.laske(null, treenipaivakirja);
    }

    
    /**
     * Asetetaan viite käytettävään treenipäiväkirjaan.
     * @param treenipaivakirja käytettävä treenipäiväkirja.
     */
    public void setTreenipaivakirja(Treenipaivakirja treenipaivakirja) {
        this.treenipaivakirja = treenipaivakirja;
    }
    
    
}