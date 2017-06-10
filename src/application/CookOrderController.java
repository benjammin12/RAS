package application;

import java.io.IOException;

import application.ViewUncokedOrdersController.OrderItemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import order.Order;
import order.OrderList;

/**
 * 
 * @author benjaminxerri
 *Cook Order Controller.  Displays a list of un-cooked order items.
 *This allows the user to enter in the order Id and have the orders status changed to cooked.
 */
public class CookOrderController extends BorderPane {
	Stage stage;
	@FXML Button placeOrder;
	@FXML Button viewOrder;
	@FXML Button payOrder;
	@FXML TextArea textDisplay;
	@FXML Stage window;
	@FXML TextArea cookOrderId;
	@FXML Label errorMessage;
	ViewUncokedOrdersController oc = new ViewUncokedOrdersController(stage);
	private ObservableList<OrderItemData> listCook = FXCollections.observableArrayList(); 
	@FXML TableView<OrderItemData> tableUser = new TableView<OrderItemData>();
	@FXML TableColumn<OrderItemData, String> orderIdCell;
	@FXML TableColumn<OrderItemData, String> seatNumberCell;
	@FXML TableColumn<OrderItemData, String> itemIdCell;
	@FXML TableColumn<OrderItemData, String> itemNameCell;
	@FXML TableColumn<OrderItemData, String> itemPriceCell;
	@FXML TableColumn<OrderItemData, String> itemDescriptionCell;



		
	OrderList od = new OrderList();
	
	public CookOrderController(Stage stage) throws IOException {
			this.stage = stage;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cookOrder.fxml"));
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
	        loadData();
	    }
	
	/**
	 * Cooks an order.
	 * @param ae  Takes in an order ID from the text field
	 * @throws IOException, throws error if it's not a valid input
	 */
	@FXML protected void cookOrder(ActionEvent ae) throws IOException{
		int cookId = -1;
		boolean isValid = true;
		String s = cookOrderId.getText();
		try{
			cookId = Integer.parseInt(s);
		}
		catch(Exception e){
			errorMessage.setText("*Non valid Id");
			isValid = false;
		}
		
		if(isValid){
			Order ord = od.searchOrder(cookId);
			if (ord != null){
				errorMessage.setText("Order has been cooked!");
				od.changeOrderStatus(cookId,1);
				loadSuccess(ae); 
	
			}
			else {
				errorMessage.setText("Order does not exists");
				System.out.println("Couldn't delete Order");
			}
		}
		
	}
	/**
	 * Populates the table with current uncooked orders from the database
	 */
	@FXML public void loadData() { //loads data from the DB
		orderIdCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("orderId"));
		seatNumberCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("seatNumber"));
		itemIdCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("itemId"));
		itemNameCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("name"));
		itemPriceCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("price"));
		itemDescriptionCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("desc"));
		listCook.addAll(oc.getObservableList());
		tableUser.setItems(listCook);
	}
	
	@FXML protected void loadSuccess(ActionEvent ae) throws IOException {
		SuccessController orderI= new SuccessController(stage);
		Scene scen = new Scene(orderI);
		this.stage.setScene(scen);
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
	
	@FXML protected void quit(ActionEvent ae) throws IOException{
		System.exit(0);
	}
	
	
	
}
