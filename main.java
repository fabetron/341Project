 
import java.util.ArrayList;

public class main {

	public static void main (String[] args) {
		Conflicts conflicts = new Conflicts(); 
		
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
		
		conflicts.checkTransitConflict(list);
		conflicts.toString(list);
		
		
	}

}