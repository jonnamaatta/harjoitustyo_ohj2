package fxTreenipaivakirja;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;

/**
 * Luokka kerhon käyttöliittymän tapahtumien hoitamiseksi.
 * @author jtmaatta
 * @version 3.2.2021
 */
public class TreenipaivakirjaGUIController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
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
        Dialogs.showMessageDialog("Ei osata vielä poistaa lajia!");
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
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä");
    }
    
    /**
     * Uuden harjoituskerran lisäys
     */
    private void uusiHarjoituskerta() {
        ModalController.showModal(TreenipaivakirjaGUIController.class.getResource("HarjoitusDialogView.fxml"),"Uusi harjoituskerta", null, "");
    }
    
    /**
     * Uuden lajin lisäys
     */
    private void uusiLaji() {
        ModalController.showModal(TreenipaivakirjaGUIController.class.getResource("LajiDialogView.fxml"),"Uusi laji", null, "");
    }
    
    /**
     * Uuden lajin lisäys
     */
    private void muokkaaHarjoitusta() {
        ModalController.showModal(TreenipaivakirjaGUIController.class.getResource("HarjoitusDialogView.fxml"),"Harjoituskerran muokkaus", null, "");
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

}