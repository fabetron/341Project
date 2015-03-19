import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProfessorClassConflicts {
	
	private ArrayList<String> rowList = new ArrayList<String>(); //List to store file as Strings
	
	public void readData(BufferedReader schedule) throws IOException{
	
		String currentRow = schedule.readLine(); //Reads first line of file
		while (currentRow != null){
			rowList.add(currentRow); //Adds the current row to the List
			currentRow = schedule.readLine(); //Reads next line of file
		}
			 schedule.close();	
	}
	
	public void findInstructorConflict(){
		String classes;
		String[] professorClasses; //Temp place to store schedule information
		ArrayList<String> professorList = new ArrayList<String>(); //A list to hold all professors and their scheduled classes
		Set<String>allClasses = new HashSet<String>(); //Set to check for duplicate classes
		Set<String>duplicateClasses = new HashSet<String>(); //Set to store duplicate classes
		
		for(String row : rowList){ //Iterates through rowList
			professorClasses = row.split(","); 
			if(professorClasses.length < 5){
				ArrayList<String> lessThanList = new ArrayList<String>(Arrays.asList(professorClasses)); //Handles blank cells at the end of array
				lessThanList.add(" ");
				classes = lessThanList.get(0) + ", " + lessThanList.get(3) + ", " + lessThanList.get(4);
			}
				else{
			classes = professorClasses[0] + ", " + professorClasses[3] + ", " + professorClasses[4]; //Creates String of professor, days, time
				}
			professorList.add(classes); //Adds String to List
				
		}
		for(String currentClass : professorList) { //Iterates through professorList
			if (allClasses.contains(currentClass)){ 
				duplicateClasses.add(currentClass);
			}                                      //Loop checks for and stores duplicate values in duplicateClasses
			else{
				allClasses.add(currentClass);
			}
		}
		System.out.println("\n" + "The following professors have scheduling conflicts:" + "\n");
		for (String printDuplicate : duplicateClasses) { //Prints professors with schedule conflicts
			System.out.println(printDuplicate);
		}
	}
	
	public void findClassroomConflict(){
		
		String times;
		String[] classTimes; //Temp place to store schedule information
		ArrayList<String> classList = new ArrayList<String>(); //A list to hold all classes and scheduled times and days
		Set<String>allTimes = new HashSet<String>(); //Set to check for duplicate times
		Set<String>duplicateTimes = new HashSet<String>(); //Set to store duplicate times
		
		for(String row : rowList){ //Iterates through rowList
			classTimes = row.split(",");
			if(classTimes.length < 5){
				ArrayList<String> lessThanList2 = new ArrayList<String>(Arrays.asList(classTimes)); //Handles blank cells at the end of array
				lessThanList2.add(" ");
				times = lessThanList2.get(2) + ", " + lessThanList2.get(3) + ", " + lessThanList2.get(4);
			}
			else{
			times = classTimes[2] + ", " + classTimes[3] + ", " + classTimes[4]; //Creates String of classroom, days, time
			}
			classList.add(times); //Adds String to List
		}
		for(String currentTime : classList) { //Iterates through classList
			if (allTimes.contains(currentTime)){ 
				duplicateTimes.add(currentTime);
			}                                      //Loop checks for and stores duplicate values in duplicateTimes
			else{
				allTimes.add(currentTime);
			}
		}
		System.out.println("\n" + "The following classrooms have scheduling conflicts:" + "\n");
		for(String printDuplicate2 : duplicateTimes){
			System.out.println(printDuplicate2);
		}
	}
	
}
