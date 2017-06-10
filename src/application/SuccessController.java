package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import com.sun.glass.events.MouseEvent;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import order.Menu;
import order.MenuItem;
import order.Order;
import order.OrderItems;
import order.OrderList;

/**
 * 
 * @author benjaminxerri
 *User is put here upon a successfull transaction.  They have the option to quit or return to home.
 */
public class SuccessController extends BorderPane {
	Stage stage;

	public SuccessController(Stage stage) throws IOException {
			
			this.stage = stage;
		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Success.fxml"));
		    // make sure that FX root construct is checked in scene builder

		    
		    fxmlLoader.setRoot(this);
	        // leave controller blank in scene builder, or set it to this class
	        // this allows us to overide that setting and reuse the scene as a template for others
	        fxmlLoader.setController(this);
	        
	        try {
	        	// load fxml file
	            fxmlLoader.load();
	        }catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }
	        
	        
		}	
	@FXML protected void quit(ActionEvent ae) throws IOException{
		System.exit(0);
	}
	
	@FXML protected void backToHome(ActionEvent ae) throws IOException{
		//instantiate controller here
		ServerHomeController cont = new ServerHomeController(stage);
		
		Scene scen = new Scene(cont);
		this.stage.setScene(scen);
	}
	

	@FXML protected void loadAbout(ActionEvent ae) throws IOException {
		AboutController cont = new AboutController(stage);
		Scene scen = new Scene(cont);
		this.stage.setScene(scen);
	}
	

}
