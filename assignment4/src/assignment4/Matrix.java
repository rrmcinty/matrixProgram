package assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * This Matrix class is used to create a Matrix object using a read in 2d 
 * array text file and a newly created 2d array. Other methods are used to
 * transpose, add, and multiply the matrices.
 * @author Ryan
 *
 */
public class Matrix {
	private int rows;
	private int columns;
	private double[][] array;
	
	/**
	 * Constructor using a 2d array as a parameter to create new
	 * matrix object containing double values.
	 * @param 2 dimensional array full of values
	 */
	public Matrix(double[][] array) {
		this.rows = array.length;
		this.columns = array[0].length;
		
		for (double i = 0; i < rows; i++) {
			for (int j = 0; j<columns; j++) {	
			}
			
		this.array = array;
		}	
	}
	
	/**
	 * Constructor using a read in text file of a 2d array to create
	 * new matrix object containing double values.
	 * @param text file containing 2d array full of values
	 */
	public Matrix(String filename) {
		double[][] temp;
		int r = 1;
		int c = 0;
		Scanner lineRead = null;
		Scanner s = null;
		
		File infile = new File(filename);
		
		try {
            lineRead = new Scanner(infile);
            lineRead.useDelimiter("\\s+");
        } catch (FileNotFoundException e) { 
            e.printStackTrace();
        }
		/*
		 * This portion used to read in file and create the
		 * dimensions of the new array 
		 */
		while(lineRead.hasNextLine()) {
			s = new Scanner(lineRead.nextLine());
			s.useDelimiter("\\s+");
			c = 0;
			
			while(s.hasNextDouble()) {
				double d = s.nextDouble(); //endlessly iterates through columns without this here
				++c;
			}
			
			if (lineRead.hasNextLine()) {
				++r;
			}
			else {
				break;
			}	    
		}
		
		this.columns = c;
		this.rows = r;
		temp = new double[rows][columns];
		/*
		 * This portion used to reread in file and add 
		 * each value to newly created array
		 */
		try {
            lineRead = new Scanner(infile);
        } catch (FileNotFoundException e) { 
            e.printStackTrace();
        }
		
		for(int i = 0; i < r; ++i) {
		    for(int j = 0; j < c; ++j)
		    {
		        if(lineRead.hasNext())
		        {
		            temp[i][j] = lineRead.nextDouble();
		        }
		    }
		}
		
		this.array = temp;	
	}
	
	/** 
	 * Method used to obtain number of rows in the matrix.
	 * @return integer number of rows
	 */
	public int getRows() {
		return this.rows;
	}
	
	/**
	 * Method used to obtain the number of columns in
	 * the matrix.
	 * @return integer number of columns
	 */
	public int getCols() {
		return this.columns;
	}
	
	/**
	 * Method used to retrieve the array in matrix object. 
	 * This 2d array contains all the values used to multiply
	 * and add matrices together.
	 * @return double[][] array attached to matrix object
	 */
	public double[][] getArray() {
		return this.array;
	}
	
	/**
	 * This method overrides the toString method to 
	 * display the dimensions of the matrix followed by
	 * the matrix array (containing all values in matrix).
	 */
	public String toString() {
	    String aString = "";     
	    String tString = this.rows + "x" + this.columns + " matrix\n";
	    double[][] a = this.array;
	    /*
	     * iterates through each row and column concatenating
	     * each newly read string and then returning to new
	     * row after the current row is fully converted
	     */
	    for (int r = 0; r < this.rows; r++) {
	        for (int c = 0; c < this.columns; c++ ) {
	        aString = aString + " " + a[r][c];
	        }
	    aString = aString + "\n";
	    }
	    
 	    return tString + aString;
	}
	
	/**
	 * This method adds two arrays together first determining if
	 * each of the matrices rows and columns match for adding, 
	 * iterating through each matrix and then adding the values
	 * together creating a new matrix object.
	 * @param other matrix
	 * @return newly added matrix
	 */
	public Matrix add(Matrix other) {
		double[][] array;
		double[][] thisArray = this.array;
		double[][] otherArray = other.array;

		if (this.columns==other.columns&&this.rows==other.rows) { //determines equal columns and rows for adding
			array = new double[rows][columns];
			for (int i = 0; i<rows ; i++) { //iterates through arrays adding to new array
				for (int j = 0; j<columns ; j++) {
					array[i][j] = thisArray[i][j] + otherArray[i][j];
				}
			}
			return new Matrix(array);
		}
		else {
			System.out.println("Matrices do not have the same dimensions, therefore can not be added together.");
			return null;
		}	
	}

	/**
	 * This method multiplies one matrix by the other, first 
	 * making sure the inner dimensions are equal, then 
	 * creating a new array with the outer dimensions, and
	 * finally creating a new multiplied matrix object.
	 * @param other matrix
	 * @return newly multiplied matrix
	 */
	public Matrix mult(Matrix other) {
		double[][] array;
		double[][] thisArray = this.getArray();
		double[][] otherArray = other.getArray();

		if (this.columns==other.rows) { //determines equality of inner dimensions
			array = new double[this.rows][other.columns]; //creates new array with outer dimensions
			for (int i = 0; i<this.rows; i++) {
				int j = 0; // this sets j back to 0
				for (j = 0; j<other.columns; j++) {
					int count = 0;
					double y = 0;
					double x = 0;
					while (count < this.columns) {
						x = thisArray[i][count]*otherArray[count][j];
						y += x;  
						count++;
					}
					array[i][j]=y;
				}
			}
			return new Matrix(array);
		}
		else {
			System.out.println("The matrices do not match inner dimensions.");
			return null;
		}
	}
	
	/**
	 * This method is used to multiply each value in
	 * the matrix and multiply it by the given 
	 * double parameter, returning new matrix.
	 * @param number to be multiplied
	 * @return newly multiplied matrix
	 */
	public Matrix mult(double d) {
		double[][] array = new double[this.rows][this.columns];
		double[][] original = this.array;
		
		for (int i = 0; i < this.rows ; i++) {
			for (int j = 0; j < this.columns ; j++) {
				array[i][j] = original[i][j] * d;
			}
		}
		
		return new Matrix(array);
	}
	
	/**
	 * This method is used to take in a matrix,
	 * transpose it, and return it in a newly created
	 * matrix object.
	 * @return newly transposed matrix
	 */
	public Matrix transpose() {
		double[][] a = this.array;
		double[][] n = new double[this.getCols()][this.getRows()];
	    for (int i = 0; i < this.rows; i++) {
	        for (int j = 0; j < this.columns; j++ ) {
	        	n[j][i]=a[i][j];
	        }
	    }
	    
	    return new Matrix(n);
	}
	
	/**
	 * Overrides the equals() method. Takes in a matrix
	 * and compares it to another matrix determining
	 * if each value and dimension is equal returning
	 * true or false.
	 * @param other matrix
	 * @return true for equals or false for not equals
	 */
	public boolean equals(Matrix other) {
		double[][] thisArray = this.getArray();
		double[][] otherArray = other.getArray();
		boolean equals = false;
		
		if (this.columns == other.columns && this.rows == other.rows) {
			for (int i = 0 ; i < this.rows ; i ++) {
				for (int j = 0 ; j < this.columns ; j ++) {
					if (thisArray[i][j] == otherArray[i][j]) {
						equals = true;
					}
					else {
						equals = false;
						break; //need to break out of loop once false to avoid future true values
					}
				}
			}
		}
		
		return equals;
	}
}

