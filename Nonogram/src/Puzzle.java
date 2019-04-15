import java.sql.*;
import java.util.*;


/**
 * 
 * @author Isaac Long
 * 
 * The backend to load puzzles to/from the database
 *
 */
public class Puzzle {
	
	ArrayList<ArrayList<Integer>> master;
	ArrayList<ArrayList<Integer>> working;
	int x;
	int y;
	int id;
	String username;
	
	public Puzzle() {
		master = new ArrayList<ArrayList<Integer>>();
		working = new ArrayList<ArrayList<Integer>>();
		x = 0;
		y = 0;
		id = 0;
		username = "";
	}
	
	public Puzzle( int id, String username) {
		this.username = username;
		load(id, username);
	}

	/**
	 * gets a puzzle from the database and loads it into master if there is one.
	 * Also loads the working version of the puzzle for that user into working if there is one.
	 * After calling load, master and working will be null if a puzzle is not found
	 * or creates a blank new working version
	 * @param id the id of the puzzle to load
	 * @param username username of the current user
	 */
	private void load( int id, String username ){
		ArrayList<ArrayList<Integer>> al = new ArrayList<>();
		Connection conn = null;
    	PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//get the puzzle with that id if it exists
			conn = DriverManager.getConnection(
					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
					"sjogden",
					"password");
			stmt = conn.prepareStatement("SELECT x, y, data FROM Puzzles WHERE puzzle_id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			//if it exists
			if(rs.next()) {
				rs.first();
				al = stringToData(rs.getString("data"), rs.getInt("x"), rs.getInt("y"));
				this.master = al;
				this.x = rs.getInt("x");
				this.y = rs.getInt("y");
				this.id = id;
			}
			//otherwise
			else {
				master = null;
				working = null;
				this.id = -1;
				return;
			}
			
			//if a puzzle was found, fetch the working version if there is one or create a new working version
			
			stmt = conn.prepareStatement("SELECT x, y, data FROM WorkingPuzzles WHERE puzzle_id = ? AND username = ?");
			stmt.setInt(1, id);
			stmt.setString(2, username);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				rs.first();
				al = stringToData(rs.getString("data"), rs.getInt("x"), rs.getInt("y"));
				this.working = al;
			}
			
			//if one is not found
			else {
				stmt = conn.prepareStatement("INSERT INTO WorkingPuzzles VALUES(?, ?, ?, ?, ?)");
				stmt.setString(1, username);
				stmt.setInt(2, x);
				stmt.setInt(3, y);
				String temp = "";
				for(int i = 0; i  < (x * y); i++) {
					temp += "0";
				}
				stmt.setString(4, temp);
				stmt.setInt(5, id);
				stmt.execute();
				working = stringToData(temp, x, y);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}
	
	/**
	 * uploads a puzzle to the database. The id will be auto-incremented from whatever
	 * the higheset current id is
	 * @param puzzle the puzzle to upload
	 */
	public void upload( ArrayList<ArrayList<Integer>> puzzle ){
		Connection conn = null;
    	PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
					"sjogden",
					"password");
			String data = dataToString(puzzle);
			stmt = conn.prepareStatement("INSERT INTO Puzzles (x, y, data) VALUES(?, ?, ?)");
			stmt.setInt(1, puzzle.get(0).size());
			stmt.setInt(2, puzzle.size());
			stmt.setString(3, data);
			stmt.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * updates a puzzle in the database
	 * @param puzzle the new version of the puzzle
	 * @param id the id of the puzzle to update
	 */
	
	
	public void update( ArrayList<ArrayList<Integer>> puzzle, int id, String username ) {
		Connection conn = null;
    	PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
					"sjogden",
					"password");
			stmt = conn.prepareStatement("UPDATE WorkingPuzzles SET data = ? WHERE puzzle_id = ? AND username = ?");
			stmt.setString(1, dataToString(puzzle));
			stmt.setInt(2, id);
			stmt.setString(3, username);
			stmt.execute();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * 
	 * @param s string representing a puzzle
	 * @param x number of columns in the puzzle
	 * @param y number of rows in the puzzle
	 * @return an arraylist of integers parsed from the puzzle string
	 */
	public ArrayList<ArrayList<Integer>> stringToData( String s, int x, int y ){ 
		ArrayList<ArrayList<Integer>> puzzle = new ArrayList<>();
		for(int i = 0; i < y; i++) {
			puzzle.add(i, new ArrayList<Integer>());
			for(int j = 0; j < x; j++) {
				puzzle.get(i).add(j, Character.getNumericValue(s.charAt((i * x) + j)));
			}
		}
		return puzzle;
	}
	
	/**
	 * 
	 * @param a representation of the puzzle
	 * @return a string representing the puzzle
	 */
	public String dataToString(ArrayList<ArrayList<Integer>> puzzle ) {
		String data = "";
		for( ArrayList<Integer> al : puzzle) {
			for(Integer i : al) {
				data += Integer.toString(i);
			}
		}
		return data;
	}
	
	/*
	 * Getters and setters
	 */
	
	public ArrayList<ArrayList<Integer>> getMaster() {
		return master;
	}

	public void setMaster(ArrayList<ArrayList<Integer>> master) {
		this.master = master;
	}

	public ArrayList<ArrayList<Integer>> getWorking() {
		return working;
	}

	public void setWorking(ArrayList<ArrayList<Integer>> working) {
		this.working = working;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}

