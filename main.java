import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
		//class 1
		ArrayList<String> c1 = new ArrayList<String>();
		c1.add("Shesh");
		c1.add("OU140");
		c1.add("10:00");
		c1.add("12:00");
		c1.add("MTRF");
		c1.add("IT100");
		
		//class 2
		ArrayList<String> c2 = new ArrayList<String>();
		c2.add("Shesh");
		c2.add("OU141");
		c2.add("12:00");
		c2.add("1:00");
		c2.add("W");
		c2.add("IT101");
		
		list.add(c2);
		list.add(c1);
		
		checkTransitConflict(list);
	}

	public static void checkTransitConflict(ArrayList<ArrayList<String>> list){
		//indices for values in ArrayList (Can be changed later) 
		int instructorIndex=0;
		int classroomIndex=1;
		int timeStartIndex=2;
		int timeEndIndex=3;
		int dayIndex=4;
		int classSectionIndex=5;
		
		//optimal transit time (Can be changed later) 
		int transitTime=15;
		//days of the week
		String[] days = {"M","T","W","R","F"};
		
		//complare every class against every other class
		for(int i=0; i<list.size()-1; i++){
			for(int j=i+1; j<list.size(); j++){
				//check same instructor
				if(list.get(i).get(instructorIndex).equals(list.get(j).get(instructorIndex))){
					//check different classrooms
					if(!list.get(i).get(classroomIndex).equals(list.get(j).get(classroomIndex))){
						System.out.println("TEST");
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
							System.out.println("TEST");
							
							//get start time of classes
							System.out.println(list.size() + " " + i + " "+ j);
							String[] s1=(list.get(i)).get(timeStartIndex).split(":");
							String[] s2=list.get(j).get(timeStartIndex).split(":");
							
							//get initial end time of class
							String[] ei1=list.get(i).get(timeEndIndex).split(":");
							String[] ei2=list.get(j).get(timeEndIndex).split(":");
							
							//get end time of class to add transit time to
							String[] e1=list.get(i).get(timeEndIndex).split(":");
							String[] e2=list.get(j).get(timeEndIndex).split(":");
							
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
							
							//get class section
							String class1=list.get(i).get(classSectionIndex);
							String class2=list.get(j).get(classSectionIndex);
							
							//check for conflicts
							Boolean conflictFound=false;
							if(Integer.parseInt(s1[0])>Integer.parseInt(ei2[0]) || (Integer.parseInt(s1[0])==Integer.parseInt(ei2[0]) && Integer.parseInt(s1[1])>=Integer.parseInt(ei2[1]))){
								if(Integer.parseInt(s1[0])<Integer.parseInt(e2[0])){
									conflictFound=true;
									//CONFLICT
									System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
								}else{
									if(Integer.parseInt(s1[0])==Integer.parseInt(e2[0])){
										if(Integer.parseInt(s1[1])<=Integer.parseInt(e2[1])){
											conflictFound=true;
											//CONFLICT
											System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
										}
									}
								}
							}
							
							//check for conflicts in opposite direction
							if(!conflictFound){
								if(Integer.parseInt(s2[0])>Integer.parseInt(ei1[0]) || (Integer.parseInt(s2[0])==Integer.parseInt(ei1[0]) && Integer.parseInt(s2[1])>=Integer.parseInt(ei1[1]))){
									if(Integer.parseInt(s2[0])<Integer.parseInt(e1[0])){
										//CONFLICT
										System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
									}else{
										if(Integer.parseInt(s2[0])==Integer.parseInt(e1[0])){
											if(Integer.parseInt(s2[1])<=Integer.parseInt(e1[1])){
												//CONFLICT
												System.out.print("Insufficient transit time between: " + class1 + " and "+ class2);
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
	}
}
