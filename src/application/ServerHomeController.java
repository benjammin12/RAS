package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * 
 * @author benjaminxerri
 * Order Home page.  Used to connect to all other order functions with button clicks.
 */
public class ServerHomeController extends BorderPane{
	Stage stage;
	@FXML Button placeOrder;
	@FXML Button viewOrder;
	@FXML Button payOrder;
	@FXML Button searchOrder;
	@FXML TextArea textDisplay;
	@FXML Stage window;
	
	public ServerHomeController(Stage stage){
		this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ServerHome.fxml"));
        // make sure that FX root construct is checked in scene builder
        fxmlLoader.setRoot(this);
     
        fxmlLoader.setController(this);
        
        try {
        	// load fxml file
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

	}
	
	
	@FXML protected void changeSceneUpdateOrder(ActionEvent ae) throws IOException{
		//instantiate controller here
		ViewUncokedOrdersController cont = new ViewUncokedOrdersController(stage);
		
		Scene scen = new Scene(cont);
		this.stage.setScene(scen);
	}
	
	@FXML protected void changeScenePayOrder(ActionEvent ae) throws IOException{
		//instantiate controller here
		PayOrderController cont = new PayOrderController(stage);
		
		Scene scen = new Scene(cont);
		this.stage.setScene(scen);
	}
	
	@FXML protected void changeSceneSearchOrder(ActionEvent ae)throws IOException {
		SearchOrderController cont = new SearchOrderController(stage);
		Scene scen = new Scene(cont);
		this.stage.setScene(scen);
	}
	
	@FXML protected void changeScenePlaceOrder(ActionEvent ae) throws IOException{
		//instantiate controller here
		PlaceOrderController cont = new PlaceOrderController(stage);
		
		Scene scen = new Scene(cont);
		this.stage.setScene(scen);
	}
	@FXML protected void changeSceneCookOrder(ActionEvent ae) throws IOException{
		//instantiate controller here
		CookOrderController cont = new CookOrderController(stage);
		
		Scene scen = new Scene(cont);
		this.stage.setScene(scen);
	}
	
	@FXML protected void changeSceneDeleteOrder(ActionEvent ae)throws IOException {
		DeleteOrderController cont = new DeleteOrderController(stage);
		Scene scen = new Scene(cont);
		this.stage.setScene(scen);
	}
	
	
	@FXML protected void loadAbout(ActionEvent ae) throws IOException {
		AboutController cont = new AboutController(stage);
		Scene scen = new Scene(cont);
		this.stage.setScene(scen);
	}
	@FXML protected void quit(ActionEvent ae) throws IOException{
		System.exit(0);
	}
	
	
}
