import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
	public static void main(String[] args)	
	{
		
		// Create a FlightManager object
		FlightManager manager = null;
		try
		{
			manager = new FlightManager();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		

		// List of reservations that have been made
		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations
		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) continue;

			// The command line is a scanner that scans the inputLine string
			// For example: list AC201
			Scanner commandLine = new Scanner(inputLine);
			
			// The action string is the command to be performed (e.g. list, cancel etc)
			String action = commandLine.next();

			if (action == null || action.equals("")) continue;

			if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
			{
				commandLine.close();
				return;
			}
			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			else if (action.equalsIgnoreCase("RES"))
			{
				// try res method, and print error message if checked excpetion is thrown
				try 
				{
					FlightReservationSystemMethods.res(commandLine, manager, myReservations);
				}
				catch (FlightNotFoundException | FlightNotLongHaulException | FlightFullException | InvalidCommandException | DuplicatePassengerException | SeatDoesNotExistException e) 
				{
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("SEATS"))
			{
				// try seats method, and print error message if checked excpetion is thrown
				try
				{
					FlightReservationSystemMethods.seats(commandLine, manager);
				}
				catch (FlightNotFoundException | FlightFullException | InvalidCommandException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("CANCEL"))
			{
				// try cancel method, and print error message if checked excpetion is thrown
				try
				{
					FlightReservationSystemMethods.cancel(commandLine, manager, myReservations);
				}
				catch (FlightNotFoundException | ReservationNotFoundException | InvalidCommandException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("MYRES"))
			{
				FlightReservationSystemMethods.myres(myReservations);
			}
			else if (action.equalsIgnoreCase("CRAFT"))
		 	{
			  manager.printAllAircraft();
			}
			else if (action.equalsIgnoreCase("PASMAN"))
			{
				// try psngrs method, and print error message if checked excpetion is thrown
				try
				{
					FlightReservationSystemMethods.pasman(commandLine, manager);
				}
				catch (FlightNotFoundException | InvalidCommandException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PREBOARD"))
			{
				try
				{
					FlightReservationSystemMethods.preboard(commandLine, manager);
				}
				catch (FlightNotFoundException | InvalidCommandException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("QUEUE"))
			{
				try
				{
					FlightReservationSystemMethods.queue(commandLine, manager);
				}
				catch (FlightNotFoundException | InvalidCommandException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("BOARD"))
			{
				try
				{
					FlightReservationSystemMethods.board(commandLine, manager);
				}
				catch (FlightNotFoundException | InvalidCommandException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else
			{
				// if action does not match any of the commands, print that it is an invalid command.
				System.out.println("Invalid Command");
			}
	  
			System.out.print("\n>");
		}
	}
}




