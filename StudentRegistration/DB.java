import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
	// Run SQL queries
	protected Statement s;
	protected ResultSet rs;
	protected Connection connection;
	
	
	// Constructor that establishes the database connection
	DB() {
		// Establish database connection
		String connectionString = "jdbc:hsqldb:testdb,sa,";
		connection = null;		
		try {
			connection = DriverManager.getConnection(connectionString);
			s = connection.createStatement();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	

	//Method for creating 2 tables: STUDENTS, COURSES
	public void createtables () {
		// CREATE STUDENTS TABLE
		String sql = "CREATE TABLE STUDENTS(ID INT IDENTITY PRIMARY KEY, LNAME VARCHAR(24), FNAME VARCHAR(24), USERNAME VARCHAR(24), PASSWORD VARCHAR(24));";
		try {
			rs = s.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// INSERT INTO table
		try {
			sql = "INSERT INTO STUDENTS VALUES(null, 'Morgan', 'Tracy', 'tracy', 'password')";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO STUDENTS VALUES(null, 'Green', 'Rachel', 'rachel', 'password2');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO STUDENTS VALUES(null, 'Caviezel', 'Jim', 'jim', 'pass');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO STUDENTS VALUES(null, 'Price', 'Jonathan', 'jonathan', 'word');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO STUDENTS VALUES(null, 'Cooper', 'Sheldon', 'sheldon', '12345');";
			rs = s.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		// CREATE CLASSES TABLE, Start & End time are in TIME format
		sql = "CREATE TABLE COURSES(ID INT IDENTITY PRIMARY KEY, COURSENAME VARCHAR(24), DAYS VARCHAR(24), STARTTIME TIME, ENDTIME TIME);";
		try {
			rs = s.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// INSERT INTO table
		try {
			sql = "INSERT INTO COURSES VALUES(null, 'SPEECH', 'MONDAY', '11:00:00', '12:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'PHYSICS', 'TUESDAY', '08:00:00', '09:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'CHEMISTRY', 'TUESDAY', '09:00:00', '10:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'BIOLOGY', 'TUESDAY', '10:00:00', '11:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'LOGIC', 'TUESDAY', '11:00:00', '12:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'JAVA', 'WEDNESDAY', '08:00:00', '09:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'ALGORITHM', 'WEDNESDAY', '09:00:00', '10:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'CALCULUS', 'WEDNESDAY', '10:00:00', '11:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'ALGEBRA', 'WEDNESDAY', '11:00:00', '12:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'TOPOLOGY', 'THURSDAY', '08:00:00', '09:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'DATABASE', 'THURSDAY', '09:00:00', '10:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'HISTORY', 'THURSDAY', '10:00:00', '11:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'SPORTS', 'THURSDAY', '11:00:00', '12:50:00');";
			rs = s.executeQuery(sql);
			sql = "INSERT INTO COURSES VALUES(null, 'PHILOSOPHY', 'FRIDAY', '11:00:00', '12:50:00');";
			rs = s.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	// sample print all student's first names in descending order
	public void printstudents() {
		// SELECT FROM TABLE
		try {
			String sql = "SELECT * FROM STUDENTS ORDER BY FNAME ASC;";
			rs = s.executeQuery(sql);
			while (rs.next()){
				System.out.println(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// sample print all course names in descending order
	public void printcourses() {
		// SELECT FROM TABLE
		try {
			String sql = "SELECT * FROM COURSES ORDER BY COURSENAME ASC;";
			rs = s.executeQuery(sql);
			while (rs.next()){
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	// This will drop all tables, restart new, we might not need this eventually
	public void droptables() {
		// DROP TABLE
		try {
			rs = s.executeQuery("DROP TABLE STUDENTS;");
			rs = s.executeQuery("DROP TABLE COURSES;");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	// this closes all connection to database
	public void closeconnection() {
		// Close database connection
		try {
			rs.close();
			s.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// Main
	public static void main(String[] args) {
		
		DB db = new DB();
		
		db.droptables();
		db.createtables();
//		db.printstudents();
//		db.printcourses();
//		db.droptables();
		db.closeconnection();

		

	}
}