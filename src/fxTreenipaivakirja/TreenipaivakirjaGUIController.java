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

import java.util.ArrayList;
import java.util.List; 
import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;


import fi.jyu.mit.fxgui.*;

/**
 * Luokka kerhon käyttöliittymän tapahtumien hoitamiseksi.
 * @author Jonna Määttä
 * @version 11.3.2021
 */
public class TreenipaivakirjaGUIController implements Initializable {
    
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelHarjoituskerta;
    @FXML private ListChooser<Laji> chooserLajit;

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
    private Laji lajiKohdalla;
    private TextArea areaLaji = new TextArea();
    private String tiednimi = "treenit";  

   
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
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa jäsenten tiedot.
     * Alustetaan myös jäsenlistan kuuntelija 
     */
      protected void alusta() {
          panelHarjoituskerta.setContent(areaLaji);
          areaLaji.setFont(new Font("Courier New", 12));
          panelHarjoituskerta.setFitToHeight(true);
          chooserLajit.clear();
          chooserLajit.addSelectionListener(e -> naytaLaji());
        }
      
      
      /**
       * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
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
       * Näyttää listasta valitun lajin tiedot, tilapäisesti yhteen isoon edit-kenttään
       */
      protected void naytaLaji() {
          lajiKohdalla = chooserLajit.getSelectedObject();

          if (lajiKohdalla == null) return;

          areaLaji.setText("");
          try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaLaji)) {
              tulosta(os, lajiKohdalla);  
          }
      }
     
      
      /**
       * Tulostaa tiedot
       * @param os tietovirta johon tulostetaan
       * @param l tulostettava laji
       */
      public void tulosta(PrintStream os, final Laji l) {
        
          os.println("----------------------------------------------");
          l.tulosta(os);
          os.println("----------------------------------------------");

          List <Harjoituskerta> esim = treenipaivakirja.annaHarjoituskerrat(l);
              for (Harjoituskerta har : esim)
                   har.tulosta(os);  
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
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Harjoituksen muokkaus
     */
    private void muokkaaHarjoitusta() {
        ModalController.showModal(TreenipaivakirjaGUIController.class.getResource("HarjoitusDialogView.fxml"),"Harjoituskerran muokkaus", null, "");
    }
    
    
    /**
     * Haetaan laji näyttöön
     * @param lnro lajinumero
     */
    private void hae(int lnro) {
        chooserLajit.clear();
       
        int index = 0;
       
        for(int i = 0; i < treenipaivakirja.getLajit(); i++) {
            Laji l = treenipaivakirja.annaLaji(i);
            if (l.getTunnusNro() == lnro) index = i; {
            chooserLajit.add(l.getNimi(), l);
        }
        chooserLajit.setSelectedIndex(index); 
        }
     } 

    
    private void uusiHarjoituskerta() {
        if ( lajiKohdalla == null ) return;  
        Harjoituskerta har = new Harjoituskerta();  
        har.rekisteroi();  
        har.vastaaJuoksu(lajiKohdalla.getTunnusNro());  
        try {
            treenipaivakirja.lisaa(har);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        hae(lajiKohdalla.getTunnusNro());    
    }
    
    
    /**
     * Lisätään uusi laji
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