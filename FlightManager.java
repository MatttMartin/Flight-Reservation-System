import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;

//import jdk.jfr.events.FileReadEvent;



public class FlightManager
{
  // Contains list of Flights departing from Toronto in a single day
	//Map<String, Flight> flights = new TreeMap<String, Flight>(new FlightMapValueDurationComparator());
  Map<String, Flight> flights = new TreeMap<String, Flight>();
  
  String[] cities = 	{"Dallas", "New York", "London", "Paris", "Tokyo"};
  final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;
  
  // flight times in hours
  int[] flightTimes = { 3, // Dallas
  											1, // New York
  											7, // London
  											8, // Paris
  											16// Tokyo
  										};
  
  // Contains list of available airplane types and their seat capacity
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
    
  Random random = new Random(); // random number generator - google "Java class Random". Use this in generateFlightNumber
  
  ArrayList<Integer> usedCodes = new ArrayList<Integer>();
  
  public FlightManager() throws FileNotFoundException
  {  	
  	airplanes.add(new Aircraft(52,"Airbus 320"));
    airplanes.add(new Aircraft(84, "Boeing 737"));
  	airplanes.add(new Aircraft(28, "Dash-8 100"));
  	airplanes.add(new Aircraft(4, 2, "Bombardier 5000"));
  	airplanes.add(new Aircraft(90, 14, "Boeing 747"));

    populateFlightMap(this.flights, this.airplanes, "flights.txt");
  }
  
  private class FlightMapValueDurationComparator implements Comparator<String>
  {
    public int compare(String flightNumA, String flightNumB)
    {
      System.out.println("in compare method for flight map");
      try{
        return findFlight(flightNumA).flightDuration - findFlight(flightNumB).flightDuration;
      } catch (FlightNotFoundException e) {
        return 0;
      }
    }
  }


  /**
   * Fills the flightList with flights from the file given. Uses the smallest aircraft that can accomodate the seats needed.
   * @param flightList
   * @param aircraftList
   * @param fileName
   * @throws FileNotFoundException
   */
  private void populateFlightMap(Map<String, Flight> flightMap, ArrayList<Aircraft> aircraftList, String fileName) throws FileNotFoundException
  {
    File file = new File(fileName);
    Scanner in = new Scanner(file);
    while (in.hasNextLine())
    {
      String line = in.nextLine();
      String[] strings = line.split(" ");
      String airline = strings[0].replace("_", " ");
      String flightNum = generateFlightNumber(airline);
      String destination = strings[1].replace("_", " ");
      String departure = strings[2];
      String seatAmount = strings[3];
      Aircraft aircraft = getBestAircraft(seatAmount, aircraftList);
      int duration = getDuration(destination);
      boolean longHaul = aircraft.numFirstClassSeats == 0 ? false : true; //if there are first class seats then the flight should be long haul
      Flight flight = generateFlight(longHaul, flightNum, airline, destination, departure, duration, aircraft);
      flightMap.put(flightNum, flight);
    }
    in.close();
  }


  /**
   * generates a Flight or a LongHaulFlight depending on the boolean longHaul
   * @param longHaul
   * @param flightNum
   * @param airline
   * @param destination
   * @param departure
   * @param duration
   * @param aircraft
   * @return Flight or LongHaulFlight that was created
   */
  public Flight generateFlight(boolean longHaul, String flightNum, String airline, String destination, String departure, int duration, Aircraft aircraft)
  {
    if (longHaul)
    {
      return new LongHaulFlight(flightNum, airline, destination, departure, duration, aircraft);
    }
    else
    {
      return new Flight(flightNum, airline, destination, departure, duration, aircraft);
    }
  }


/**
 * Gets the duration for the flight based off of the destination
 * @param destination
 * @return int duration of flight
 * @throws FlightCreationErrorException
 */
  private int getDuration(String destination) throws FlightCreationErrorException
  {
    for (int i = 0; i < cities.length; i++)
    {
      if (destination.equals(cities[i]))
      {
        return flightTimes[i];
      }
    }
    throw new FlightCreationErrorException("City " + destination + " not found");
  }


  /**
   * Finds the smallest aircraft from the aircraft list that can accomodate the number of seats needed for the flight.
   * @param seatAmount
   * @param aircraftList
   * @return
   */
  private Aircraft getBestAircraft(String seatAmount, ArrayList<Aircraft> aircraftList)
  {
    int seatAmountInt = Integer.parseInt(seatAmount);
    Collections.sort(aircraftList); //sorts the aircrafts by number total seats
    Aircraft bestAircraft = aircraftList.get(aircraftList.size() - 1); //sets the starting best one to the one with the most seats (which should accomodate every flight)
    for (int i = 0; i < aircraftList.size() - 1; i++)
    {
      Aircraft current = aircraftList.get(i);
      if (current.getTotalSeats() < bestAircraft.getTotalSeats() && current.getTotalSeats() >= seatAmountInt)
      {
        bestAircraft = current;
      }
    }

    if (bestAircraft.getTotalSeats() >= seatAmountInt)
    {
      return bestAircraft;
    }
    else
    {
      throw new FlightCreationErrorException("No Compatible Aircrafts For Flight with " + seatAmount + " seats");
    }
  }



  /**
   * This private helper method generates and returns a flight number string from the airline name parameter
   * For example, if parameter string airline is "Air Canada" the flight number should be "ACxxx" where xxx is 
   * a random 3 digit number between 101 and 300 (Hint: use class Random - see variable random at top of class)
   * you can assume every airline name is always 2 words. 
   * @param airline
   * @return String
   */
  private String generateFlightNumber(String airline)
  {
    String flightNum = "";
    String[] split_airline = airline.split(" "); //splits airline into an array of two strings

    //adds the first letter of each word of the airline to the flightNum
    flightNum += split_airline[0].charAt(0);
    flightNum += split_airline[1].charAt(0);


    //finds a code that has not been used yet
    boolean found = false;
    int code = 0;
    while (!found) //loops through and tries random numbers until one is not in the usedCodes list (this ensures codes are not reused)
    {
      code = random.nextInt(300 - 101) + 101;
      if (!codeUsed(code))
      {
        found = true;
      }
    }

    usedCodes.add(code); //adds the code to the usedCodes list so that it is not used in other flightNums
    flightNum += Integer.toString(code); //appends the code onto the flightNum 
  	return flightNum;
  }



  
  /** 
   * Checks if three digit code has already been used by another flightNum
   * @param code
   * @return boolean
   */
  private boolean codeUsed(int code)
  {
    for (int i = 0; i < usedCodes.size(); i++) //loops through every code in the list
    {
      if (code == usedCodes.get(i)) //if the code is found return true
      {
        return true;
      }
    }
    return false; //otherwise return false
  }

  /**
   *  Prints all flights in flights array list (see class Flight toString() method) 
  */
  public void printAllFlights()
  {
    for (String key : flights.keySet())
    {
      Flight flight = flights.get(key);
      System.out.println(flight.toString());
    }

  }

  
  
  /** 
   * Given a flight number (e.g. "UA220"), check to see if there are economy seats available
   * if so return true, if not return false
   * @param flightNum
   * @return boolean true if seats available, false otherwise.
   */
  public boolean seatsAvailable(String flightNum) throws FlightNotFoundException
  { 
    Flight flight = findFlight(flightNum); //finds the flight
    return flight.seatsAvailable(); //returns whether seats are available on the flight we found
  }
  
  
  /** 
   * Creates and returns the reference to a reservation object for the passenger given.
   * @param passenger
   * @param flightNum
   * @return Reservation
   * @throws SeatDoesNotExistException
   */
  public Reservation reserveSeatOnFlight(Passenger passenger, String flightNum) throws FlightNotFoundException, FlightFullException, DuplicatePassengerException, SeatDoesNotExistException
  {
    Flight flight = findFlight(flightNum); //finds the flight that matches the parameters given
    flight.reserveSeat(passenger); // reserves a seat for the passenger given
    return new Reservation(flightNum, flight.toString(), passenger.getName(), passenger.getPassportNum(), passenger.getSeatNum()); //returns a reservation for the passenger on the flight we found
  }
 
  
  /** 
   * Given a reservation object, this method finds the flight corresponding to the reservation,
   * then cancels the seat on that flight.
   * @param res
   */
  public void cancelReservation(Reservation res) throws FlightNotFoundException
  {
    String flightNum = res.flightNum; 
    Flight flight = findFlight(flightNum);
    flight.cancelSeat(res);
  }

  
  /** 
  * Prints all aircraft in airplanes array list. 
  * See class Aircraft for a print() method
  */
  public void printAllAircraft()
  {
  	for (int i = 0; i < airplanes.size(); i++)
    {
      airplanes.get(i).print();
    }
  }

  
  /** 
   * Prints information of all passenger objects on the flight that matches the flightNum parameter.
   * Note: if a passenger has more than one ticket on the flight, they are considered two different passenger objects because they have two different seatNums.
   * @param flightNum
   */
  public void printAllPassengers(String flightNum) throws FlightNotFoundException
  { 
    Flight flight = findFlight(flightNum);

    for (int i = 0; i < flight.manifest.size(); i++) //loops through each passenger on the flight and prints each one
    {
      System.out.println(flight.manifest.get(i));
    }
  }
  

  
  /** 
   * Searches list of flights for the flight that matches the flightNum parameter.
   * @param flightNum
   * @return Flight object if flight is found.
   */
  public Flight findFlight(String flightNum) throws FlightNotFoundException
  {
    Flight flight = flights.get(flightNum);
    if (flight == null)
    {
      throw new FlightNotFoundException("Flight " + flightNum + " Not Found"); //if the flight isnt found, throw a FlightNotFoundException
    }
    return flight;
  }

  /**
   * Print the whole seat layout of the flight which matches the flightNum given. Occupied seats will be represented with "XX",
   * and first class seats are shown with a '+' after.
   * @param flightNum
   * @throws FlightNotFoundException
   */
  public void printSeats(String flightNum) throws FlightNotFoundException
  {
    Flight flight = findFlight(flightNum);
    String[][] layout = flight.aircraft.layout;

    for (int i = 0; i < layout.length; i++)
	  {
      for (int j = 0; j < layout[i].length; j++)
      {
        String current = layout[i][j];
        if (flight.seatMap.get(current) != null)
        {
          System.out.print("XX ");
        }
        else
        {
          System.out.print(current + " ");
        }
      }
      System.out.println();
      if (i == 1)
      {
        System.out.println();
      }
	  }
  }


  public void preboardFlight(String flightNum) throws FlightNotFoundException
  {
    Flight flight = findFlight(flightNum);
    flight.loadQueue();
  }

  public void printQueue(String flightNum) throws FlightNotFoundException
  {
    Flight flight = findFlight(flightNum);
    flight.printQueue();
  }

  public void boardFlight(String flightNum, char startRow, char endRow) throws FlightNotFoundException
  {
    Flight flight = findFlight(flightNum);
    flight.removeFromQueue(startRow, endRow);
  }
}


