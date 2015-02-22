package Controller;

import java.util.ArrayList;
import java.util.Date;
import Agent.BaseAgent;



public class MainController {
	
	static MonitorForestStatusController controller = new MonitorForestStatusController();
	static int stage = 1;
	static ArrayList<Long> duList = new ArrayList<Long>();	
	
	public static ArrayList<BaseAgent> startMonitorForestStatus()
	{	
		System.gc();
		ArrayList<BaseAgent> retList;			
		
		Date start2 = new Date();
		retList = controller.getOptimal_Genetic();
		duList.add(new Date().getTime() - start2.getTime());
		System.out.println("Duration nG:"+(new Date().getTime() - start2.getTime()));		
		return retList;
	}
}



