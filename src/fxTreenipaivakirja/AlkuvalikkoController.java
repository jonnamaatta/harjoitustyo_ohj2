package fxTreenipaivakirja;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Aloitusvalikon ja ohjelman tietojen näytön hoitava luokka
 * @author Jonna Määttä
 * @version 7.4.2021
 */
public class AlkuvalikkoController implements ModalControllerInterface<String> {
    @FXML private Label textAuthor;
    
    
    @FXML private void handleOK() {
        ModalController.closeStage(textAuthor);
    }
        
    
    @Override
    public String getResult() {
        return null;
    }

    
    @Override
    public void setDefault(String oletus) {
        //
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty.
     */
    @Override
    public void handleShown() {
        textAuthor.requestFocus();
    }
    
    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null.
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String avaa(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                AlkuvalikkoController.class.getResource("AlkuvalikkoView.fxml"),
                "Treenipäiväkirja",
                modalityStage, oletus);
    }
}
