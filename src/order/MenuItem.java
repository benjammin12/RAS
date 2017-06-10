
package order;
/**
 * 
 * @author Benjamin Goodwin
 * Class name: MenuItem
 * Description: This class sets a menu item's name and price,
 * and then let's the name and price be able to be received.
 * 
 */
public class MenuItem {

	/**
	 * Name will be the name of a menu item.
	 */
	private String item_name;
	
	/**
	 * Price will be the price of a menu item.
	 */
	private double price;
	
	private String item_description;
	
	private int item_id;
	
	/**
	 * This constructor will set the name and price for the menu item
	 * @param name1 new Name
	 * @param price1 new Price
	 */
	public MenuItem(int id1, String name1, double price1, String description1){
		item_id = id1;
		item_name = name1;
		price = price1;
		item_description = description1;
	}
	
	/**
	 * This is the default constructor if a name and price are not set
	 */
	public MenuItem(){
		item_name = "Food";
		price = 0;
		item_description = "Good Food";
		item_id=-1;
	}
	
	/**
	 * Return the name
	 * @return The name of the menu item.
	 */
	public String getName(){
		return item_name;
	}
	
	/**
	 * Return the price
	 * @return The price of the menu item
	 */
	public double getPrice(){
		return price;
	}
	
	/**
	 * Return the description
	 * @return The description of the menu item
	 */
	public String getDescription(){
		return item_description;
	}
	
	/**
	 * Return the id
	 * @return The id of the menu item
	 */
	public int getID(){
		return item_id;
	}
	
	
	
	//CHANGE SETTERS TO NOT VOID
	
	
	/**
	 * Set the name to the parameters input.
	 * @param name new Name
	 */
	public void setName(String name){
		this.item_name = name;
	}
	
	/**
	 * Set the price to the parameters input.
	 * @param price new Price
	 */
	public void setPrice(int price){
		this.price=price;
	}
	
	/**
	 * Set the description to the parameters input.
	 * @param description new Description
	 */
	public void setDescription(String description){
		this.item_description = description;
	}
	
	public String toString(){
		String item = " Id = " + getID() + " Name = " + getName() + " Price = " + getPrice() + " Description = " + getDescription();
		return item;
	}
	
}
