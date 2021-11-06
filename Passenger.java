public class Passenger implements Comparable<Passenger>
{

    private String name;
    private String passportNum;
    private String seatNum;
    private int seatRow;
    private String seatType;
    public static final String DEFAULT_NAME = "";
    public static final String DEFAULT_PASSPORT_NUM = "";
    public static final String DEFAULT_SEAT_NUM = "";


    public Passenger(String name, String passportNum, String seatNum)
    {
        this.name = name;
        this.passportNum = passportNum;
        this.seatNum = seatNum;
        this.seatRow = generateSeatRow();
        this.seatType = generateSeatType();
    }


    /**
     * generates the seat type of the passenger by checking if the seatNum ends in a '+'
     * @return String "First Class" or "Economy"
     */
    private String generateSeatType()
    {
        if (seatNum.charAt(seatNum.length() - 1) == '+')
        {
            return "First Class";
        }
        else
        {
            return "Economy";
        }
    }

    /**
     * Extracts the seatNum integer from the seatNum string and returns it.
     * @return int, the column number of the passengers seatNum
     */
    private int generateSeatRow()
    {
        if (this.seatNum.charAt(seatNum.length() - 1) == '+')
        {
            return Integer.parseInt(this.seatNum.substring(1, seatNum.length() - 1));
        }
        else
        {
            return Integer.parseInt(this.seatNum.substring(1));
        }
    }


    public int getSeatRow()
    {
        return seatRow;
    }

    public void setSeatRow(int seatRow)
    {
        this.seatRow = seatRow;
    }
    

    public String getSeatType()
    {
        return seatType;
    }

    public void setSeatType(String seatType)
    {
        this.seatType = seatType;
    }


    /** 
     * Gets the name instance variable
     * @return String name
     */
    public String getName()
    {
        return name;
    }
    
    /** 
     * Gets the passportNum instance variable
     * @return int passportNum
     */
    public String getPassportNum()
    {
        return passportNum;
    }
    
    /**
     * Gets the seatNum instance variable 
     * @return String seatNum
     */
    public String getSeatNum()
    {
        return seatNum;
    }
    
    /** 
     * Sets the name instance variable
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /** 
     * Sets the passportNum instance variable
     * @param passportNum
     */
    public void setPassportNum(String passportNum)
    {
        this.passportNum = passportNum;
    }
    
    /** 
     * Sets the seatNum instance variable
     * @param seatNum
     */
    public void setSeatNum(String seatNum)
    {
        this.seatNum = seatNum;
    }

    
    /** 
     * Returns a string containing info about the passenger object including: name, passportNum, seatNum.
     * @return String
     */
    public String toString()
    {
        return "Passenger Name: " + this.name + "\t Passport Number: " + this.passportNum + "\t Seat Number: " + this.seatNum;
    }

    
    /** 
     * Checks if two passengers are equal
     * (if they are both passenger objects, have the same name, and have the same passportNum)
     * @param other
     * @return boolean
     */
    @Override
    public boolean equals(Object other)
    {
        if (other == null || !(other instanceof Passenger))
        {
            return false;
        }
        Passenger otherP = (Passenger) other;
        if (!this.name.equals(otherP.name))
        {
            return false;
        }
        if (!this.passportNum.equals(otherP.passportNum))
        {
            return false;
        }
        return true;
    }

    
    /** 
     * Compares two Passenger objects in reference to their seatRows.
     * @param otherPassenger
     * @return int, negative if this.seatRow < otherPassenger.seatNum, 0 if the seatRows are equal, positive otherwise
     */
    @Override
    public int compareTo(Passenger otherPassenger)
    {
        return this.getSeatRow() - otherPassenger.getSeatRow();
    }

}
