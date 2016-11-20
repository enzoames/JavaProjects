import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;

public class Scheduler {
	
	private boolean registerUser(String username, String firstName, String lastName, String password){
		boolean registered = false;
		// connection to database
		DB db = new DB();
		
		// Check if new user already exists
		String sql = "SELECT USERNAME FROM STUDENTS WHERE USERNAME = '" + username + "';";
		try {
			db.rs  = db.s.executeQuery(sql);
			// user already exists
			if (db.rs.next()){					
				System.out.println("I'm sorry, user " + username + " has already been taken.");
				registered = false;
			}
			// register the user
			else {
				sql = "INSERT INTO STUDENTS VALUES(null, '" + lastName + "', '" + firstName + "', '" + username + "', '" + password + "')";
				db.rs  = db.s.executeQuery(sql);
				System.out.println("Thanks for registering " + firstName);
				registered = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeconnection();
		return registered;
	}
	
	private boolean loginUser(String username, String password){
		boolean flag = false;
		// connection to database		
		DB db = new DB();
		String dbUsername;
		String dbPassword;
		String sql;
		
		sql = "SELECT USERNAME, PASSWORD FROM STUDENTS;";
		try{
			db.rs = db.s.executeQuery(sql);
			
			while(db.rs.next())
			{
				dbUsername = db.rs.getString("USERNAME");
				dbPassword = db.rs.getString("PASSWORD");
				if(username.equals(dbUsername) && password.equals(dbPassword))
				{
					flag=true;
					return flag;
				}
			}
			
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
		return flag;
	}
	
	// get firstname of user
	private String getFName(String username){
		String firstname = null;
		// connection to database
		DB db = new DB();
		
		// Check if new user already exists
		String sql = "SELECT FNAME FROM STUDENTS WHERE USERNAME = '" + username + "';";
		try {
			db.rs = db.s.executeQuery(sql);
			if (db.rs.next())	firstname = db.rs.getString("FNAME");	// username found, get First Name
			else firstname = "No such user!";	// redundant statement, but just in case
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeconnection();
		return firstname;
	}
	
	// check if course ID # id exists in the database
	private boolean courseExists(int id) {
		boolean flag = false;
		// connection to database
		DB db = new DB();
		
		// find course id
		String sql = "SELECT ID FROM COURSES WHERE ID = " + id + ";";
		try {
			db.rs = db.s.executeQuery(sql);
			if (db.rs.next())	flag = true;
			else flag = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeconnection();
		return flag;
	}
	
	// print all courses
	private void printAllCourses(){
		// connection to database
		DB db = new DB();
		
		// Print entire COURSES Table
		try {
			System.out.printf("%-5s %-25s %-15s %-10s %-10s%n", "ID", "Course Name", "Day", "Start Time", "End Time");
			System.out.println("============================================================================");
			String sql = "SELECT * FROM COURSES ORDER BY COURSENAME ASC;";
			db.rs = db.s.executeQuery(sql);
			while (db.rs.next()){
				System.out.printf("%-5s %-25s %-15s %-10s %-10s%n", db.rs.getString("ID").toString(), db.rs.getString("COURSENAME"), db.rs.getString("DAYS"), String.format("%1$tR %1$Tp",db.rs.getTimestamp("STARTTIME")), String.format("%1$tR %1$Tp",db.rs.getTimestamp("ENDTIME")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeconnection();
	}
	
	
	// List all courses of current user
	private void listMyCourses(int[] myCourses, int count){
		if (count == 0) {
			System.out.println("You have not selected any course!");
			return;
		}
		// connection to database
		DB db = new DB();
		
		// list my courses from array: myCourses
		try {
			for (int i = 0; i < count; i++) {
				db.rs = db.s.executeQuery("SELECT * FROM COURSES WHERE ID = " + myCourses[i] + ";");
				if (db.rs.next()) System.out.printf("%-5s %-25s %-15s %-10s %-10s%n", db.rs.getString("ID").toString(), db.rs.getString("COURSENAME"), db.rs.getString("DAYS"), String.format("%1$tR %1$Tp",db.rs.getTimestamp("STARTTIME")), String.format("%1$tR %1$Tp",db.rs.getTimestamp("ENDTIME")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeconnection();
	}
    
    //Checks current schedule for conflicts (returns true if conflict is found with userChoice : false otherwise)
    private boolean checkForConflicts(int[] userSchedule,int courseCount,String userChoice){
        String userCourseDay = null;
        Time userCourseStart = null;
        Time userCourseEnd = null;
    	if(courseCount == 0){
            return false;
        }
        
        // connection to database
        DB db = new DB();
        
        //Gets information from userChoice class (i.e. Day, Start time, End Time)
        int userCourseInt = Integer.parseInt(userChoice);
        try {
			db.rs = db.s.executeQuery("SELECT * FROM COURSES WHERE ID = " + userCourseInt + ";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			if(db.rs.next()){
			    userCourseDay = db.rs.getString("DAYS");
			    userCourseStart = db.rs.getTime("STARTTIME");
			    userCourseEnd = db.rs.getTime("ENDTIME");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //Compares userChoice class information to class information in userSchedule
        for(int i = 0; i < courseCount; i++){
            try {
				db.rs = db.s.executeQuery("SELECT * FROM COURSES WHERE ID = " + userSchedule[i] + ";");
				if(db.rs.next()){
				    String day = db.rs.getString("DAYS");
				    Time start = db.rs.getTime("STARTTIME");
				    Time end = db.rs.getTime("ENDTIME");
					//If classes are on the same day, check their times, if they conflict return true. 
				    if(userCourseDay.equals(day)) {
				    	if((userCourseStart.after(start) && userCourseStart.before(end)) || (userCourseEnd.after(start) && userCourseEnd.before(end))){
				    		return true;
				    	}
				    }
			}
				}
            catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
        return false;
    }
	
	
	public static void main(String[] args ){
		Scheduler scheduler = new Scheduler();
		Scanner scanner = new Scanner(System.in);
		String userInput = null;
		boolean validInput = false;
		boolean userValidated = false;
		final int maxCourse = 6;					// maximum courses allowed
		int[] userSchedule = new int[maxCourse];	// array: shopping cart for courses
		int courseCount = 0;						// keeps track of number of courses added

		String username = null;
		String firstName;
		String lastName;
		String password;
		
		// sets a loop so that users will only proceed if they type in register or login
		while(!validInput){
			System.out.println("Scheduler. Type 'register' or 'login': ");
			userInput = scanner.next();

			if (userInput.equals("register") || userInput.equals("login")){
				validInput = true;
			}
		}

		switch(userInput){
		
			//in the case of register, takes username, first name, last name, and password that user wants to set
			case("register"):
				System.out.println("Username: ");
				username = scanner.next();
				System.out.println("First Name: ");
				firstName = scanner.next();
				System.out.println("Last Name: ");
				lastName = scanner.next();
				System.out.println("Password: ");
				password = scanner.next();
			
				userValidated = scheduler.registerUser(username, firstName, lastName, password);
				break;
				
			// in the case of login - takes username and password of user. if valid- proceeds to the schedule compiler/checker
			case("login"):
				System.out.println("Username: ");
				username = scanner.next();
				System.out.println("Password: ");
				password = scanner.next();
				
				userValidated = scheduler.loginUser(username,password);
				break;
		}
		
//		Uservalidation will result in the user being led to the scheduling section 
		if (userValidated){
			String userFirstName = scheduler.getFName(username); //retrieve this from the users DB 
			System.out.println(userFirstName + " select a maximum of six classes from the list of classes and we will let you know if your schedule is valid");
			
//			List out the classes from the classes DB 
			scheduler.printAllCourses();
			
			
//			User's selection will go into userSchedule array
			String userChoice = null;	// user input
			int userCourseInt = 0;			// user's chosen course ID 
			boolean quit = false;		// quit the menu looping
			while (!quit) {				// menu
				System.out.println();
				System.out.println("=========================================");
				System.out.println("Please choose a course number to enroll");
				System.out.println("Or enter 'L' to list all available courses");
				System.out.println("Or enter 'M' to list courses you have added");
				System.out.println("Or enter 'C' to clear all my selected courses");
				System.out.println("Or enter 'Q' to quit: ");
				
				userChoice = scanner.next().toUpperCase();
				
				switch(userChoice) {
					case("Q"):
						System.out.println("Thank you and good bye, " + userFirstName + "!");
						quit = true;
						break;
					case("C"):
						courseCount = 0;
						System.out.println("Your schedule is now empty.");
						break;
					case("M"):
						System.out.println("Your current chosen course(s) are: ");
						scheduler.listMyCourses(userSchedule, courseCount);
						break;
					case("L"):
						scheduler.printAllCourses();
						break;
					default:
						// Check if user input complies with options: i.e. invalid non-numeric input, if passed, convert input to primitive int.
						try {
							userCourseInt = Integer.parseInt(userChoice);
						} catch (NumberFormatException e){
							System.out.println(userChoice + " is an invalid input!");
							break;
						}
						userCourseInt = Integer.parseInt(userChoice);		// get COURSE ID from user input, as primitive int type.
						
						// check if the chosen course exists
						if (!scheduler.courseExists(userCourseInt)) {
							System.out.println("The course ID # " + userChoice + " does not exist!");
							break;
						}
						
						// check if max allowed courses is reached
						if (courseCount >= userSchedule.length) {
							System.out.println("You have reached the maximum capacity of courses allowed! Course ID# " + userCourseInt + " is not registered. Your current schedule has no conflicts");
							break;
						}
						// check for duplicate courses
						else if (courseCount > 0) {
							boolean duplicate = false;
							for (int i = 0; i < courseCount; i++) {
								if (userCourseInt == userSchedule[i]) {
									duplicate = true;
									break;
								}
							}
							if (duplicate)	{		// duplicate found, break out of switch
								System.out.println("You have already selected course ID# " + userCourseInt + ".");
								break;
							}
						}
                        // check for conflicting times
                        if(scheduler.checkForConflicts(userSchedule,courseCount,userChoice)){
                            System.out.println("Course " + userChoice + " conflicts with current schedule.");
                            break;
                        }
                        
                        	// if no conflict, i.e. checkForConflicts() == true;
    						userSchedule[courseCount] = userCourseInt;
    						System.out.println("Course ID # " + userCourseInt + " registered successfully!");
    						courseCount++;
    						break;
                        
						
				}	// End of switch

			}//end while
		}//end if
		else
		{
			System.out.println("Wrong username or password, Good bye!");
			System.exit(0);
		}

		scanner.close();
		
	}

}