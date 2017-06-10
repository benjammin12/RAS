package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import order.MenuItem;
import order.MenuJDBC;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
/*
//Author BEN GOODWIN
//import 
// Note this is an extension of the container class BorderPane. The fxml must also have that as its root.
public class ViewController extends BorderPane implements Initializable{
	private TableView<MenuItemData> itemTable = new TableView<MenuItemData>();
	private ObservableList<MenuItemData> data = FXCollections.observableArrayList();
	private MenuJDBC db;
	final HBox hb = new HBox();
	VBox vbox;
	
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
	
	public ViewController(Stage stage) throws ClassNotFoundException, SQLException{
        Scene scene = new Scene(new Group());
        stage.setWidth(450);
        stage.setHeight(550);
        initializeTable();
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        stage.setScene(scene);
        stage.show();
	}
	
	public void initializeTable() throws ClassNotFoundException, SQLException{
		db=new MenuJDBC();
		MenuItemData iData;
	    final Label label = new Label("Menu");
		itemTable.setEditable(true);
		TableColumn item_name = new TableColumn("Item ID");
		TableColumn item_id = new TableColumn("Item Name");
		TableColumn item_price = new TableColumn("Item Price");
		TableColumn item_description = new TableColumn("Item Description");
		itemTable.getColumns().addAll(item_id, item_name, item_price, item_description);
	    item_name.setMinWidth(100);
	    item_id.setMinWidth(100);
	    item_price.setMinWidth(100);
	    item_description.setMinWidth(100);
        item_name.setCellValueFactory(new PropertyValueFactory<MenuItemData, String>("name"));
        item_id.setCellValueFactory(new PropertyValueFactory<MenuItemData, String>("item_id"));
        item_price.setCellValueFactory(new PropertyValueFactory<MenuItemData, String>("price"));
        item_description.setCellValueFactory(new PropertyValueFactory<MenuItemData, String>("description"));
        db = new MenuJDBC();
        HashMap<Integer, MenuItem> hm = new HashMap<>();
        hm = db.populateMenu();
        
		item_name.setCellFactory(TextFieldTableCell.forTableColumn());
		
		item_name.setOnEditCommit(
            new EventHandler<CellEditEvent<MenuItemData, String>>() {
                @Override
                public void handle(CellEditEvent<MenuItemData, String> t) {
                    ((MenuItemData) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setName((t.getNewValue()));
                }
            }
        );
        
        itemTable.setItems(data);
        for(Integer key : hm.keySet()){
        	iData = new MenuItemData(hm.get(key));
        	data.add(iData);
        }
        final TextField addItemID = new TextField();
        addItemID.setPromptText("Item ID");
        addItemID.setMaxWidth(item_id.getPrefWidth());
        final TextField addItemName = new TextField();
        addItemName.setMaxWidth(item_name.getPrefWidth());
        addItemName.setPromptText("Item Name");
        final TextField addItemPrice = new TextField();
        addItemPrice.setMaxWidth(item_price.getPrefWidth());
        addItemPrice.setPromptText("Item Price");
        final TextField addItemDescription = new TextField();
        addItemDescription.setMaxWidth(item_description.getPrefWidth());
        addItemDescription.setPromptText("Item Description");
 
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
					db.insertData(new MenuItem(Integer.parseInt(addItemID.getText()), addItemName.getText(), Double.parseDouble(addItemPrice.getText()), addItemDescription.getText()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                addItemID.clear();
                addItemName.clear();
                addItemPrice.clear();
                addItemDescription.clear();
            }
        });
        hb.getChildren().addAll(addItemID, addItemName, addItemPrice, addItemDescription, addButton);
        hb.setSpacing(4);
 
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, itemTable, hb);
        
	}
	
	public void addItem(){
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	 //    item_id.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getID() ) ) );
      //   MenuItem mu = new MenuItem();
       //  data.add(mu);
        // ordTable.getItems().setAll(this.data);
	}
	}

*/