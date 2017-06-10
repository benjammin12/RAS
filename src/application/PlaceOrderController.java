package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javafx.beans.property.SimpleStringProperty;
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
import order.Menu;
import order.MenuItem;
import order.Order;
import order.OrderItems;
import order.OrderList;
/**
 * 
 * @author benjaminxerri
 *Place order Scene.  Allows the user to see all the menu items, and create an order.
 *To create an order, the user must first enter in the Server Id, the table Id, and select at least 1 order item.  
 */
public class PlaceOrderController extends BorderPane {
	//FXML items
	Stage stage;
	@FXML Button placeOrder;
	@FXML Button viewOrder;
	@FXML Button payOrder;
	@FXML TextArea textDisplay;
	@FXML Stage window;
	@FXML Button getValue;
	@FXML Label itemAddedMessage;
	@FXML TextArea serverId;
	@FXML TextArea tableId;
	@FXML Label errorMessage;
	@FXML TextArea itemId;
	@FXML Button addItem;
	
	OrderItems[] orders = new OrderItems[10];

	//private variables
	private int orderNum;
	private int numItems; 
	private int seatNum;

	
	//sets table rows
	@FXML TableView<MenuItemData> tableUser = new TableView<MenuItemData>();
	@FXML TableColumn<MenuItemData, String> itemIdCell;
	@FXML TableColumn<MenuItemData, String> itemNameCell;
	@FXML TableColumn<MenuItemData, String> itemPriceCell;
	@FXML TableColumn<MenuItemData, String> itemDescriptionCell;
	

	
	OrderList orderList = new OrderList();
	private ObservableList<MenuItemData> data = FXCollections.observableArrayList();
	private Menu db;
    HashMap<Integer, MenuItem> hm = new HashMap<>();

	
	public PlaceOrderController(Stage stage) throws IOException {
		orderNum = (int) (Math.random()* 1000);
		numItems = 0;
		seatNum = 1;
		try { //load menu from DB from Menu constructor.
			db = new Menu(); 
		} catch (Exception e) {
			e.printStackTrace(); //catch loading from db errors, error from here is likely to be a problem with DB login credentials
		} 
		hm = db.getHash(); //populates local hashmap with database info
		this.stage = stage;
		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlaceOrder.fxml"));
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
	        
	        try {
				initializeTable(); //when scene loads, attempt to populate table
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        addItem.setOnAction((event) ->{ //lambda function for add item button, active listener for a click, will call addItem function
	        	addOrderItem();
	        });
	    
		}
	
	/**Add Order Item, Function for adding an order item, takes value from GUI field itemID
	 * At least 1 item is required to place an order.
	 */
	 void addOrderItem(){
		 	int item = 0;
			String itemInput = itemId.getText();
			try {
				item = Integer.parseInt(itemInput); //make sure user entered a number
			}
			catch(Exception e){
				errorMessage.setText("Item Id must be a number."); 
			}
			
			if (item >= 1 && item <= 10){ //must be a valid number
				OrderItems oi = new OrderItems(orderNum,seatNum,item,hm.get(item).getName(),hm.get(item).getPrice(),hm.get(item).getDescription());
				orders[numItems] = oi;
				numItems++;
				seatNum++;
				itemAddedMessage.setText("Item added successfully");
				System.out.println("Item added");
			}
			else {
				itemAddedMessage.setText("You must enter an item number between 1 and 10");
			}
		
	}
	/**
	 * Submits order to the database.  Takes in the table ID and server ID for the order.
	 * @param ae listens for submit button
	 * @throws IOException if error with submitting to DB
	 */
	@FXML protected void submitOrder(ActionEvent ae) throws IOException{
		boolean isValidOrder = true;
		int tableInt = 0;
		int serverInt = 0;
		String server = serverId.getText();
		String table = tableId.getText();
		try{ //make sure the fiels are valid numbers
			 serverInt = Integer.parseInt(server);
			 tableInt = Integer.parseInt(table);
		}
		catch(Exception e){
			isValidOrder = false;
			errorMessage.setVisible(true);
			errorMessage.setText("* You can only enter numbers for table Id ");
		}
		Order o = new Order(orderNum,serverInt,tableInt,0,0.0);
	
		try {
			if (isValidOrder && numItems >0){ //if everything is valid, then place the order and load success screen
				orderList.placeOrder(o,orders);
				loadSuccess(ae); 
			}
			else{
				
				errorMessage.setText("Place order failed, you need at least 1 item.");
			}
		} catch (SQLException e) {
			System.out.println("Error Placing Order");
			e.printStackTrace();
		}
	}
	
	@FXML public void initializeTable() throws ClassNotFoundException, SQLException{

		itemIdCell.setCellValueFactory(new PropertyValueFactory<MenuItemData, String>("item_id"));
		itemNameCell.setCellValueFactory(new PropertyValueFactory<MenuItemData,String>("name"));
		itemPriceCell.setCellValueFactory(new PropertyValueFactory<MenuItemData,String>("price"));
		itemDescriptionCell.setCellValueFactory(new PropertyValueFactory<MenuItemData,String>("description"));
		
		for(Integer key : hm.keySet()){
				System.out.println(hm.get(key));
	        	MenuItem menItem = new MenuItem(hm.get(key).getID(),hm.get(key).getName(),hm.get(key).getPrice(),hm.get(key).getDescription());
	        	data.add(new MenuItemData(menItem));
	        }
	        
	        tableUser.setItems(data);


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
	
	@FXML protected void loadSuccess(ActionEvent ae) throws IOException {
		SuccessController orderI= new SuccessController(stage);
		Scene scen = new Scene(orderI);
		this.stage.setScene(scen);

	}
	
	@FXML protected void quit(ActionEvent ae) throws IOException{
		System.exit(0);
	}
	
	
	
public static class MenuItemData{
		
		final SimpleStringProperty name;
	    final SimpleStringProperty item_id;
	    final SimpleStringProperty price;
	    final SimpleStringProperty description;
	   
	    public MenuItemData (MenuItem item){
			this.name = new SimpleStringProperty(item.getName());
			this.item_id = new SimpleStringProperty(Integer.toString(item.getID()));
			this.price = new SimpleStringProperty(Double.toString(item.getPrice()));
			this.description = new SimpleStringProperty(item.getDescription());
	    }
			
			public String getName(){
				return this.name.get();
			}    
			public String getItem_id(){
				return this.item_id.get();
			}
			public String getPrice(){
				return this.price.get();
			}
			public String getDescription(){
				return this.description.get();
			}
			
			public void setName(String name){
				//this.name=item_id.set(name);
			}
	}
}
