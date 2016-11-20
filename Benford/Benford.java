//Edwards Ames
//CSC 221 Software Design Lab
//SUMMER 2016
//Prof. Bherwani 

import java.io.*;

public class Benford 
{	
	final int ARRAY_LENGTH = 9;
	private int [] countArray = new int[ARRAY_LENGTH]; //Array to hold the count for every significant figure ( 1 - 9 )
	private double [] percentArray = new double [ARRAY_LENGTH]; //Array to hold the percent for every significant figure
	
	private int total = 0; //Total number of lines in the data.txt
	
	public static void main (String[] args) 
	{
		System.out.println("BENFORD: PROJECT 1 \n");

		Benford test1 = new Benford (); //Creating an object of type Benford
		
		//Function openFile() created to take care of opening data.txt file
		test1.openFile();
		
		//function getCount() was created for testing purposes, prints the count for every significant figure
		//test1.getCount();
		
		//This function prints the percentage for every significant figure on the screen in the correct format
		test1.printPercentage();
	}

	public void openFile ()
	{
		try
		{
			//Creating an object of type fileReader
			//inputFile now holds the data.txt file found at my desktop
			//I believe its an issue with the Mac operating system that doesn't allow me to just have ("data.txt") as 
			// a parameter in the FileReader method. I tried adding the data text file in the same directory 
			// as the source file but that doesn't fix the error. I also tried implementing it the way the 
			// book suggests and still no solution; I'm not sure what the issue is.
			//Please change the link to the appropriate place the data.txt file is saved in your computer. Thank you
			
			FileReader inputFile = new FileReader("/Users/Enzo/Desktop/data.txt"); 
			
			//Creates another object of type BufferedReader
			BufferedReader bufferReader = new BufferedReader(inputFile);
			
			String line;
			
			//while there's still a line to read in data.txt, run this loop
			while ( (line = bufferReader.readLine() ) != null ) 
			{
				computeNumber(line); //This function is called for every line in data.txt
				this.total++; //Total is updated in order to obtain the total number or lines
			}
			
			//Once all computations are finished, program closes the file
			bufferReader.close();
		}
		catch (Exception e) //Try and Catch is required in order to open a file in Java. 
		{
			//If any error is encountered this message will be printed on the screen
			System.out.println("Error Opening File. Program Terminating " + e.getMessage());
			System.exit(1);
		}
	}
	
	public void computeNumber (String line)
	{
		char temp = ' ';
		int numb = 0;
		
		//This loop is implemented in order to find the first significant figure on each line of data.txt.
		for (int i = 0; i < line.length(); i++)
		{
			//A comparison is made for every character in the line
			if ( line.charAt(i) == '-' || line.charAt(i) == '0' || line.charAt(i) == '.') 
			{
				continue;
			}
			//Once the first significant figure is found, assign such character to temp
			else
			{
				temp = line.charAt(i); 
				break;
			}
		}
		
		//Class Character has a method called getNumericValue() which returns the numeric value of a character
		numb = Character.getNumericValue(temp);
		
		//This function will be called for every significant figure found in each line of data.txt
		computeSwitch(numb); //Passes numb as a parameter
	}
	
	public void computeSwitch (int number)
	{	
		//Switch statement is required to keep track of the count for every significant figure
		//countArray is updated accordingly depending on the number that is passed to the function
		switch (number)
		{
			case 1:
				this.countArray[0]++;
				break;
			case 2:
				this.countArray[1]++;
				break;
			case 3:
				this.countArray[2]++;
				break;
			case 4:
				this.countArray[3]++;
				break;
			case 5:
				this.countArray[4]++;
				break;
			case 6:
				this.countArray[5]++;
				break;
			case 7:
				this.countArray[6]++;
				break;
			case 8:
				this.countArray[7]++;
				break;
			case 9:
				this.countArray[8]++;
				break;
			default:
				break;
		}
	}

//	Function created for testing purposes. Prints the count for every significant figure
/*	public void getCount ()
	{	
		for (int i = 0; i < 9 ; i++)
		{
			System.out.printf("digit %d = %d, ", i+1, this.countArray[i]);
		}
	}
*/
	
	public void printPercentage ()
	{	
		double prcnt;
		double roundedNumber;
		int i, j;
		
		//This for loop computes the percentage for every significant figure and records it in percentArray
		for (i = 0; i < 9 ; i++)
		{
			prcnt = this.countArray[i];
			prcnt /= this.total;
			prcnt *=100;
			this.percentArray[i] = prcnt;
		}
		
		//This double for loop outputs the percentage for every significant figure and the correct number of '*'
		for (i = 0; i < 9; i++)
		{
			System.out.printf("%d  (%.3f%%)\t: ", i+1, this.percentArray[i]);
			
			//Math.round() is used here to round the percentage to the nearest whole number
			roundedNumber = Math.round(percentArray[i]); //roundedNumber is updated for every index of percentArray 
			
			//This inner loop runs up to the rounded number in order to print the correct number of '*'
			for (j = 0; j < roundedNumber ; j++) 
			{
				System.out.printf("*");
			}
			System.out.printf("\n");
		}
	}
		
}

