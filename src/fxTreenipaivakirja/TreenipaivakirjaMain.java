package fxTreenipaivakirja;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import treenipaivakirja.Treenipaivakirja;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * @author Jonna Määttä
 * @version 7.4.2021
 */
public class TreenipaivakirjaMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("TreenipaivakirjaGUIView.fxml"));
			final Pane root = (Pane)ldr.load();
			final TreenipaivakirjaGUIController treenipaivakirjaCtrl = (TreenipaivakirjaGUIController) ldr.getController();
			
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("treenipaivakirja.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Treenipäiväkirja");
			primaryStage.setOnCloseRequest((event) -> {
			    if ( !treenipaivakirjaCtrl.voikoSulkea() ) event.consume();
			});
			
			
			Treenipaivakirja treenipaivakirja = new Treenipaivakirja();
	            treenipaivakirjaCtrl.setTreenipaivakirja(treenipaivakirja);
	            if ( !treenipaivakirjaCtrl.avaa() ) Platform.exit();
	            primaryStage.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	
	/**
	 * Käynnistetään pääohjelma
	 * @param args joku
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
