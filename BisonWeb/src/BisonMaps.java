import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*************************************************************************
 *  Name:		  Ranjay Salmon
 *  CourseNumber: SYCS 3601 -> Large Scale Programming
 *  Title:        Bison Maps Project
 *  Instructor:   DR.Legan Burge
 *  Compilation:  javac BisonMaps.java
 *  Execution:    java BisonMaps mapfile < input.txt
 *  Dependencies: EuclideanGraph.java Dijkstra.java In.java StdIn.java
 *
 *  Reads in a map from a file, and repeatedly reads in two integers s
 *  and d from standard input, and prints the shortest path from s
 *  to d to standard output.
 *
 ****************************************************************************/


public class BisonMaps {
	DataConnector instance;
	
	public static void Menu(DataConnector instance){
		Scanner in = new Scanner(System.in);
		int menuChoice = 0;
		do{
			try{
				System.out.println("*****************************************");
				System.out.println("*          MAIN MENU                    *");
				System.out.println("*****************************************");
				System.out.println("* 1.Get Directions                      *");
				System.out.println("* 2.Find Campus Resources               *");
				System.out.println("* 3.Display Campus Building Names       *");
				System.out.println("* 4.Display all campus Resources        *");
				System.out.println("* 5.Display all department Locations    *");
				System.out.println("* 6.Exit                                *");
				System.out.println("*****************************************");
				menuChoice = in.nextInt();
				
				switch(menuChoice){
				case 1:
					System.out.println("Succesful 1");
					getDirections(instance);
					break;
				case 2:
					System.out.println("Succesful 2");
					//findResources();
					break;
				case 3:
					System.out.println("Succesful 3");
					//displayAllBuildingNames();
					instance.showDepartments();
					break;
				case 4:
					System.out.println("Succesful 4");
					//displayAllResources();
					instance.showResources();
					break;
				case 5:
					System.out.println("Succesful 5");
					displayAllDepartmentLocations(instance);
					break;
				case 6:
					break;
					default :
						System.out.println("Invalid Entry try again");
						break;
				}
				
			}catch(InputMismatchException ex){
				System.out.println("Numbers ONLy");
				in.next();
				continue;
			}
			
		}while(menuChoice != 6);
		
	}
	public static void getDirections(DataConnector instance){
		Scanner in = new Scanner(System.in);
		ArrayList<Integer> SID = instance.getSID(); // SID from Graph table
		ArrayList<Integer> DID = instance.getDID(); // DID from graph table
		
		ArrayList<Integer> NLSID = instance.getNLSID(); // SID from NodeLocation 
		ArrayList<Integer> NLX = instance.getNLX();     // X value from NodeLocation
		ArrayList<Integer> NLY = instance.getNLY();     //Y value from NodeLocation
		ArrayList<Integer> DP_SID = instance.getDP_SID(); // SID from Department table
		ArrayList<String> DepartmentName = instance.getDepartName();// Department name from Department Table
		ArrayList<String> ndDescription = instance.getDescription(); // Stores all the Department names from the Node Description table
		ArrayList<Integer> ndSID = instance.getND_SID();            // Stores the SID from the Node Description table
		
		
		
		
		EuclideanGraph G = new EuclideanGraph(SID,DID,NLSID,NLX,NLY);
        System.err.println("Done reading the graph " + SID.size());
        System.err.println("Enter query pairs from stdin");

        // read in the s-d pairs from standard input
        Dijkstra dijkstra = new Dijkstra(G);
        int cont = -1;
        do{
        	
        	try{
        		System.out.println("Enter start Location ID:_");
        		int s = in.nextInt();
        		System.out.println("Enter end Location ID:_");
                int d = in.nextInt();
                switch(s){
                case 1 : case 3 :case 6:case 8:case 9:case 15:case 17:case 16:case 20:case 23:case 32:case 34:case 38:case 42:case 43:
                case 45:case 49:case 52:case 54:case 59:case 57:case 66:case 67:case 71:case 78:case 72: case 75: case 80:
                case 84: case 100: case 101: case 105:case 108: case 96: case 93: case 116: case 120:case 125: case 126:case 129:
                case 133:case 88:case 89:case 92:case 97:case 0:
                	switch(d){
                	case 1 : case 3 :case 6:case 8:case 9:case 15:case 17:case 16:case 20:case 23:case 32:case 34:case 38:case 42:case 43:
                    case 45:case 49:case 52:case 54:case 59:case 57:case 66:case 67:case 71:case 78:case 72: case 75: case 80:
                    case 84: case 100: case 101: case 105:case 108: case 96: case 93: case 116: case 120:case 125: case 126:case 129:
                    case 133:case 88:case 89:case 92:case 97:case 0:
                    	//dijkstra.showPath(s, d,DP_SID,DepartmentName);
                    	dijkstra.showPath(s, d,ndSID,ndDescription);
                    	//dijkstra.showPath(s, d);
                    	break;
                    default:
                    		System.out.println("Invalid input try again");
                    		break;
                	}
                	break;
                default:
                	System.out.println("Invalid input try again");
                	break;
                }
                //dijkstra.showPath(s, d);
                System.out.println();
                System.out.println("Enter -1 to continue");
                cont = in.nextInt();
        	}catch(InputMismatchException ex){
        		System.out.println("Numbers ONLy");
				in.next();
        	}
        	
        }while (cont == -1);
        
	}
	
	public static void displayAllDepartmentLocations(DataConnector instance){
		ArrayList<String> departments = instance.getDepartName();
		System.out.println("Department"+"\t"+"\t"+"\t"+"Building");
		
		System.out.println("Computer Science: "+"\t"+"\t"+departments.get(22));
		System.out.println("Chemistry: "+"\t"+"\t"+"\t"+departments.get(23));
		System.out.println("College of Dentistry: "+"\t"+"\t"+departments.get(1));
		System.out.println("School of Medicine: "+"\t"+"\t"+departments.get(2));
		System.out.println("School of Architecture: "+"\t"+departments.get(27));
		System.out.println("Busines School: "+"\t"+"\t"+departments.get(37));
		System.out.println("School of Education: "+"\t"+"\t"+departments.get(43));
		System.out.println("TV Station: "+"\t"+"\t"+"\t"+departments.get(12));
		System.out.println("Physics : "+"\t"+"\t"+"\t"+departments.get(0));
		System.out.println("School of Communication: "+"\t"+departments.get(9));
		System.out.println("School of Nursing: "+"\t"+"\t"+departments.get(4));
		System.out.println("Health Sciences: "+"\t"+"\t"+departments.get(11));
		System.out.println("Math Department: "+"\t"+"\t"+departments.get(4));
		
		
		
		
	}

    public static void main(String[] args) {
    	DataConnector newInstance = DataConnector.getInstance();
		newInstance.getGraphData();
		newInstance.getNodeLocationData();
		newInstance.displayAllBuildingNames();
		newInstance.getNodeDescription();
		newInstance.displayAllResources();
    	Menu(newInstance);
    	/*
        // read in the graph from a file
        In graphin = new In(args[0]);
        EuclideanGraph G = new EuclideanGraph(graphin);
        System.err.println("Done reading the graph " + args[0]);
        System.err.println("Enter query pairs from stdin");

        // read in the s-d pairs from standard input
        Dijkstra dijkstra = new Dijkstra(G);
        while(!StdIn.isEmpty()) {
            int s = StdIn.readInt();
            int d = StdIn.readInt();
            dijkstra.showPath(s, d);
            System.out.println();
        } */
    }
}
