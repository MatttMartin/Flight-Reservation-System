import java.util.ArrayList;


/*
 * 
 * This class models an aircraft type with a model name, a maximum number of economy seats, and a max number of forst class seats 
 * 
 * Add code such that class Aircraft implements the Comparable interface
 * Compare two Aircraft objects by first comparing the number of economy seats. If the number is equal, then compare the
 * number of first class seats 
 */
public class Aircraft implements Comparable<Aircraft>
{
  int numEconomySeats;
  int numFirstClassSeats;
  String[][] layout;
  String model;

  public Aircraft(int seats, String model)
  {
  	this.numEconomySeats = seats;
  	this.numFirstClassSeats = 0;
  	this.model = model;
	this.layout = generateLayout(this.layout);
  }

  public Aircraft(int economy, int firstClass, String model)
  {
  	this.numEconomySeats = economy;
  	this.numFirstClassSeats = firstClass;
  	this.model = model;
	this.layout = generateLayout(this.layout);
  }
  
	
  private String[][] generateLayout(String[][] layout)
  {
	int columns = getNumberOfColumnsNeeded(this.getTotalSeats());
	int rows = this.getTotalSeats() / columns;
	layout = new String[columns][rows];
	Character[] seatLetters = {'A', 'B', 'C', 'D'};
	int FCSeatsToBeAdded = this.getNumFirstClassSeats();

	for (int i = 0; i < rows; i++)
	{
		for (int j = 0; j < columns; j++)
		{
			if (FCSeatsToBeAdded > 0)
			{
				layout[j][i] = seatLetters[j] + String.valueOf(i) + "+";
				FCSeatsToBeAdded--;
			}
			else
			{
				layout[j][i] = seatLetters[j] + String.valueOf(i);
			}
		}
	}

	return layout;
  }


//   public void printSeats()
//   {
// 	for (int i = 0; i < this.layout.length; i++)
// 	{
// 		for (int j = 0; j < this.layout[i].length; j++)
// 		{
// 			System.out.print(this.layout[i][j] + " ");
// 		}
// 		System.out.println();
// 		if (i == 1 || i == 3)
// 		{
// 			System.out.println();
// 		}
// 	}
//   }


  private int getNumberOfColumnsNeeded(int numberOfSeats)
  {
	if (numberOfSeats < 16)
	{
		return 2;
	}
	else
	{
		return 4;
	}
  }


	/** 
	 * gets number of economy seats on the aircraft
	 * @return int
	 */
	public int getNumSeats()
	{
		return numEconomySeats;
	}
	
	
	/** 
	 * gets the total number of seats on the aircraft
	 * @return int
	 */
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	
	
	/**
	 * gets the number of first class seats on the aircraft 
	 * @return int
	 */
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}

	
	/** 
	 * gets the model of the aircraft
	 * @return String
	 */
	public String getModel()
	{
		return model;
	}

	
	/** 
	 * sets the model of the aircraft
	 * @param model
	 */
	public void setModel(String model)
	{
		this.model = model;
	}
	
	/**
	 * Prints the information of the aircraft
	 */
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}


	 /** 
	  * compares two aircrafts by total number of seats
	  * @param otherAircraft
	  * @return int
	  */
	 public int compareTo(Aircraft otherAircraft)
	 {
		 if (this.getTotalSeats() < otherAircraft.getTotalSeats())
		 {
			 return -1;
		 }
		 else if (this.getTotalSeats() == otherAircraft.getTotalSeats())
		 {
			 return 0;
		 }
		 else
		 {
			 return 1;
		 }
	 }
	
}
  
