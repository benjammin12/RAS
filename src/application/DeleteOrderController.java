package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


import application.SearchOrderController.OrderData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import order.Order;
/**
 * 
 * @author benjaminxerri
 *This allows the user to search for an order, and if the order is there, it displays the order and it's current status.  
 */
public class DeleteOrderController extends BorderPane  {
	Stage stage;
	@FXML Button placeOrder;
	@FXML Button viewOrder;
	@FXML Button payOrder;
	@FXML TextArea textDisplay;
	@FXML TextArea deleteItem;
	@FXML Label errorMessage;
	@FXML Label orderStatusLabel;
	@FXML Stage window;
	private order.OrderList oi;
	private ObservableList<OrderData> list = FXCollections.observableArrayList(); 
	private HashMap<Integer, List<order.OrderItems>> orderItems111;
	private HashMap<Integer, Order> ord;
	
	private HashMap<Integer, Order> ordersHashMap;



	@FXML TableView<OrderData> tableUser = new TableView<OrderData>();
	@FXML TableColumn<OrderData, String> orderIdCell;
	@FXML TableColumn<OrderData, String> serverIdCell;
	@FXML TableColumn<OrderData, String> tableIdCell;
	@FXML TableColumn<OrderData, String> orderStatusCell;
	@FXML TableColumn<OrderData, String> orderTotalCell;

	
	public DeleteOrderController(Stage stage) throws IOException {
		oi = new order.OrderList(); //create a new order list
		orderItems111 = oi.getOrderItems(); //put those items in a hashmap to iterate

		this.stage = stage;
		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeleteOrder.fxml"));
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
	/**
	 * Populates GUI with Orders table from database
	 */
	@FXML public void initialize(){ //name initialize is automatically called.
		
		oi = new order.OrderList(); //create a new order list
		ordersHashMap = oi.getOrder();
		orderIdCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("orderId"));
		serverIdCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("serverId"));
		tableIdCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("tableId"));
		orderStatusCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("orderStatus"));
		orderTotalCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("orderTotal"));
		
		for(Entry<Integer, Order> key : ordersHashMap.entrySet()){
			Order o = new Order(key.getValue().getOrderId(),key.getValue().getServerId(),key.getValue().getTableIdinOrder(),
				key.getValue().getOrderStatus(),key.getValue().getOrderTotal());
				
        	list.add(new OrderData(o));
        }
		tableUser.setItems(list);
	
	}
	
	/**
	 * Deletes an order from the database. In the process, also deletes order items if they exists.
	 */
	
	@FXML public void deleteOrder() {
		String s = deleteItem.getText();
		int id = -1;
		try {
			id = Integer.parseInt(s);
		}catch(Exception e){
			errorMessage.setText("*Order Id must be a number");

		}
		
		Order o = oi.deleteOrder(id);
		if(o != null){
			errorMessage.setText("");
			System.out.println("Order was deleted.");
			orderStatusLabel.setText("Order was deleted.");
			list.clear();
			initialize();
		}
		else {
			orderStatusLabel.setText("");
			errorMessage.setText("Order not found");
		}
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
