package fxTreenipaivakirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import treenipaivakirja.Harjoituskerta;
import treenipaivakirja.Laji;
import treenipaivakirja.Treenipaivakirja;


/**
 * Tilastot hoitava luokka
 * @author Jonna Määttä
 * @version 6.4.2021
 *
 */
public class TilastotController implements ModalControllerInterface<Treenipaivakirja> {
    @FXML private TextField  textSuosituinlaji;
    @FXML private TextField  textKeskikuormittavuus;
    @FXML private TextField  textKeskimatka;
    @FXML private TextField  textPisinmatka;
    @FXML private Label      tilastoja;
    
    private Treenipaivakirja treenipaivakirja;
 
    
    @FXML private void handleOK() {
        ModalController.closeStage(tilastoja);
    }

    
    @Override
    public Treenipaivakirja getResult() {
        // TODO Auto-generated method stub
        return null;
    }



    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void setDefault(Treenipaivakirja oletus) {
        // TODO Auto-generated method stub
        
    }
    
    
    public static Treenipaivakirja laske(Stage modalityStage, Treenipaivakirja oletus) {
       return ModalController.showModal(
                TilastotController.class.getResource("TilastotView.fxml"),
                "Tilastoja", modalityStage, oletus, ctrl->((TilastotController) ctrl).setPaivakirja(oletus));
    }
    
    
    private void setPaivakirja(Treenipaivakirja kirja) {
        treenipaivakirja = kirja;
        laske();
    }
    
    
    /**
     * TODO: lisää oikeiden tilastojen laskeminen
     */
    private void laske() {
        textSuosituinlaji.setText("kävely");
        textKeskikuormittavuus.setText("4.4");
        textKeskimatka.setText("7.4");
        textPisinmatka.setText("11.2");
    }


}
