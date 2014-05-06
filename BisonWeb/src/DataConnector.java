import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

/*************************************************************************
 *  Dijkstra's algorithm.
 *  Name:		  Ranjay Salmon
 *  CourseNumber: SYCS 3601 -> Large Scale Programming
 *  Title:        Bison Maps Project
 *  Instructor:   DR.Legan Burge
 *  Compilation:  javac DataConnector.java
 *  Execution:    java DataConnector
 *  Dependencies: EuclideanGraph.java
 *  Test Case:    Main Function at the end of class
 *  
 *  this class does the job of connecting to the database and 
 *  allowing the user to query the database
 *  and stores its results into arraylist so the user does not have to constantly querry 
 *  the DB 
 *  
 *  Test function main established at the end to test the connection
 *
 *************************************************************************/
public class DataConnector {
	
	private static DataConnector firstInstance = null;
	
	private Connection con;// Object which does the connection to to database
	private Statement st; // Object used to execute queries
	private ResultSet rs; // object that holds the restults of each query 
	
	/**
	 * Arraylist used to store data received from th database
	 */
	private ArrayList<Integer> NLSID = new ArrayList<Integer>(); // Array list storing SID from Node Layer table
	private ArrayList<Integer> NLX = new ArrayList<Integer>();
	private ArrayList<Integer> NLY = new ArrayList<Integer>();
	private ArrayList<Integer> GSID = new ArrayList<Integer>();
	private ArrayList<Integer> GDID = new ArrayList<Integer>();
	private ArrayList<String> DepartmentName = new ArrayList<String>(); // Array list storing Department Name from departmentName table
	private ArrayList<Integer> DPNSID = new ArrayList<Integer>();
	private ArrayList<String>DpName = new ArrayList<String>();
	private ArrayList<Integer> ATM = new ArrayList<Integer>();
	private ArrayList<Integer> Vending = new ArrayList<Integer>();
	private ArrayList<Integer> RestRoom = new ArrayList<Integer>();
	private ArrayList<Integer> PublicPhone = new ArrayList<Integer>();
	private ArrayList<Integer> ComputerLab = new ArrayList<Integer>();
	private ArrayList<Integer> WiFi = new ArrayList<Integer>();
	private ArrayList<Integer> Security = new ArrayList<Integer>();
	private ArrayList<Integer> BusStop = new ArrayList<Integer>();
	private ArrayList<String> ND_Description = new ArrayList<String>();
	private ArrayList<Integer> ND_SID = new ArrayList<Integer>();
	
	//private Properties props; 
	
	
	/***
	 * Singleton class used  to make connection to database 
	 */
	private DataConnector(){}
	/***
	 * getinstance method to ensure that only one object of the class is ever instantiated
	 * @return
	 */
	public static DataConnector getInstance(){
		
		if(firstInstance == null){
			firstInstance = new DataConnector();
			
			try{
				
				Class.forName("org.postgresql.Driver");
				
				String url = "jdbc:postgresql://ec2-54-204-31-13.compute-1.amazonaws.com:5432/d9lseimja9c1dm";  
				
				Properties props = new Properties(); 
				props.setProperty("user", "cbeiyqcwpnzybi"); 
				props.setProperty("password", "mvNeBYNvNd4nazKttPdj7uXYsD"); 
				props.setProperty("ssl", "true");
				props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
				
				
				//firstInstance.con = DriverManager.getConnection("jdbc:postgresql://ec2-54-204-31-13.compute-1.amazonaws.com:5432/d9lseimja9c1dm","cbeiyqcwpnzybi","mvNeBYNvNd4nazKttPdj7uXYsD");
				firstInstance.con = DriverManager.getConnection(url,props); 
				firstInstance.st = firstInstance.con.createStatement();
				
			}catch(Exception ex){
				System.out.println("Error: "+ex);
				ex.printStackTrace();
			}
		}
		return firstInstance;
	}
	
	public void getGraphData(){
		try{
			
			String query = "select * from \"Graph\" ";
			firstInstance.rs = firstInstance.st.executeQuery(query);
			System.out.println("REcords from database: ");
			
			while (firstInstance.rs.next()){
				//String SID = firstInstance.rs.getString("SID"); // name of column in database goes insside getString() method
				//String DID = firstInstance.rs.getString("DID");
				
				int SID = Integer.parseInt(firstInstance.rs.getString("SID"));
				int DID = Integer.parseInt(firstInstance.rs.getString("DID"));
				firstInstance.GSID.add(SID);
				firstInstance.GDID.add(DID);
				
				
				//System.out.println("SID: "+SID+" "+"DID: "+DID);
			}
			
		}catch(Exception ex){
			System.out.println("Exception in get Function: "+ex);
		}
		
	}
	
	public void getNodeLocationData(){
		try{
			
			String query = "select * from \"NodeLocation\" ";
			firstInstance.rs = firstInstance.st.executeQuery(query);
			System.out.println("REcords from NodeLocation: ");
			
			while (firstInstance.rs.next()){
		
				
				int NSID = Integer.parseInt(firstInstance.rs.getString("SID"));
				int X = Integer.parseInt(firstInstance.rs.getString("X"));
				int Y = Integer.parseInt(firstInstance.rs.getString("Y"));
				firstInstance.NLSID.add(NSID);
				firstInstance.NLX.add(X);
				firstInstance.NLY.add(Y);
				
				
				//System.out.println("SID: "+SID+" "+"DID: "+DID);
			}
			
		}catch(Exception ex){
			System.out.println("Exception in get Function: "+ex);
		}
		
	}
	
	public void displayAllBuildingNames(){
		try{
			
			String query = "select * from \"DepartmentLocation\" ";
			firstInstance.rs = firstInstance.st.executeQuery(query);
			System.out.println("REcords from DepartmentLocation: ");
			
			while (firstInstance.rs.next()){
				int dpnsid = Integer.parseInt(firstInstance.rs.getString("DP_SID"));
				String Name = firstInstance.rs.getString("DepartmentName");
				firstInstance.DPNSID.add(dpnsid);
				firstInstance.DepartmentName.add(Name);
				
				//System.out.println("Departments: "+Name);
			}
			
		}catch(Exception ex){
			System.out.println("Exception in get Function: "+ex);
		}
		
	}
	public void getNodeDescription(){
		try{
			
			String query = "select * from \"NodeDescription\" ";
			firstInstance.rs = firstInstance.st.executeQuery(query);
			System.out.println("REcords from DepartmentLocation: ");
			
			while (firstInstance.rs.next()){
				int ndsid = Integer.parseInt(firstInstance.rs.getString("NodeDescription_SID"));
				String Name = firstInstance.rs.getString("Name");
				firstInstance.ND_SID.add(ndsid);
				firstInstance.ND_Description.add(Name);
				
				//System.out.println("Departments: "+Name);
			}
			
		}catch(Exception ex){
			System.out.println("Exception in get Function: "+ex);
		}
		
	}
	public void displayAllResources(){
		try{
			
			
			
			String query = "select \"DepartmentLocation\".\"DepartmentName\",\"NodeLayer\".\"ATM\",\"NodeLayer\".\"Vending\","+
					" \"NodeLayer\".\"RestRoom\",\"NodeLayer\".\"PublicPhone\",\"NodeLayer\".\"ComputerLab\",\"NodeLayer\".\"WiFi\","+
					"\"NodeLayer\".\"Security\",\"NodeLayer\".\"BusStop\""+
					"from \"NodeLayer\" "+
					"inner join \"DepartmentLocation\" on \"DepartmentLocation\".\"DP_SID\" = \"NodeLayer\".\"NLay_SID\" ";

			
			
			firstInstance.rs = firstInstance.st.executeQuery(query);
			System.out.println("REcords from database: ");
			
			while (firstInstance.rs.next()){
				//String SID = firstInstance.rs.getString("SID"); // name of column in database goes insside getString() method
				//String DID = firstInstance.rs.getString("DID");
				
				String dpName = firstInstance.rs.getString("DepartmentName");
				int atm = Integer.parseInt(firstInstance.rs.getString("ATM"));
				int vending = Integer.parseInt(firstInstance.rs.getString("Vending"));
				int restroom = Integer.parseInt(firstInstance.rs.getString("RestRoom"));
				int phone = Integer.parseInt(firstInstance.rs.getString("PublicPhone"));
				int computerlab = Integer.parseInt(firstInstance.rs.getString("ComputerLab"));
				int wifi = Integer.parseInt(firstInstance.rs.getString("WiFi"));
				int security = Integer.parseInt(firstInstance.rs.getString("Security"));
				int busstop = Integer.parseInt(firstInstance.rs.getString("BusStop"));
				firstInstance.DpName.add(dpName);
				firstInstance.ATM.add(atm);
				firstInstance.Vending.add(vending);
				firstInstance.RestRoom.add(restroom);
				firstInstance.PublicPhone.add(phone);
				firstInstance.ComputerLab.add(computerlab);
				firstInstance.WiFi.add(wifi);
				firstInstance.Security.add(security);
				firstInstance.BusStop.add(busstop);
				
				//System.out.println(dpName+" "+atm+" "+vending+" "+restroom+" "+phone+" "+wifi+" "+security+" "+busstop);
			}
			
		}catch(Exception ex){
			System.out.println("Exception in get Function: "+ex);
		}
		
		try {
			firstInstance.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}	
	
	public void showDepartments(){
		for (int i=0; i< DepartmentName.size();i++){
			System.out.println(DPNSID.get(i)+"."+DepartmentName.get(i));
		}
	}
	
	public void showResources(){
		System.out.println("Building"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"ATM"+"\t"+"Vending Machine"+"\t"+"Restroom"+"\t"+"Phone"+"\t"+"Computer Lab"+"\t"+"wifi"+"\t"+"Security"+"\t"+"Bus Stop");
		for (int i=0; i< DpName.size();i++){
			
			System.out.println(DpName.get(i)+" "+ATM.get(i)+" "+Vending.get(i)+" "+RestRoom.get(i)+" "+PublicPhone.get(i)+" "+
			ComputerLab.get(i)+" "+WiFi.get(i)+" "+Security.get(i)+" "+BusStop.get(i));
		}
	}
	/**
	 * getters that return the value stored in each ArrayList
	 * @return
	 */

	public ArrayList<Integer> getSID(){
		return firstInstance.GSID;
	}
	
	public ArrayList<Integer> getDID(){
		return firstInstance.GDID;
	}
	
	public ArrayList<Integer> getNLSID(){
		return firstInstance.NLSID;
	}
	
	public ArrayList<Integer> getNLX(){
		return firstInstance.NLX;
	}
	
	public ArrayList<Integer> getNLY(){
		return firstInstance.NLY;
	}
	
	public ArrayList<String> getDepartName(){
		return firstInstance.DepartmentName;
	}
	public ArrayList<String> getDPName(){
		return firstInstance.DpName;
	}
	public ArrayList<Integer> getATM(){
		return firstInstance.ATM;
	}
	public ArrayList<Integer> getVending(){
		return firstInstance.Vending;
	}
	public ArrayList<Integer> getRestRoom(){
		return firstInstance.RestRoom;
	}
	public ArrayList<Integer> getPublicPhone(){
		return firstInstance.PublicPhone;
	}
	public ArrayList<Integer> getComputerLab(){
		return firstInstance.ComputerLab;
	}
	public ArrayList<Integer> getWiFi(){
		return firstInstance.WiFi;
	}
	public ArrayList<Integer> getSecurity(){
		return firstInstance.Security;
	}
	public ArrayList<Integer> getBusStop(){
		return firstInstance.BusStop;
	}
	
	public ArrayList<Integer> getDP_SID(){
		return firstInstance.DPNSID;
	}
	
	public ArrayList<Integer> getND_SID(){
		return firstInstance.ND_SID;
	}
	
	public ArrayList<String> getDescription(){
		return firstInstance.ND_Description;
	}
	public void Menu(){
		Scanner in = new Scanner(System.in);
		int menuChoice = 0;
		do{
			System.out.println("1.Get Directions");
			System.out.println("2.Find Campus Resources");
			System.out.println("3.Display Campus Building Names");
			System.out.println("4.Display all campus Resources");
			System.out.println("5.Display all department Locations");
			System.out.println("6.Exit");
			menuChoice = in.nextInt();
			
			switch(menuChoice){
			case 1:
				getDirections();
				break;
			case 2:
				//findResources();
				break;
			case 3:
				displayAllBuildingNames();
				break;
			case 4:
				//displayAllResources();
				break;
			case 5:
				//displayAllDepartmentLocations();
				break;
			case 6:
				break;
				default :
					break;
			}
			
		}while(menuChoice != 6);
	}
	
	public void getDirections(){
		
		EuclideanGraph G = new EuclideanGraph(GSID,GDID,NLSID,NLX,NLY);
        System.err.println("Done reading the graph " + GSID.size());
        System.err.println("Enter query pairs from stdin");

        // read in the s-d pairs from standard input
        Dijkstra dijkstra = new Dijkstra(G);
        while(!StdIn.isEmpty()) {
            int s = StdIn.readInt();
            int d = StdIn.readInt();
            dijkstra.showPath(s, d);
            System.out.println();
        }
	}
	
	/***
	 * Main Function used to test to ensure the connection was succesful and values were retrieved correctly
	 * @param args
	 */
	public static void main(String[] args){
		DataConnector newInstance = DataConnector.getInstance();
		newInstance.getGraphData();
		newInstance.getNodeLocationData();
		newInstance.displayAllBuildingNames();
		newInstance.getNodeDescription();
		newInstance.displayAllResources();
		
		ArrayList<Integer> SID = newInstance.getSID();
		ArrayList<Integer> DID = newInstance.getDID();
		
		ArrayList<Integer> NLSID = newInstance.getNLSID();
		ArrayList<Integer> NLX = newInstance.getND_SID();
		ArrayList<String> NLY = newInstance.getDescription();
		
		for(int i=0; i< NLY.size(); i++){
			System.out.println("SID: "+NLY.get(i)+" "+NLX.get(i));
		}
		
		
		/*
		EuclideanGraph G = new EuclideanGraph(SID,DID,NLSID,NLX,NLY);
        System.err.println("Done reading the graph " + SID.size());
        System.err.println("Enter query pairs from stdin");

        // read in the s-d pairs from standard input
        Dijkstra dijkstra = new Dijkstra(G);
        while(!StdIn.isEmpty()) {
            int s = StdIn.readInt();
            int d = StdIn.readInt();
            dijkstra.showPath(s, d);
            System.out.println();
        }*/
		
		
	}

}
