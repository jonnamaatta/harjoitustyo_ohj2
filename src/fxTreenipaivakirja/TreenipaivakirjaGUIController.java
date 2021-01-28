package fxTreenipaivakirja;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;

/**
 * Luokka kerhon käyttöliittymän tapahtumien hoitamiseksi.
 * @author jtmaatta
 * @version 25.1.2021
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
        Dialogs.showMessageDialog("Ei osata vielä lisätä");
    }
    

}