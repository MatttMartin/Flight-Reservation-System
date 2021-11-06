import java.util.ArrayList;
import java.util.Scanner;

public class FlightReservationSystemMethods
{
    
    /** 
     * This method reserves a seat on the flight that matches the flight number given in the scanner input,
     * and adds the reservation to the list of reservations.
     * @param commandLine
     * @param manager
     * @param myReservations
     * @throws DuplicatePassengerException
     * @throws SeatDoesNotExistException
     */
    public static void res(Scanner commandLine, FlightManager manager, ArrayList<Reservation> myReservations) throws FlightNotFoundException, FlightFullException, FlightNotLongHaulException, InvalidCommandException, DuplicatePassengerException, SeatDoesNotExistException
    {
        ArrayList<String> inputStrings = organizeInput(commandLine);
        if (inputStrings.size() != 4)
        {
            throw new InvalidCommandException("Should be formatted as res flightNum Name PassportNum seatNum");
        }
        String flightNum = inputStrings.get(0);
        String name = inputStrings.get(1);
        String passportNum = inputStrings.get(2);
        String seatNum = inputStrings.get(3);

        Passenger passenger = new Passenger(name, passportNum, seatNum); //create a passenger object with given info

        Reservation res = manager.reserveSeatOnFlight(passenger, flightNum); //reserve seat on the flight with newly made passenger
        myReservations.add(res);
        res.print(); 
    }

    private static ArrayList<String> organizeInput(Scanner commandLine)
    {
        ArrayList<String> inputStrings = new ArrayList<String>();
        while (commandLine.hasNext())
        {
            inputStrings.add(commandLine.next());
        }
        return inputStrings;
    }

      
    /** 
     * This method prints whether seats are available on the flight that matches the 
     * flight number inputed in the scanner.
     * @param commandLine
     * @param manager
     */
    public static void seats(Scanner commandLine, FlightManager manager) throws FlightNotFoundException, FlightFullException, InvalidCommandException
    {
        if (commandLine.hasNext())
        {
            String flightNum = commandLine.next();
            manager.printSeats(flightNum);
        }
        else 
        {
            throw new InvalidCommandException("Invalid Command: Need Flight Number");
        }
    }
    
    
    /**
     * This method cancels a reservation and removes it from the reservation list for the flight that
     * matches the flight number inputed in the scanner.
     * Note: this method only cancels reservations that have a default passenger. 
     * @param commandLine
     * @param manager
     * @param myReservations
     */
    public static void cancel(Scanner commandLine, FlightManager manager, ArrayList<Reservation> myReservations) throws FlightNotFoundException, ReservationNotFoundException, InvalidCommandException
    {
        ArrayList<String> inputStrings = organizeInput(commandLine);
        if (inputStrings.size() != 3)
        {
            throw new InvalidCommandException("Should be formatted as: cancel flightNum Name PassportNum");
        }
        String flightNum = inputStrings.get(0);
        String name = inputStrings.get(1);
        String passportNum = inputStrings.get(2);

        Reservation res = findReservation(myReservations, flightNum, name, passportNum);
        manager.cancelReservation(res);
        myReservations.remove(res);
    } 
    
    
    /** 
     * This method prints the information for every reservation in the reservation list.
     * @param myReservations
     */
    public static void myres(ArrayList<Reservation> myReservations)
    {
        for (int i = 0; i < myReservations.size(); i++)
        {
            myReservations.get(i).print(); //call each reservation's print() method.
        }
    } 

    
    /** 
     * This method prints the information of the passenger associated with each reservation for the flight
     * that matches the flight number inputed in the scanner.
     * @param commandLine
     * @param manager
     */
    public static void pasman(Scanner commandLine, FlightManager manager) throws FlightNotFoundException, InvalidCommandException
    {
        if (commandLine.hasNext())
        {
            String flightNum = commandLine.next();  
            manager.printAllPassengers(flightNum); //print all of the passengers
        }
        else 
        {
            throw new InvalidCommandException("Invalid Command: Need Flight Number");
        }
        
    } 


    public static void preboard(Scanner commandLine, FlightManager manager) throws FlightNotFoundException, InvalidCommandException
    {
        if (commandLine.hasNext())
        {
            String flightNum = commandLine.next();  
            manager.preboardFlight(flightNum); 
        }
        else 
        {
            throw new InvalidCommandException("Invalid Command: Need Flight Number");
        }
    }


    public static void queue(Scanner commandLine, FlightManager manager) throws FlightNotFoundException, InvalidCommandException
    {
        if (commandLine.hasNext())
        {
            String flightNum = commandLine.next();  
            manager.printQueue(flightNum); 
        }
        else 
        {
            throw new InvalidCommandException("Invalid Command: Need Flight Number");
        }
    }


    public static void board(Scanner commandLine, FlightManager manager) throws FlightNotFoundException, InvalidCommandException
    {
        ArrayList<String> inputStrings = organizeInput(commandLine);
        if (inputStrings.size() != 3)
        {
            throw new InvalidCommandException("Should be formatted as board flightNum startRow endRow");
        }
        String flightNum = inputStrings.get(0);
        char startRow = inputStrings.get(1).charAt(0);
        char endRow = inputStrings.get(2).charAt(0);

        manager.boardFlight(flightNum, startRow, endRow);
    }    

    
    /** 
     * This method searches the list of reservations and returns the reservation that matches the info given in the parameters.
     * @param myReservations
     * @param flightNum
     * @param name
     * @param passportNum
     * @return Reservation
     */
    public static Reservation findReservation(ArrayList<Reservation> myReservations, String flightNum, String name, String passportNum) throws ReservationNotFoundException
	{
        //loops through all reservations in myReservations, then if one matches all of the parameters, return it
		for (int i = 0; i < myReservations.size(); i++)
		{
			Reservation res = myReservations.get(i);
			if (flightNum.equals(res.flightNum) && name.equals(res.passengerName) && passportNum.equals(res.passengerPassport))
			{
				return res;
			}
		}
        //if none of them match, throw a ReservationNotFoundException
        throw new ReservationNotFoundException("Reservation Not Found");	
	}

}