/*
 * A simple class to model an electronic airline flight reservation
 * 
 * This class has been done for you
 */
public class Reservation
{
	String flightNum;
	String flightInfo;
	boolean firstClass;
	String passengerName;
	String passengerPassport;
	String seat;

	
	/**
	 * Constructor method for Class Reservation
	 * @param flightNum
	 * @param info
	 * @param passenger
	 */
	public Reservation(String flightNum, String FlightInfo, String passengerName, String passengerPassport, String seat)
	{
		this.flightNum = flightNum;
		this.flightInfo = FlightInfo;
		this.firstClass = false;
		this.passengerName = passengerName;
		this.passengerPassport = passengerPassport;
		this.seat = seat;
	}

	
	
	/** 
	 * Checks if reservation object is first class.
	 * @return boolean true if reservation is first class, false otherwise
	 */
	public boolean isFirstClass()
	{
		return firstClass;
	}

	/**
	 * Sets firstClass instance variable to true
	 */
	public void setFirstClass()
	{
		this.firstClass = true;
	}

	
	/** 
	 * Gets flightNum instance variable
	 * @return String
	 */
	public String getFlightNum()
	{
		return flightNum;
	}
	
	
	/** 
	 * Gets flightInfo instance variable
	 * @return String
	 */
	public String getFlightInfo()
	{
		return flightInfo;
	}

	
	/** 
	 * sets flightInfo instance variable
	 * @param flightInfo the flightInfo to set
	 */
	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}


	public String getPassengerInfo()
	{
		return "Passenger Name: " + this.passengerName + "\tPassenger Passport: " + this.passengerPassport + "\tSeat: " + this.seat;
	}

	/**
	 * Prints Reservation information (includes: flightInfo and passenger info)
	 */
	public void print()
	{
		System.out.println(flightInfo + "\t" +  getPassengerInfo());
	}


	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof Reservation))
		{
			return false;
		}
		Reservation other = (Reservation) o;
		if (this.flightNum.equals(other.flightNum) && this.passengerName.equals(other.passengerName) && this.passengerPassport.equals(other.passengerPassport))
		{
			return true;
		}
		return false;
	}
}
