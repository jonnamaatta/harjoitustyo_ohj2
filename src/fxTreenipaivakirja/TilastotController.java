package fxTreenipaivakirja;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import treenipaivakirja.Laji;
import treenipaivakirja.Treenipaivakirja;


/**
 * Tilastot hoitava luokka
 * @author Jonna Määttä
 * @version 9.4.2021
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
        return null;
    }


    @Override
    public void handleShown() {
        //
    }


    @Override
    public void setDefault(Treenipaivakirja oletus) {
        treenipaivakirja = oletus;
    }
    
    
    /**
     * Avaa tilastodialogin
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksen
     * @return dialogi, jossa laskettu tilastot
     */
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
     * Laskee tilastot.
     */
    private void laske() {
        int suosituinlaji = treenipaivakirja.haeSuosituinLaji();
        Laji laji = treenipaivakirja.annaLajiTn(suosituinlaji);
        String suosituimmannimi = laji.getNimi();
        double keskikuormittavuus = treenipaivakirja.laskeKeskikuormittavuus();     
        double keskimatka = treenipaivakirja.laskeKeskimatka();
        double pisinmatka = treenipaivakirja.haePisinmatka();
        asetaArvot(suosituimmannimi, keskikuormittavuus, pisinmatka, keskimatka);
    }

    /**
     * Asettaa arvot tekstikenttiin
     * @param suosituimmannimi suosituimman lajin nimi
     * @param keskikuormittavuus keskimääräinen kuormittavuus
     * @param pisinmatka pisinmatka
     * @param keskimatka keskimääräin matka
     */
    private void asetaArvot(String suosituimmannimi, double keskikuormittavuus,
            double pisinmatka, double keskimatka) {
        textSuosituinlaji.setText(suosituimmannimi);
        if(keskikuormittavuus == 0) {
            textKeskikuormittavuus.setText("");
        }
        else textKeskikuormittavuus.setText(String.valueOf(keskikuormittavuus));
        textPisinmatka.setText(String.valueOf(pisinmatka));
        textKeskimatka.setText(String.valueOf(keskimatka));
    }

}