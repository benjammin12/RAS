package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import order.OrderItems;
/**
 * 
 * @author benjaminxerri
 *This scene allows the user to see all the uncooked orders.  
 */
public class ViewUncokedOrdersController extends BorderPane implements Initializable {
	Stage stage;
	@FXML Button placeOrder;
	@FXML Button viewOrder;
	@FXML Button payOrder;
	@FXML TextArea textDisplay;
	@FXML Stage window;
	@FXML ComboBox<Integer> orderChoice;
	private order.OrderList oi;
	private ObservableList<OrderItemData> list = FXCollections.observableArrayList(); 
	private HashMap<Integer, List<order.OrderItems>> orderItemsHashMap;

	@FXML TableView<OrderItemData> tableUser = new TableView<OrderItemData>();
	@FXML TableColumn<OrderItemData, String> orderIdCell;
	@FXML TableColumn<OrderItemData, String> seatNumberCell;
	@FXML TableColumn<OrderItemData, String> itemIdCell;
	@FXML TableColumn<OrderItemData, String> itemNameCell;
	@FXML TableColumn<OrderItemData, String> itemPriceCell;
	@FXML TableColumn<OrderItemData, String> itemDescriptionCell;

	
	public ViewUncokedOrdersController(Stage stage) throws IOException {
		oi = new order.OrderList(); //create a new order list
		orderItemsHashMap = oi.getOrderItems(); //put those items in a hashmap to iterate

		this.stage = stage;
		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewUncookedOrders.fxml"));
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
	 * FXML method to populate GUI with all of the un-cooked orders
	 */
	@FXML public void initialze(){
		orderIdCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("orderId"));
		seatNumberCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("seatNumber"));
		itemIdCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("itemId"));
		itemNameCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("name"));
		itemPriceCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("price"));
		itemDescriptionCell.setCellValueFactory(new PropertyValueFactory<OrderItemData,String>("desc"));
		
		for(Entry<Integer, List<OrderItems>> key: orderItemsHashMap.entrySet()){
			//System.out.println("key: " +key + ": " + "Value: " + orderItems111.get(key));
			for(OrderItems num : key.getValue()){
				OrderItems o = new OrderItems(num.getOrderItemsId(),num.getSeatNumber(),num.getID(),num.getName(),num.getPrice(),num.getDescription());
				list.add(new OrderItemData(o));
			}
			
		}
		tableUser.setItems(list);
		
	}
	public ObservableList<OrderItemData> getObservableList(){
		return list;
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
	

	public void initialize(URL arg0, ResourceBundle arg1) {


	}
	
	@FXML protected void populateItems(){
		
	}
	
	public static class OrderItemData{
		
		final SimpleStringProperty orderId; //error from not naming getters correctly
		final SimpleStringProperty seatNumber;
		final SimpleStringProperty menuItemId;
		final SimpleStringProperty name;
		final SimpleStringProperty price;
		final SimpleStringProperty desc;
		
		public OrderItemData(order.OrderItems item) {
			//String s = Integer.toString(item.getOrderItemsId());
			this.orderId = new SimpleStringProperty(Integer.toString(item.getOrderItemsId()));
		//	this.oId = new SimpleIntegerProperty(item.getOrderItemsId());
			this.seatNumber = new SimpleStringProperty(String.valueOf(item.getSeatNumber())); //<- this is not converting it to a string
			this.menuItemId = new SimpleStringProperty(String.valueOf(item.getID()));
			this.name = new SimpleStringProperty(item.getName());
			this.price = new SimpleStringProperty(Double.toString(item.getPrice()));
			this.desc = new SimpleStringProperty(item.getDescription());
		}
			
		public String getOrderId() { //these getters have to be named properly, i.e get getOrderId() , not getOrdId
			return orderId.get();
		}
		
		public String getSeatNumber() {
			return this.seatNumber.get();
		}

		public String getItemId() {
			return this.menuItemId.get();
		}

		public String getName() {
			return this.name.get();
		}

		public String getPrice() {
			return this.price.get();
		}

		public String getDesc() {
			return this.desc.get();
		}
		
		public void setOrderId(String num){
			this.orderId.set(num);
		}
		public void setSeatNumber(String num) {
			this.seatNumber.set(num);
		}
		
		public void setPrice(String val) {
			this.price.set(val);
		}
		public void setDesc(String description){
			this.desc.set(description);
		}
		
		@FXML protected void quit(ActionEvent ae) throws IOException{
			System.exit(0);
		}
		
		
	}
}
