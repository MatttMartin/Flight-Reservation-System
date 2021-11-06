import java.util.ArrayList;

/*
 * A long haul flight is a flight that travels thousands of kilometers and typically has separate seating areas 
 */

public class LongHaulFlight extends Flight
{
	int numFirstClassPassengers;
	//ArrayList<Integer> usedFCSeatNums;
	
	// Possible seat types
	public static final String FIRSTCLASS = "First Class Seat";
	public static final String ECONOMY = "Economy Seat";  
 
	/**
	 * Constructor for LongHaulFlight
	 * @param flightNum
	 * @param airline
	 * @param dest
	 * @param departure
	 * @param flightDuration
	 * @param aircraft
	 */
	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		numFirstClassPassengers = 0;
	}


	/**
	 * default constructor for LongHaulFLight
	 */
	public LongHaulFlight()
	{
     // default constructor
	 super();
	 numFirstClassPassengers = 0;
	}

	@Override
	public FlightType getFlightType()
	{
		return FlightType.LONGHAUL;
	}

	/** 
	 * Checks if first class seats are available on the flight.
	 * @return boolean true if there are seats available, false otherwise.
	 */
	public boolean firstClassSeatsAvailable()
	{
		if (aircraft.numFirstClassSeats > numFirstClassPassengers)
		{
			return true;
		}
		return false;
	}

	
	/** 
	 * Returns the flight information, plus "LongHaul"
	 * @return String
	 */
	@Override
	public String toString()
	{
		return super.toString() + "\t" + "LongHaul";
	}
}
