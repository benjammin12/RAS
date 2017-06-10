package order;
import java.sql.SQLException;
import java.util.HashMap;



/**
 * 
 * @author Benjamin
 * Class Name: Menu
 * DESCRIPTION: This class adds, removes, search, and updates items to the menu and views the menu.
 *
 */
public class Menu {
	
	private MenuJDBC db;
	private MenuItem item;
	
	/**
	 * This method will create a new MenuItem with the given name and price and add that to the Hashmap.
	 * @param item The menu item that needs to be added
	 * @return true if the item was added or false if the item was not added
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	public Menu() throws ClassNotFoundException, SQLException{
		db = new MenuJDBC();
		item = new MenuItem();
	}
	
	public boolean addItem(MenuItem item) throws ClassNotFoundException, SQLException{
		try{
			db.insertData(item);
			return true;
		}
		catch(SQLException e){
			System.out.print(e.getStackTrace());
		}
		return false;
	}
	
	/**
	 * This method will search the hashmap for the wanted MenuItem.
	 * @param name The name of the item to be searched for
	 * @return the menu item that was searched for
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public MenuItem searchItem(int id) throws ClassNotFoundException, SQLException{
		return db.getHash().get(id);
	}
	
	/**
	 * This method will call on the search method to look for an item,
	 * then if that item is found it will be removed from the hashmap with it's price.
	 * @param name The name of the item to be removed
	 * @return the MenuItem that was removed
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public MenuItem removeItem(int id) throws ClassNotFoundException, SQLException{
		try{
			item = db.deleteData(id);
			return item;
		}
		catch(SQLException e){
			System.out.print(e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * This method will call on the search method to look for an item,
	 * then if that item is found it will change that item's name or price.
	 * @param name The name of the item to be changed
	 * @return the menu item that was updated
	 * @throws ClassNotFoundException 
	 */
	public MenuItem updateItem(MenuItem item, int id) throws ClassNotFoundException{
		try{
			return db.updateData(item, id);
		}
		catch(SQLException e){
			System.out.print(e.getStackTrace());
		}
		return null;
	}
	

	public HashMap<Integer, MenuItem> getHash(){
		return db.getHash();
	}
	
	
	/**
	 * This method will output everything as a string
	 * @return a string of text
	 */
	@Override
	public String toString(){
		String st = "";
        for(Integer key : db.getHash().keySet()){
        	st += db.getHash().get(key).toString() + "\n";
        }
        return st;
	}
	
}