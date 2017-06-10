package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import application.SearchOrderController.OrderData;
import application.ViewUncokedOrdersController.OrderItemData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import order.MenuItem;
import order.Order;

/**
 * 
 * @author benjaminxerri
 *Pay order Scene.  Allows the user to see all the orders and pay for an order that has been cooked.  
 */
public class PayOrderController extends BorderPane {
	Stage stage;
	@FXML Button placeOrder;
	@FXML Button viewOrder;
	@FXML Button payOrder;
	@FXML Stage window;
	@FXML TextArea textDisplay;
	@FXML TextArea deleteItem;
	@FXML Label errorMessage;
	@FXML Label orderStatusLabel;
	private order.OrderList oi;
	private ObservableList<OrderData> list = FXCollections.observableArrayList(); 
	private HashMap<Integer, Order> ordersHashMap;
	private HashMap<Integer, Order> ord;
	@FXML TableView<OrderData> tableUser = new TableView<OrderData>();
	@FXML TableColumn<OrderData, String> orderIdCell;
	@FXML TableColumn<OrderData, String> serverIdCell;
	@FXML TableColumn<OrderData, String> tableIdCell;
	@FXML TableColumn<OrderData, String> orderStatusCell;
	@FXML TableColumn<OrderData, String> orderTotalCell;
	
	public PayOrderController(Stage stage) throws IOException {

		this.stage = stage;
		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PayOrder.fxml"));
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
			if(key.getValue().getOrderStatus()==1)
			{
        	list.add(new OrderData(o));
			}
        }
		tableUser.setItems(list);
	
	}
	
	@FXML protected void payOrder(ActionEvent ae) throws IOException{
		int cookId = -1;
		String s = deleteItem.getText();
		boolean inputCheck=true;
		try{
			cookId = Integer.parseInt(s);
		}
		catch(Exception e)
		{
			inputCheck=false;
			errorMessage.setText("*Non valid Id");
		}
		if(inputCheck)
		{
			Order ord = oi.payOrder(cookId);
		
			if (ord != null){
			errorMessage.setText("");
			System.out.println("Order was Paid.");
			orderStatusLabel.setText("Order Was Paid.");
			list.clear();
			initialize();
		}
			else 
				{
				orderStatusLabel.setText("");
				errorMessage.setText("Order does not exists");
				//System.out.println("Couldn't delete Order");
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
	
	

}
