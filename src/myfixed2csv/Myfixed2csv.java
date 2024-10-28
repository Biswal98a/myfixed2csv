package myfixed2csv;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Myfixed2csv {
	public static void main(String[] args) throws Exception {
		// If less than 3 arguments 
        if (args.length < 3) 
        { 
            System.out.println("Usage: myfixed2csv <fixed-width-file> <n-columns> <length1> <length2> ...");
            return;
        }
         String fileName = "C:\\Users\\Merceadm\\Documents\\fixedwidth1.txt"; 
       // Parse the column lengths from the command line arguments
         int[] lengths = Arrays.stream(Arrays.copyOfRange(args, 2, args.length)) // Extract length arguments
                              .mapToInt(Integer::parseInt) // Convert them to integers
                              .toArray(); // Store them in an int array
         // Read the file line by line and process each line
          Files.lines(Paths.get(fileName)).forEach(line -> {
        	  // Use an array to track the starting position, which can be modified inside the lambda
        	  int[] start = {0}; // Array to hold the start position
         // Convert each fixed-width line into CSV format
            String csv = Arrays.stream(lengths).mapToObj(len -> {
            	 // Check if start position is within the line's length
                if (start[0] >= line.length()) {
                    return ""; // If out of bounds, return an empty string
                }
                // Ensure the end index doesn't go beyond the line's length
                int end = Math.min(start[0] + len, line.length());
                    // Extract field
                    String field = line.substring(start[0], end); // Safely extract the substring
                    start[0] += len;  // Update the starting point for the next field
                    return "\"" + field.trim().replaceFirst("^0+", "") + "\""; // Trim spaces and remove leading zeros
                    }).reduce((a, b) -> a + "," + b).orElse(""); // Join fields with commas
            // Output the final CSV line
            System.out.println(csv);
        });
    }

}
