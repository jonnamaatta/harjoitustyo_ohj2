package fxTreenipaivakirja;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import treenipaivakirja.Harjoituskerta;
import treenipaivakirja.Laji;
import treenipaivakirja.SailoException;
import treenipaivakirja.Treenipaivakirja;

import java.util.Collection;
import java.util.List; 
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;


import fi.jyu.mit.fxgui.*;

/**
 * Luokka treenipäiväkirjan käyttöliittymän tapahtumien hoitamiseksi.
 * 
 * @author Jonna Määttä
 * @version 24.3.2021
 * 
 */
public class TreenipaivakirjaGUIController implements Initializable {
    
    @FXML private TextField                   hakuehto;
    @FXML private TextField                   editPvm; 
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
    
    
    /**
     * Käsitellään hakuehto.
     */
    @FXML private void handleHakuehto() {
        String hakukentta = cbKentat.getSelectedText();
        String ehto = hakuehto.getText(); 
        if ( ehto.isEmpty() )
            naytaVirhe(null);
        else
            naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
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
        Dialogs.showMessageDialog("Ei osata vielä poistaa harjoituskertaa!");
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
     */
    @FXML private void handlePoistaLaji() {
        Dialogs.showMessageDialog("Ei osata vielä!");
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
        Dialogs.showMessageDialog("Treenipäiväkirja");
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
        Dialogs.showMessageDialog("Ei osata vielä tulostaa!");
    }
    
    
    /**
     * Käsitellään "Apua"-osion näyttäminen.
     */
    @FXML private void handleApua() {
        Dialogs.showMessageDialog("Ei osata vielä auttaa!");
    }
      
   
    //======================================================================================================
    // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    private Treenipaivakirja treenipaivakirja;
    private Harjoituskerta   harjoitusKohdalla;
    private Laji             lajiKohdalla;
    private String           tiednimi = "treenit";  
    private TextField        edits[];
    
    
    /**
     * Tekee muut tarvittavat alustukset.
     */
      protected void alusta()  {
          chooserHarjoitukset.clear();
          chooserHarjoitukset.addSelectionListener(e -> naytaHarjoituskerta());
         
          edits = new TextField[]{editPvm, editKesto, editMatka, editKuormittavuus, editKommentti};     
      }
      
      
      /**
       * Alustaa treenipäiväkirjan lukemalla sen valitun nimisestä tiedostosta.
       * @param nimi tiedosto josta treenipäiväkirjan tiedot luetaan
       * @return null jos onnistuu, muuten virhe tekstinä
       */
      protected String lueTiedosto(String nimi) {
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
       * Kysytään tiedoston nimi ja luetaan se.
       * @return true jos onnistui, false jos ei
       */
      public boolean avaa() {
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
          if (harjoitusKohdalla == null) return;
          HarjoitusDialogController.naytaHarjoitus(edits, harjoitusKohdalla);
          }
      
 
      /**
       * Hakee harjoituskerran tiedot listaan.
       * @param hnro harjotuksen numero, joka aktivoidaan haun jälkeen
       */
      protected void hae(int hnro) {
          chooserHarjoitukset.clear(); 

          int index = 0; 
          Collection<Harjoituskerta> harjoitukset; 
          try { 
              harjoitukset = treenipaivakirja.etsiHarjoitus("", 1); 
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
      
      
     /** Hakee lajin tiedot listaan
      * @param lnro lajin numero
      */
      protected void haeLaji(int lnro) {
          tableLajit.clear();
         
          int index = 0; 
          Collection<Laji> lajit; 
          try { 
              lajit = treenipaivakirja.etsiLaji("", 1); 
              int i = 0; 
              for (Laji laji:lajit) { 
                  if (laji.getTunnusNro() == lnro) index = i; 
                  tableLajit.add(laji.getNimi()); 
                  i++; 
              } 
          } catch (SailoException ex) { 
              Dialogs.showMessageDialog("Lajin hakemisessa ongelmia! " + ex.getMessage()); 
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
       * Tulostaa harjoituskerran tiedot.
       * @param os tietovirta johon tulostetaan
       * @param harjoitus tulostettava harjoituskerta
       */
      public void tulosta(PrintStream os, final Harjoituskerta harjoitus) {
          os.println("----------------------------------------------");
          harjoitus.tulosta(os);
          os.println("----------------------------------------------");
      }
     
      
     /**
      * Näytetään virhe.
      * @param virhe virhe, joka näytetään
      */
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
     * Uuden lajin lisääminen.
     * TODO: Muokkaa sellaiseksi, että avaa dialogin
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
            //HarjoitusDialogController.vieLaji(uusi.getTunnusNro());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Lisääminen epäonnistui: " + e.getMessage());
        }
        
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
     * Tilastojen näyttäminen.
     */
    private void tilastoja() {
        ModalController.showModal(TreenipaivakirjaGUIController.class.getResource("TilastotView.fxml"),"Tilastojen näyttäminen", null, "");
    }

    
    /**
     * Asetetaan viite käytettävään treenipäiväkirjaan.
     * @param treenipaivakirja käytettävä treenipäiväkirja.
     */
    public void setTreenipaivakirja(Treenipaivakirja treenipaivakirja) {
        this.treenipaivakirja = treenipaivakirja;
    }
    
    
}