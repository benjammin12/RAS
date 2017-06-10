package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import order.OrderItems;
/**
 * 
 * @author benjaminxerri
 *This allows the user to search for an order, and if the order is there, it displays the order and it's current status.  
 */
public class SearchOrderController extends BorderPane  {
	Stage stage;
	@FXML Button placeOrder;
	@FXML Button viewOrder;
	@FXML Button payOrder;
	@FXML TextArea textDisplay;
	@FXML TextArea searchItem;
	@FXML Label errorMessage;
	@FXML Label orderStatusLabel;
	@FXML Stage window;
	@FXML ComboBox<Integer> orderChoice;
	private order.OrderList oi;
	private ObservableList<OrderData> list = FXCollections.observableArrayList(); 
	private HashMap<Integer, List<order.OrderItems>> orderItems111;
	private HashMap<Integer, Order> ordersHashMap;


	@FXML TableView<OrderData> tableUser = new TableView<OrderData>();
	@FXML TableColumn<OrderData, String> orderIdCell;
	@FXML TableColumn<OrderData, String> serverIdCell;
	@FXML TableColumn<OrderData, String> tableIdCell;
	@FXML TableColumn<OrderData, String> orderStatusCell;
	@FXML TableColumn<OrderData, String> orderTotalCell;

	
	public SearchOrderController(Stage stage) throws IOException {
		oi = new order.OrderList(); //create a new order list
		orderItems111 = oi.getOrderItems(); //put those items in a hashmap to iterate

		this.stage = stage;
		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchOrder.fxml"));
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
	        initialze();
		}
	/**
	 * Populates GUI with Orders table from database
	 */
	@FXML public void initialze(){
		orderIdCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("orderId"));
		serverIdCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("serverId"));
		tableIdCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("tableId"));
		orderStatusCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("orderStatus"));
		orderTotalCell.setCellValueFactory(new PropertyValueFactory<OrderData,String>("orderTotal"));
	}
	
	/**
	 * Searches for an order in the database.
	 * Message if not found.
	 * If the order was found, it displays the order and it's status.
	 * 0 if uncooked.
	 * 1 if cooked and not paid.
	 * 2 if cooked and paid.
	 */
	
	@FXML public void searchOrder() {
		list.clear();
		boolean isValid = true;
		String s = searchItem.getText();
		int id = -1;
		try {
			id = Integer.parseInt(s);
		}catch(Exception e){
			errorMessage.setText("*Order Id must be a number");
			isValid = false;

		}
		
		
		if(isValid){
			Order o = oi.searchOrder(id);
			if(o != null){
				errorMessage.setText("");
				list.add(new OrderData(o));
				tableUser.setItems(list);
				System.out.println("Searching For an Order");
				switch(o.getOrderStatus()){
				case 0:
					orderStatusLabel.setText("Order is uncooked.");
					break;
				case 1:
					orderStatusLabel.setText("Order is cooked but not paid");
					break;
				case 2:
					orderStatusLabel.setText("Order is cooked and paid");
					break;
				default:
					orderStatusLabel.setText("Abnormal order status, consider deleting.");
					break;
				}
			}
			else {
				orderStatusLabel.setText("");
				errorMessage.setText("Order not found");
				}
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
	
	public static class OrderData{
		
		final SimpleStringProperty orderId; //error from not naming getters correctly
		final SimpleStringProperty serverId;
		final SimpleStringProperty tableId;
		final SimpleStringProperty orderStatus;
		final SimpleStringProperty orderTotal;
		
		public OrderData(Order o) {
			this.orderId = new SimpleStringProperty(Integer.toString(o.getOrderId()));
			this.serverId = new SimpleStringProperty(Integer.toString(o.getServerId())); //<- this is not converting it to a string
			this.tableId = new SimpleStringProperty(String.valueOf(o.getTableIdinOrder()));
			this.orderStatus = new SimpleStringProperty(Integer.toString(o.getOrderStatus()));
			this.orderTotal = new SimpleStringProperty(Double.toString(o.getOrderTotal()));
		}
			
		public String getOrderId() { //these getters have to be named properly, i.e get getOrderId() , not getOrdId
			return orderId.get();
		}
		
		public String getServerId() {
			return this.serverId.get();
		}

		public String getTableId() {
			return this.tableId.get();
		}

		public String getOrderStatus() {
			return this.orderStatus.get();
		}

		public String getOrderTotal() {
			return this.orderTotal.get();
		}

		
		public void setOrderId(String num){
			this.orderId.set(num);
		}
		/*
		public void setSeatNumber(String num) {
			this.seatNumber.set(num);
		}
		
		public void setPrice(String val) {
			this.price.set(val);
		}
		public void setDesc(String description){
			this.desc.set(description);
		}
		*/
	}
}
