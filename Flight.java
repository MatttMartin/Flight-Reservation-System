/* 
 *  Class to model an airline flight. In this simple system, all flights originate from Toronto
 *  
 *  This class models a simple flight that has only economy seats
 */
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.Iterator;


public class Flight implements Comparable<Flight>
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum FlightType {SHORTHAUL, MEDIUMHAUL, LONGHAUL};

	String flightNum;
	String airline;
	String origin, dest;
	String departureTime;
	Status status; // see enum Status above. google this to see how to use it
	int flightDuration;
	Aircraft aircraft;
	protected int passengers; // count of (economy) passengers on this flight - initially 0


	ArrayList<Passenger> manifest;
	ArrayList<Integer> usedSeatNums; 
	protected TreeMap<String, Passenger> seatMap;
	protected PriorityQueue<Passenger> queue;

	Random random = new Random(); 
  

	public Flight()
	{
		// Initialize instance variables to default values
		flightNum = "";
		airline = "";
		dest = "";
		origin = "";
		departureTime = "";
		flightDuration = 0;
		aircraft = null;
		passengers = 0;
		status = Status.ONTIME;
		manifest = new ArrayList<Passenger>();
		usedSeatNums = new ArrayList<Integer>();
		seatMap = populateSeatMap(seatMap);
		queue = new PriorityQueue<Passenger>();
	}
	

	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		//Initialize instance variables to parameter values
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		passengers = 0;
		status = Status.ONTIME;	
		manifest = new ArrayList<Passenger>();
		usedSeatNums = new ArrayList<Integer>();
		seatMap = populateSeatMap(seatMap);
		queue = new PriorityQueue<Passenger>();
	}


	private TreeMap<String, Passenger> populateSeatMap(TreeMap<String, Passenger> seatMap)
	{
		seatMap = new TreeMap<String, Passenger>();
		for (int i = 0; i < this.aircraft.layout.length; i++)
		{
			for (int j = 0; j < this.aircraft.layout[i].length; j++)
			{
				seatMap.put(this.aircraft.layout[i][j], null);
			}
		}
		return seatMap;
	}

	public void printMap()
	{
		System.out.println(this.seatMap);
	}
	

	public FlightType getFlightType()
	{
		return FlightType.MEDIUMHAUL;
	}


	/** 
	 * @return flightNum String
	 */
	public String getFlightNum()
	{
		return flightNum;
	}
	
	/** 
	 * Sets flightNum instance variable
	 * @param flightNum
	 */
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	
	/** 
	 * Gets airline instance variable
	 * @return String airline
	 */
	public String getAirline()
	{
		return airline;
	}
	
	/** 
	 * Sets airline instance variable
	 * @param airline
	 */
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	
	/** 
	 * Gets origin instance variable
	 * @return String origin
	 */
	public String getOrigin()
	{
		return origin;
	}
	
	/** 
	 * Sets origin instance variable
	 * @param origin 
	 */
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	
	/** 
	 * Gets destination instance variable
	 * @return String dest
	 */
	public String getDest()
	{
		return dest;
	}
	
	/** 
	 * Sets destination instance variable
	 * @param dest
	 */
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	
	/** 
	 * Gets departure time instance variable
	 * @return String departureTime
	 */
	public String getDepartureTime()
	{
		return departureTime;
	}
	
	/** 
	 * Sets departure time instance variable
	 * @param departureTime
	 */
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	
	/** 
	 * Gets status instance variable
	 * @return Status status
	 */
	public Status getStatus()
	{
		return status;
	}
	
	/** 
	 * Sets status instance variable 
	 * @param status
	 */
	public void setStatus(Status status)
	{
		this.status = status;
	}
	
	/** 
	 * gets flight duration instance variable
	 * @return int flightDuration
	 */
	public int getFlightDuration()
	{
		return flightDuration;
	}
	
	/** 
	 * Sets flight duration instance variable
	 * @param dur
	 */
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	
	
	/** 
	 * Gets Economy passenger count variable
	 * @return int passengers
	 */
	public int getPassengers()
	{
		return passengers;
	}
	
	/** 
	 * Sets passenger count variable
	 * @param passengers
	 */
	public void setPassengers(int passengers)
	{
		this.passengers = passengers;
	}
	

	
	
	/** 
	 * Checks if there are seats available on the flight.
	 * @return boolean true if there are seats available, otherwise false.
	 */
	public boolean seatsAvailable()
	{
		if (aircraft.getTotalSeats() > passengers) //returns true if there are more economy seats than passengers
		{
			return true;
		}
		return false;
	}
	

	
	
	/** 
	 * Removes the passenger from the passengers list, and reduces the economy passenger count
	 * @param res reservation containing passenger to be removed
	 */
	public void cancelSeat(Reservation res)
	{
		Passenger passenger = findPassenger(res);
		seatMap.put(passenger.getSeatNum(), null);
		manifest.remove(passenger); 
		passengers--;
	}


	/**
	 * Finds and returns a passenger in the manifest that matches the info given in the reservation.
	 * @param res
	 * @return passenger that matches.
	 */
	private Passenger findPassenger(Reservation res)
	{
		Passenger temp = new Passenger(res.passengerName, res.passengerPassport, res.seat);
		if (manifest.contains(temp))
		{
			return temp;
		}
		throw new PassengerNotFoundException("Passenger " + res.passengerName + " not found");
	}


	/**
	 * Adds the passenger to the flight (adds to manifest, seatMap, and increases passenger count for flight).
	 * @param passenger to be added to flight.
	 * @throws FlightFullException
	 * @throws DuplicatePassengerException
	 * @throws SeatDoesNotExistException
	 */
	public void reserveSeat(Passenger passenger) throws FlightFullException, DuplicatePassengerException, SeatDoesNotExistException
	{
		if (!seatsAvailable())
		{
			throw new FlightFullException("Flight " + flightNum + " Full");	
		}
		if (seatMap.get(passenger.getSeatNum()) != null)
		{
			throw new FlightFullException("Seat " + passenger.getSeatNum() + " is already booked");
		}
		if (manifest.contains(passenger))
		{
			throw new DuplicatePassengerException("Passenger " + passenger.getName() + " is Already Booked on this flight");
		}		
		String seatNum = passenger.getSeatNum();
		if (!seatMap.containsKey(seatNum))
		{
			throw new SeatDoesNotExistException("Seat " + seatNum + " does not exist on flight " + flightNum);
		}

		//if no exceptions are thrown:
		manifest.add(passenger);
		seatMap.put(seatNum, passenger);
		passengers++;
	}

	
	
	/** 
	 * Returns info about the flight including: airline, flightNum, dest, departureTime, flightDuration, status.
	 * @return String
	 */
	public String toString()
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
		
	}

	/**
	 * Compares two flight objects by flightDuration
	 * @param other
	 * @return
	 */
	public int compareTo(Flight other)
	{
		return this.flightDuration - other.flightDuration;
	}


	/**
	 * adds all of the passengers in the manifest to the queue
	 */
	public void loadQueue()
	{
		queue.addAll(manifest);
	}

	/**
	 * prints all of the passengers in the queue
	 */
	public void printQueue()
	{
		//since iterating throught the priority queue normally prints them in random order, we need to print each by using poll()
		PriorityQueue<Passenger> temp = new PriorityQueue<Passenger>();
		while (!queue.isEmpty())
		{
			Passenger tempPassenger = queue.poll();
			System.out.println(tempPassenger.toString());
			temp.add(tempPassenger);
		}
		queue = temp;
	}

	/**
	 * Removes all passengers from the queue that's seatRows are within startRow and endRow (inclusive).
	 * @param startRow Example: 'A'
	 * @param endRow Example: 'B'
	 */
	public void removeFromQueue(char startRow, char endRow)
	{
		Iterator<Passenger> iterator = queue.iterator();
		while (iterator.hasNext())
		{
			Passenger passenger = iterator.next();
			int passengerSeatRow = passenger.getSeatRow();
			if (passengerSeatRow >= startRow && passengerSeatRow <= endRow)
			{
				iterator.remove();
			}
		}
	}

}

