package src.pkg341project;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Conflicts {
		//indices in Array list
	    private int instructorIndex=0; 
	    private int classroomIndex=1;
	    private int timeStartIndex=2;
	    private int timeEndIndex=3;
	    private int dayIndex=4;
	    
	    //variables for checks
	    private int maxClasses = 0;
	    private int classSectionIndex=5;
	    private int transitTime=15;
	    
	private ArrayList<String> rowList = new ArrayList<String>(); //List to store file as Strings
	private ArrayList<ArrayList<String>> indexedList = new ArrayList<ArrayList<String>>(); //List to store file as Strings
		
	public void readData(BufferedReader schedule) throws IOException{
		rowList.clear();
		indexedList.clear();
		
		String currentRow = schedule.readLine(); //Reads first line of file
		while (currentRow != null){
			rowList.add(currentRow); //Adds the current row to the List
			
			ArrayList<String> indexedRow= new ArrayList<String>();
			String[] items=currentRow.split(",");
			for(int i=0;i<items.length;i++){
				indexedRow.add(items[i]);
			}
			indexedList.add(indexedRow);
			
			currentRow = schedule.readLine(); //Reads next line of file
		}
			 schedule.close();	
	}
	
	public String checkTransitTimeConflict(ArrayList<ArrayList<String>> list){
		String conflicts="";
		//days of the week
		String[] days = {"M","T","W","R","F"};
		
		//compare every class against every other class
		for(int i=0; i<list.size()-1; i++){
			for(int j=i+1; j<list.size(); j++){
				//check same instructor
				if(list.get(i).get(instructorIndex).equals(list.get(j).get(instructorIndex))){
					//check different classrooms
					if(!list.get(i).get(classroomIndex).equals(list.get(j).get(classroomIndex))){
						//check same day
						String days1=list.get(i).get(dayIndex);
						String days2=list.get(j).get(dayIndex);
						
						Boolean sameDay=false;
						
						for(int d=0;d<5;d++){
							if(days1.contains(days[d])&&days2.contains(days[d])){
								sameDay=true;
							}
						}
						
						//check class times 
						if(sameDay){
							//get start and end times
							
							//get AM/PM
							String[] start1=(list.get(i)).get(timeStartIndex).split(" ");
							String[] start2=(list.get(j)).get(timeStartIndex).split(" ");
							
							String[] end1=(list.get(i)).get(timeEndIndex).split(" ");
							String[] end2=(list.get(j)).get(timeEndIndex).split(" ");
							
							//get start time of classes
							String[] s1=start1[0].split(":");
							String[] s2=start2[0].split(":");
							
							//get initial end time of class
							String[] ei1=end1[0].split(":");
							String[] ei2=end2[0].split(":");
							
							//get end time of class to add transit time to
							String[] e1=end1[0].split(":");
							String[] e2=end2[0].split(":");
							
							//update times for PM
							if(!(s1[0].equals("12")) && start1[1].equals("PM")){
								s1[0]=Integer.toString(Integer.parseInt(s1[0])+12);
							}
							
							if(!(s2[0].equals("12")) && start2[1].equals("PM")){
								s2[0]=Integer.toString(Integer.parseInt(s2[0])+12);
							}
							
							if(!(ei1[0].equals("12")) && end1[1].equals("PM")){
								ei1[0]=Integer.toString(Integer.parseInt(ei1[0])+12);
								e1[0]=Integer.toString(Integer.parseInt(e1[0])+12);
							}
							
							if(!(ei2[0].equals("12")) && end2[1].equals("PM")){
								ei2[0]=Integer.toString(Integer.parseInt(ei2[0])+12);
								e2[0]=Integer.toString(Integer.parseInt(e2[0])+12);
							}
							
							//update end time with transit time added
							if(Integer.parseInt(e1[1])<60-transitTime){
								e1[1]=Integer.toString(Integer.parseInt(e1[1])+transitTime);
							}else{
								e1[0]=Integer.toString(Integer.parseInt(e1[0])+1);
								e1[1]=Integer.toString(Integer.parseInt(e1[1])-60+transitTime);
							}
							
							if(Integer.parseInt(e2[1])<60-transitTime){
								e2[1]=Integer.toString(Integer.parseInt(e2[1])+transitTime);
							}else{
								e2[0]=Integer.toString(Integer.parseInt(e2[0])+1);
								e2[1]=Integer.toString(Integer.parseInt(e2[1])-60+transitTime);
							}
							
							//System.out.print(s1[0] + " " + s2[0] + " " + ei1[0] + " " + ei2[0] + " " + e1[0] + " " + e2[0]);
							
							//get class section
							String class1=list.get(i).get(classSectionIndex);
							String class2=list.get(j).get(classSectionIndex);
							
							//check for conflicts
							Boolean conflictFound=false;
							if(Integer.parseInt(s1[0])>Integer.parseInt(ei2[0]) || (Integer.parseInt(s1[0])==Integer.parseInt(ei2[0]) && Integer.parseInt(s1[1])>=Integer.parseInt(ei2[1]))){
								if(Integer.parseInt(s1[0])<Integer.parseInt(e2[0])){
									conflictFound=true;
									//CONFLICT
									//System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
									conflicts+="Transit time conflict between: " + class1 + " and "+ class2 +"\n";
								}else{
									if(Integer.parseInt(s1[0])==Integer.parseInt(e2[0])){
										if(Integer.parseInt(s1[1])<Integer.parseInt(e2[1])){
											conflictFound=true;
											//CONFLICT
											//System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
											conflicts+="Transit time conflict between: " + class1 + " and "+ class2 +"\n";
										}
									}
								}
							}
							
							//check for conflicts in opposite direction
							if(!conflictFound){
								if(Integer.parseInt(s2[0])>Integer.parseInt(ei1[0]) || (Integer.parseInt(s2[0])==Integer.parseInt(ei1[0]) && Integer.parseInt(s2[1])>=Integer.parseInt(ei1[1]))){
									if(Integer.parseInt(s2[0])<Integer.parseInt(e1[0])){
										//CONFLICT
										//System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
										conflicts+="Transit time conflict between: " + class1 + " and "+ class2 +"\n";
									}else{
										if(Integer.parseInt(s2[0])==Integer.parseInt(e1[0])){
											if(Integer.parseInt(s2[1])<Integer.parseInt(e1[1])){
												//CONFLICT
												//System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
												conflicts+="Transit time conflict between: " + class1 + " and "+ class2 +"\n";
											}
										}
									}
								}
							}
						}	
					}	
				}
			}
		}	
		
		return conflicts;
	}
	
	public String checkClassroomTimeConflict(ArrayList<ArrayList<String>> list){
		String conflicts="";
		//days of the week
		String[] days = {"M","T","W","R","F"};
		
		//compare every class against every other class
		for(int i=0; i<list.size()-1; i++){
			for(int j=i+1; j<list.size(); j++){
				//check same classroom
				if(list.get(i).get(classroomIndex).equals(list.get(j).get(classroomIndex))){
					//check same day
					String days1=list.get(i).get(dayIndex);
					String days2=list.get(j).get(dayIndex);
					
					Boolean sameDay=false;
					
					for(int d=0;d<5;d++){
						if(days1.contains(days[d])&&days2.contains(days[d])){
							sameDay=true;
						}
					}
					
					//check class times 
					if(sameDay){
						//get start and end times
						
						//get AM/PM
						String[] start1=(list.get(i)).get(timeStartIndex).split(" ");
						String[] start2=(list.get(j)).get(timeStartIndex).split(" ");
						
						String[] end1=(list.get(i)).get(timeEndIndex).split(" ");
						String[] end2=(list.get(j)).get(timeEndIndex).split(" ");
						
						//get start time of classes
						String[] s1=start1[0].split(":");
						String[] s2=start2[0].split(":");
						
						//get end time of class to add transit time to
						String[] e1=end1[0].split(":");
						String[] e2=end2[0].split(":");
						
						//update times for PM
						if(!(s1[0].equals("12")) && start1[1].equals("PM")){
							s1[0]=Integer.toString(Integer.parseInt(s1[0])+12);
						}
						
						if(!(s2[0].equals("12")) && start2[1].equals("PM")){
							s2[0]=Integer.toString(Integer.parseInt(s2[0])+12);
						}
						
						if(!(e1[0].equals("12")) && end1[1].equals("PM")){
							e1[0]=Integer.toString(Integer.parseInt(e1[0])+12);
						}
						
						if(!(e2[0].equals("12")) && end2[1].equals("PM")){
							e2[0]=Integer.toString(Integer.parseInt(e2[0])+12);
						}
						
						//System.out.print(s1[0] + " " + s2[0] + " " + ei1[0] + " " + ei2[0] + " " + e1[0] + " " + e2[0]);
						
						//get class section
						String class1=list.get(i).get(classSectionIndex);
						String class2=list.get(j).get(classSectionIndex);
						
						//check for conflicts
						Boolean conflictFound=false;
						
						if(Integer.parseInt(s1[0])<Integer.parseInt(e2[0]) || (Integer.parseInt(s1[0])==Integer.parseInt(e2[0]) && Integer.parseInt(s1[1])<Integer.parseInt(e2[1]))){
							if(Integer.parseInt(e1[0])>Integer.parseInt(s2[0])||(Integer.parseInt(e1[0])==Integer.parseInt(s2[0]) && Integer.parseInt(e1[1])>Integer.parseInt(s2[1]))){
								conflictFound=true;
								//CONFLICT
								//System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
								conflicts+="Classroom-time conflict between: " + class1 + " and "+ class2 +"\n";
							}
						}
						
						//check for conflicts in opposite direction
						if(!conflictFound){
							if(Integer.parseInt(s2[0])<Integer.parseInt(e1[0]) || (Integer.parseInt(s2[0])==Integer.parseInt(e1[0]) && Integer.parseInt(s2[1])<Integer.parseInt(e1[1]))){
								if(Integer.parseInt(e2[0])>Integer.parseInt(s1[0])||(Integer.parseInt(e2[0])==Integer.parseInt(s1[0]) && Integer.parseInt(e2[1])>Integer.parseInt(s1[1]))){
									conflictFound=true;
									//CONFLICT
									//System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
									conflicts+="Classroom-time conflict between: " + class1 + " and "+ class2 +"\n";
								}
							}
						}
					}		
				}
			}
		}	
		
		return conflicts;
	}
	
	public String checkInstructorTimeConflict(ArrayList<ArrayList<String>> list){
		String conflicts="";
		//days of the week
		String[] days = {"M","T","W","R","F"};
		
		//compare every class against every other class
		for(int i=0; i<list.size()-1; i++){
			for(int j=i+1; j<list.size(); j++){
				//check same instructor
				if(list.get(i).get(instructorIndex).equals(list.get(j).get(instructorIndex))){
					//check same day
					String days1=list.get(i).get(dayIndex);
					String days2=list.get(j).get(dayIndex);
					
					Boolean sameDay=false;
					
					for(int d=0;d<5;d++){
						if(days1.contains(days[d])&&days2.contains(days[d])){
							sameDay=true;
						}
					}
					
					//check class times 
					if(sameDay){
						//get start and end times
						
						//get AM/PM
						String[] start1=(list.get(i)).get(timeStartIndex).split(" ");
						String[] start2=(list.get(j)).get(timeStartIndex).split(" ");
						
						String[] end1=(list.get(i)).get(timeEndIndex).split(" ");
						String[] end2=(list.get(j)).get(timeEndIndex).split(" ");
						
						//get start time of classes
						String[] s1=start1[0].split(":");
						String[] s2=start2[0].split(":");
						
						//get end time of class to add transit time to
						String[] e1=end1[0].split(":");
						String[] e2=end2[0].split(":");
						
						//update times for PM
						if(!(s1[0].equals("12")) && start1[1].equals("PM")){
							s1[0]=Integer.toString(Integer.parseInt(s1[0])+12);
						}
						
						if(!(s2[0].equals("12")) && start2[1].equals("PM")){
							s2[0]=Integer.toString(Integer.parseInt(s2[0])+12);
						}
						
						if(!(e1[0].equals("12")) && end1[1].equals("PM")){
							e1[0]=Integer.toString(Integer.parseInt(e1[0])+12);
						}
						
						if(!(e2[0].equals("12")) && end2[1].equals("PM")){
							e2[0]=Integer.toString(Integer.parseInt(e2[0])+12);
						}
						
						//System.out.print(s1[0] + " " + s2[0] + " " + ei1[0] + " " + ei2[0] + " " + e1[0] + " " + e2[0]);
						
						//get class section
						String class1=list.get(i).get(classSectionIndex);
						String class2=list.get(j).get(classSectionIndex);
						
						//check for conflicts
						Boolean conflictFound=false;
						
						if(Integer.parseInt(s1[0])<Integer.parseInt(e2[0]) || (Integer.parseInt(s1[0])==Integer.parseInt(e2[0]) && Integer.parseInt(s1[1])<Integer.parseInt(e2[1]))){
							if(Integer.parseInt(e1[0])>Integer.parseInt(s2[0])||(Integer.parseInt(e1[0])==Integer.parseInt(s2[0]) && Integer.parseInt(e1[1])>Integer.parseInt(s2[1]))){
								conflictFound=true;
								//CONFLICT
								//System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
								conflicts+="Instructor-time conflict between: " + class1 + " and "+ class2 +"\n";
							}
						}
						
						//check for conflicts in opposite direction
						if(!conflictFound){
							if(Integer.parseInt(s2[0])<Integer.parseInt(e1[0]) || (Integer.parseInt(s2[0])==Integer.parseInt(e1[0]) && Integer.parseInt(s2[1])<Integer.parseInt(e1[1]))){
								if(Integer.parseInt(e2[0])>Integer.parseInt(s1[0])||(Integer.parseInt(e2[0])==Integer.parseInt(s1[0]) && Integer.parseInt(e2[1])>Integer.parseInt(s1[1]))){
									conflictFound=true;
									//CONFLICT
									//System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
									conflicts+="Instructor-time conflict between: " + class1 + " and "+ class2 +"\n";
								}
							}
						}
					}		
				}
			}
		}	
		
		return conflicts;
	}
	
	
	
	public void findInstructorConflict(){
		String classes;
		String[] professorClasses; //Temp place to store schedule information
		ArrayList<String> professorList = new ArrayList<String>(); //A list to hold all professors and their scheduled classes
		HashSet<String>allClasses = new HashSet<String>(); //Set to check for duplicate classes
		HashSet<String>duplicateClasses = new HashSet<String>(); //Set to store duplicate classes
		
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
		HashSet<String>allTimes = new HashSet<String>(); //Set to check for duplicate times
		HashSet<String>duplicateTimes = new HashSet<String>(); //Set to store duplicate times
		
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
	
	
	
	
	public String toString(ArrayList<ArrayList<String>> list){
		String s="";
		for(int x = 0; x < list.size(); x++){
			for(int y = 0; y<list.get(0).size(); y++){
				//System.out.print(list.get(x).get(y)+" \t");
				s+=list.get(x).get(y)+" \t";
			}
			//System.out.println();
			s+="\n";
		}
		return s;
	 
	}

}

	
	
	

