package fxTreenipaivakirja;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import treenipaivakirja.Harjoituskerta;
import treenipaivakirja.Laji;
import treenipaivakirja.SailoException;
import treenipaivakirja.Treenipaivakirja;
import treenipaivakirja.Lajit;

/**
 * Kysytään harjoituskerran tiedot luomalla sille uusi dialogi
 * 
 * @author Jonna Määttä
 * @version 30.3.2021
 *
 */
public class HarjoitusDialogController implements ModalControllerInterface<Harjoituskerta>, Initializable  {

    @FXML private TextField editPvm;
    @FXML private TextField editKesto;
    @FXML private TextField editMatka;
    @FXML private TextField editKuormittavuus;    
    @FXML private TextField editKommentti;    
    @FXML private Label     labelVirhe;
    @FXML private ComboBoxChooser<Laji> lajiChooser;
    @FXML private ScrollPane panelHarjoitus;
    @FXML private GridPane gridHarjoituskerta;
    @FXML private TextField editLaji;

    

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    
    @FXML private void handleOK() {
        if ( harjoitusKohdalla != null && harjoitusKohdalla.getPvm().trim().equals("") ) {
            naytaVirhe("Päivämäärä ei saa olla tyhjä.");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    
    @FXML private void handleCancel() {
        harjoitusKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

    
// ========================================================    
    private Harjoituskerta harjoitusKohdalla;
    private TextField edits[];
    private Laji laji = new Laji();
    private Treenipaivakirja treenipaivakirja;
    private static Harjoituskerta apuHarjoitus = new Harjoituskerta(); // Harjoituskerta jolta voidaan kysellä tietoja.
    private int kentta = 0;

    
    /**
     * Tyhjentää tekstikentät.
     * @param edits taulukko, jossa tyhjennettäviä tekstikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }


    /**
     * Tekee tarvittavat muut alustukset, esim. laittaa edit-kentistä tulevan
     * tapahtuman menemään kasitteleMuutosHarjoitukseen-metodiin ja vie sille
     * kentän numeron parametrina.
     */
    protected void alusta() {
        edits = new TextField[]{editPvm, editLaji, editKesto, editMatka, editKuormittavuus, editKommentti};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosHarjoitukseen(k, (TextField)(e.getSource())));
        }
       
    }
    
    
    @Override
    public void setDefault(Harjoituskerta oletus) {
       harjoitusKohdalla = oletus;
       naytaHarjoitus(edits, harjoitusKohdalla, laji);
    }

    
    @Override
    public Harjoituskerta getResult() {
        return harjoitusKohdalla;
    }
    
    
    /**
     * Mitä tehdään kun dialogi on näytetty.
     */
    @Override
    public void handleShown() {
        editPvm.requestFocus();
    }
    
    
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
     * Käsitellään harjoitukseen tullut muutos.
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosHarjoitukseen(int k, TextField edit) {
        if (harjoitusKohdalla == null) return;
        String s = edit.getText();
        Laji l = lajiChooser.getSelectedObject();
        String virhe = null;
        switch (k) {
           case 1 : virhe = harjoitusKohdalla.setPvm(s); break;
           case 2 : virhe = harjoitusKohdalla.setLajiNro(l.getTunnusNro()); break;
           case 3 : virhe = harjoitusKohdalla.setKesto(s); break;
           case 4 : virhe = harjoitusKohdalla.setMatka(s); break;
           case 5 : virhe = harjoitusKohdalla.setKuormittavuus(s); break;
           case 6 : virhe = harjoitusKohdalla.setKommentti(s); break;
           default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }

    
    /**
     * Näytetään harjoituskerran tiedot TextField komponentteihin.
     * @param edits taulukko jossa tekstikenttiä
     * @param har näytettävä harjoitus
     */
    public static void naytaHarjoitus(TextField[] edits, Harjoituskerta har, Laji l) {
        if (har == null) return;
        edits[0].setText(har.getPvm());
        edits[1].setText(l.getNimi());
        edits[2].setText(har.getKesto());
        edits[3].setText(har.getMatka());
        edits[4].setText(har.getKuormittavuus());
        edits[5].setText(har.getKommentti());
    }
    
      
    /**
     * Viedään lajit ComboBoxChooseriin
     */
    public void vieLaji() {
        for (Laji laji:treenipaivakirja.etsiLaji("", 1)) { 
            lajiChooser.add(laji.getNimi(),laji); 
        } 
    }
    
    
    /**
     * Luodaan harjoituskerran kysymisdialogi ja palautetaan sama tietue muutettuna tai null.
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @param kirja treenipaivakirja
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
   public static Harjoituskerta kysyHarjoitus(Stage modalityStage, Harjoituskerta oletus, Treenipaivakirja kirja) {
   return ModalController.<Harjoituskerta, HarjoitusDialogController>showModal(
            HarjoitusDialogController.class.getResource("HarjoitusDialogView.fxml"),
                "Treenipäiväkirja",
             modalityStage, oletus, ctrl->ctrl.setPaivakirja(kirja)
              );
   
   }
    
    
    private void setPaivakirja(Treenipaivakirja kirja) {
        treenipaivakirja = kirja;
        vieLaji();
    }


}