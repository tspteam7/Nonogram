import java.sql.*;
import java.util.*;

import javafx.stage.Stage;

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
	
	public Puzzle() {
		master = new ArrayList<ArrayList<Integer>>();
		working = new ArrayList<ArrayList<Integer>>();
		x = 0;
		y = 0;
		id = 0;
	}

	/**
	 * gets a puzzle from the database
	 * @param id the id of the puzzle to load
	 * @return the puzzle or null if no puzzle is found
	 */
	public ArrayList<ArrayList<Integer>> load( int id ){
		ArrayList<ArrayList<Integer>> al = new ArrayList<>();
		Connection conn = null;
    	PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
					"sjogden",
					"password");
			stmt = conn.prepareStatement("SELECT x, y, data FROM Puzzle WHERE id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(!rs.next()) return null;
			else {
				rs.first();
				al = stringToData(rs.getString("data"), rs.getInt("x"), rs.getInt("y"));
				return al;
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
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
			stmt = conn.prepareStatement("INSERT INTO Puzzles (x, y, data) VALUES(?, ?, ?");
			stmt.setInt(1, puzzle.get(0).size());
			stmt.setInt(2, puzzle.size());
			stmt.setString(3, data);
			rs = stmt.executeQuery();
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
	
	
	public void update( ArrayList<ArrayList<Integer>> puzzle, int id ) {
		Connection conn = null;
    	PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
					"sjogden",
					"password");
			stmt = conn.prepareStatement("UPDATE WorkingPuzzles SET data = ? WHERE puzzle_id = ?");
			stmt.setString(1, dataToString(puzzle));
			stmt.setInt(2, id);
			rs = stmt.executeQuery();
			
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
	
}

