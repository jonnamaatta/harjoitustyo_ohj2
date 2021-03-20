package fxTreenipaivakirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import treenipaivakirja.Laji;

/**
 * Kysytään lajin tiedot luomalla sille uusi dialogi
 * 
 * @author Jonna Määttä
 * @version 20.3.2021
 *
 */
public class LajiDialogController implements ModalControllerInterface<Laji>,Initializable  {

    @FXML private TextField editNimi;
    @FXML private Label     labelVirhe;

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    
    @FXML private void handleOK() {
        ModalController.closeStage(labelVirhe);
    }

    
    @FXML private void handleCancel() {
        ModalController.closeStage(labelVirhe);
    }

// ========================================================    
    private Laji lajiKohdalla;
    private TextField edits[];
   

    /**
     * Tyhjennetään tekstikentät.
     * @param edits taulukko jossa tyhjennettäviä tekstikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }


    /**
     * Tekee tarvittavat muut alustukset, esim. laittaa edit-kentistä tulevan
     * tapahtuman menemään kasitteleMuutosLajiin-metodiin ja vie sille
     * kentän numeron parametrina.
     */
    protected void alusta() {
        edits = new TextField[]{editNimi};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosLajiin(k, (TextField)(e.getSource())));
        }
    }
    
    
    @Override
    public void setDefault(Laji oletus) {
        lajiKohdalla = oletus;
        naytaLaji(edits, lajiKohdalla);
    }

    
    @Override
    public Laji getResult() {
        return lajiKohdalla;
    }
    
    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
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
     * Käsitellään lajiin tullut muutos.
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosLajiin(int k, TextField edit) {
        if (lajiKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
           case 1 : virhe = lajiKohdalla.setNimi(s); break;
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
     * Näytetään lajin tiedot TextField-komponentteihin.
     * @param edits taulukko jossa tekstikenttiä
     * @param laji näytettävä laji
     */
    public static void naytaLaji(TextField[] edits, Laji laji) {
        if (laji == null) return;
        edits[0].setText(laji.getNimi());
    }
    
    
    /**
     * Luodaan lajin kysymisdialogi ja palautetaan sama tietue muutettuna tai null.
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Laji kysyLaji(Stage modalityStage, Laji oletus) {
        return ModalController.<Laji, LajiDialogController>showModal(
                    LajiDialogController.class.getResource("LajiDialogView.fxml"),
                    "Treenipäiväkirja",
                    modalityStage, oletus, null 
                );
    }


}