package dao;
import myApp.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public enum PeopleDao {
instance;
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/oneDB", "SA", "Passw0rd");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	public ArrayList showAll() {
		Connection connection = getConnection();
		ArrayList<Person> peopleList = new ArrayList<Person>();
		
		try {
			PreparedStatement psmt = connection.prepareStatement("Select * from people");
			
			ResultSet data = psmt.executeQuery();
			while(data.next()) {
				String id = data.getString("id");
				String firstName = data.getString("first_name");
				String lastName = data.getString("last_name");
				String car = data.getString("car");
				String city = data.getString("city");
				String country = data.getString("country");
                peopleList.add( new Person (id, firstName, lastName, car, city, country));

			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return peopleList;
	}
	public Person getPerson(String id) {
		Connection connection = getConnection();
		Person person = new Person();
		try {
			PreparedStatement psmt = connection.prepareStatement("Select * from people where id = ?");
			psmt.setString(1,  id);
			ResultSet data = psmt.executeQuery();
			while(data.next()) {
				person.setId(id);
				person.setFirstName(data.getString("first_name"));
				person.setLastName(data.getString("last_name"));
				person.setCar(data.getString("car"));
				person.setCity(data.getString("city"));
				person.setCountry(data.getString("country"));

			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return person;
	}
	public void deleteAccount(String id) {
		try {
			Connection connection = getConnection();
			PreparedStatement psmt = connection.prepareStatement("DELETE FROM People WHERE id = ?");
			psmt.setString(1,  id);
			psmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void addPerson(Person person){
        try{
        	Connection connection = getConnection();
            PreparedStatement psmt = connection.prepareStatement("INSERT INTO people(first_name, last_name, car, city, country) values( ?,?,?,?,?)");  
            psmt.setString(1, person.getFirstName());
            psmt.setString(2, person.getLastName());
            psmt.setString(3, person.getCar());
            psmt.setString(4, person.getCity());
            psmt.setString(5, person.getCountry());
            
            psmt.executeUpdate();
           
            
        } catch(SQLException ex){
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
    }
	public void updatePerson(Person person){
	       try{
	    	   Connection connection = getConnection();
	            PreparedStatement psmt = connection.prepareStatement("UPDATE people SET first_name = ?, last_name = ?, car = ?, city = ?, country = ?  WHERE id = ?");  
	            psmt.setString(1, person.getFirstName());
	            psmt.setString(2, person.getLastName());
	            psmt.setString(3, person.getCar());
	            psmt.setString(4, person.getCity());
	            psmt.setString(5, person.getCountry());
	            psmt.setString(6, person.getId());
	            psmt.executeUpdate();
	            
	            
	        } catch(SQLException ex){
	            System.err.println("\nSQLException");
	            ex.printStackTrace();
	        }
	    }
}
