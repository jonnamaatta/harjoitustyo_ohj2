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
 * @author Jonna Määttä
 * @version 19.3.2021
 */
public class TreenipaivakirjaGUIController implements Initializable {
    
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelHarjoituskerta;
    @FXML private ListChooser<Harjoituskerta> chooserHarjoitukset;
    @FXML private StringGrid<Laji> tableLajit;
    @FXML private TextField editPvm; 
    @FXML private TextField editKesto; 
    @FXML private TextField editMatka;
    @FXML private TextField editKuormittavuus;
    @FXML private TextField editKommentti;

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    /*
     * Käsitellään hakuehto
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
     * Käsitellään uuden harjoituskerran lisääminen
     */
    @FXML private void handleUusiHarjoituskerta() {
        uusiHarjoituskerta();
    }
    
    
    /**
     * Käsitellään harjoituskerran muokkaaminen
     */
    @FXML private void handleMuokkaaHarjoituskerta() {
        muokkaaHarjoitusta();
    }
    
    
    /**
     * Käsitellään harjoituskerran poistaminen
     */
    @FXML private void handlePoistaHarjoituskerta() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa harjoituskertaa!");
    }
    
    
    /**
     * Käsitellään uuden lajin lisääminen
     */
    @FXML private void handleUusiLaji() {
        uusiLaji();
    }
    
    
    /**
     * Käsitellään lajin muokkaaminen
     */
    @FXML private void handleMuokkaaLajia() {
        muokkaaLajia();
    }
    
    
    /**
     * Käsitellään lajin poistaminen
     */
    @FXML private void handlePoistaLaji() {
        Dialogs.showMessageDialog("Ei osata vielä!");
    }
    
    
    /**
     * Käsitellään tallentaminen
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * Käsitellään lopettaminen
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
     
    
    /**
     * Käsitellään tietojen näyttäminen
     */
    @FXML private void handleTietoja() {
        Dialogs.showMessageDialog("Treenipäiväkirja");
    }
    
    
    /**
     * Käsitellään tilastojen näyttäminen
     */
    @FXML private void handleTilastoja() {
        tilastoja();
    }
    
    
    /**
     * Käsitellään harjoituskertojen tulostaminen
     */
    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa!");
    }
    
    
    /**
     * Käsitellään "Apua"-osion näyttäminen
     */
    @FXML private void handleApua() {
        Dialogs.showMessageDialog("Ei osata vielä auttaa!");
    }
      
   
    //======================================================================================================
    // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    private Treenipaivakirja treenipaivakirja;
    private Harjoituskerta harjoitusKohdalla;
    // private TextArea areaLaji = new TextArea();
    private String tiednimi = "treenit";  
    private Treenipaivakirja treeni;
    private TextField edits[];
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa harjoitusten tiedot.
     * Alustetaan myös lajilistan kuuntelija 
     */
      protected void alusta() {
          chooserHarjoitukset.clear();
          chooserHarjoitukset.addSelectionListener(e -> naytaHarjoituskerta());
          
          edits = new TextField[]{editPvm, editKesto, editMatka, editKuormittavuus, editKommentti};
        }
      
      
      /**
       * Alustaa treenipäiväkirjan lukemalla sen valitun nimisestä tiedostosta
       * @param nimi tiedosto josta kerhon tiedot luetaan
       * @return null jos onnistuu, muuten virhe tekstinä
       */
      protected String lueTiedosto(String nimi) {
          tiednimi = nimi;
          try {
              treenipaivakirja.lueTiedostosta(nimi);
              hae(0);
              return null;
          } catch (SailoException e) {
              hae(0);
              String virhe = e.getMessage(); 
              if ( virhe != null ) Dialogs.showMessageDialog(virhe);
              return virhe;
          }
       }


      /**
       * Kysytään tiedoston nimi ja luetaan se
       * @return true jos onnistui, false jos ei
       */
      public boolean avaa() {
          String uusinimi = tiednimi;
          if (uusinimi == null) return false;
          lueTiedosto(uusinimi);
          return true;
      }
      
      
      /**
       * Tietojen tallennus
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
       * Tarkistetaan onko tallennus tehty
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
       * Hakee harjoituskerran tiedot listaan
       * @param hnro harjotuksen numero, joka aktivoidaan haun jälkeen
       */
      protected void hae(int hnro) {
          chooserHarjoitukset.clear(); 

          int index = 0; 
          Collection<Harjoituskerta> harjoitukset; 
          try { 
              harjoitukset = treenipaivakirja.etsi("", 1); 
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
      
      
      
      /**
       * Uuden harjoituskerran lisääminen
       * TODO: lisää parametrilistaan treenipaivakirja
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
       * Tulostaa harjoituksen tiedot
       * @param os tietovirta johon tulostetaan
       * @param harjoitus tulostettava jäsen
       */
      public void tulosta(PrintStream os, final Harjoituskerta harjoitus) {
          os.println("----------------------------------------------");
          harjoitus.tulosta(os);
          os.println("----------------------------------------------");
      }
     
     
      
     /**
      * Näytetään virhe
      * @param virhe
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
     * Harjoituksen muokkaus
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
     * Uuden lajin lisääminen
     */
    private void uusiLaji() {
        Laji l = new Laji();
        l.rekisteroi();
        l.vastaaJuoksu();
        try {
            treenipaivakirja.lisaa(l);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(l.getTunnusNro());
    }
    
    
    /**
     * Lajin muokkaus
     */
    private void muokkaaLajia() {
        ModalController.showModal(TreenipaivakirjaGUIController.class.getResource("LajiDialogView.fxml"),"Lajin muokkaaminen", null, "");
    }
    
    
    /**
     * Tilastojen näyttäminen
     */
    private void tilastoja() {
        ModalController.showModal(TreenipaivakirjaGUIController.class.getResource("TilastotView.fxml"),"Tilastojen näyttäminen", null, "");
    }

    
    /**
     * Asetetaan viite käytettävään kerhoon
     * @param treenipaivakirja käytettävä kerho
     */
    public void setTreenipaivakirja(Treenipaivakirja treenipaivakirja) {
        this.treenipaivakirja = treenipaivakirja;
    }
    
    
}