package order;
import java.sql.*;
import java.util.HashMap;

/**
 * Class Description: This class will add, remove, update, and search the database
 * @author Benjamin
 *
 */
public class MenuJDBC {

	private static String url = "jdbc:mysql://localhost:3306/RAS3";
	private static String username = "root";
	private static String password = "mcNp#tzpQi7";
	private Connection conn = null;
	private Statement st = null;
	private MenuItem item;
	private PreparedStatement pt;
	private ResultSet rs;
	private HashMap<Integer, MenuItem> list;
	
	public MenuJDBC() throws ClassNotFoundException, SQLException{
		list = new HashMap<>();
		populateMenu();
	}
	/**
	 * This will find an id in the database
	 * @param id the wanted id
	 * @return the item
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MenuItem findItem (int id) throws ClassNotFoundException, SQLException{
		String query = "SELECT * FROM menuitem WHERE item_id = " + id + ";";
		try{
			conn=getDBConnection();
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				return new MenuItem(rs.getInt("item_id"), rs.getString("item_name"), rs.getInt("item_price"), rs.getString("item_description"));
			}
		}
		catch(SQLException e){
			System.out.print(e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * This will insert a new menuitem to the database
	 * @param item a new menuitem
	 * @return 0 if the item was not added or the id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MenuItem insertData(MenuItem item) throws ClassNotFoundException, SQLException{
		String sql = "INSERT INTO menuitem" + "(item_id, item_name, item_price, item_description)" + " VALUES " + "(" + item.getID() + ", \'" + item.getName() + "\', " + item.getPrice() + ", \'" + item.getDescription() + "\');";
		try{
			conn = getDBConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			list.put(item.getID(), item);
			return item;
		}
		catch(SQLException e){
			System.out.print(e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * This will delete a menuitem from the database
	 * @param id the wanted menuitem to be deleted
	 * @return the id that was deleted or 0 if it wasnt
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MenuItem deleteData(int id)throws ClassNotFoundException, SQLException{
		String sql = "DELETE FROM menuitem WHERE item_id = " + id  + ";";
		item = findItem(id);
		try{
			conn = getDBConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			list.remove(id);
			return item;
		}
		catch(SQLException e){
			System.out.print(e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * This will update a menuitem from the database
	 * @param id the wanted menuitem to be deleted
	 * @return the id that was updated or 0 if it wasnt
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MenuItem updateData(MenuItem item, int id)throws ClassNotFoundException, SQLException{
		String sql = "UPDATE menuitem SET item_id = ?, item_name = ?, item_price = ?, item_description = ? WHERE item_id = ?";
		try{
			conn = getDBConnection();
			pt=conn.prepareStatement(sql);
			pt.setInt(1, item.getID());
			pt.setString(2, item.getName());
			pt.setDouble(3, item.getPrice());
			pt.setString(4, item.getDescription());
			pt.setInt(5, id);
			pt.executeUpdate();
			list.put(id, item);
			return item;
		}
		catch(SQLException e){
			System.out.print(e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * This will connect the jdbc to the database
	 * @return the database connection
	 */
	public Connection getDBConnection() throws ClassNotFoundException, SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection(url,username,password);
		    return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public HashMap<Integer, MenuItem> populateMenu() throws SQLException, ClassNotFoundException{
		conn=getDBConnection();
		pt = conn.prepareStatement("SELECT * FROM menuitem");
		rs = pt.executeQuery();
		while(rs.next()){
			Integer itemID = new Integer(rs.getInt("item_id"));
			String itemName = new String(rs.getString("item_name"));
			Double itemPrice = new Double(rs.getDouble("item_price"));
			String itemDes = new String(rs.getString("item_description"));
			list.put(itemID, new MenuItem(itemID, itemName, itemPrice, itemDes));
		}
		return list;
	}
	
	public HashMap<Integer, MenuItem> getHash(){
		return list;
	}
	
	@Override
	public String toString(){
		return list.toString();
	}
	
}