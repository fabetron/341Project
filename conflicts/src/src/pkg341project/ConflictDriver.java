package src.pkg341project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConflictDriver {

	public static void main(String[] args) throws IOException {
		GUI interaction = new GUI(); 
		Conflicts conflict = new Conflicts(); 
		String schedule = "schedule.csv"; 
		File schedule1 = new File(schedule); 
		BufferedReader reader = new BufferedReader(new FileReader(schedule1)); 
		
		conflict.readData(reader);
		//conflict.checkClassroomTimeConflict(list)
	
	}

}
