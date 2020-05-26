package socialdistancing;

import com.amazonaws.samples.addData;

//entry point
public class _Main {
	Settings sets;
	Control control;
//
	/* 
	 * Entry point, starts Settings Panel
	 */
	public static void main( String[] args ) {
		//this is instance of main
		_Main m = new _Main();
		
		//start settings panel
		m.sets = new Settings(m);
		m.sets.setVisible(true); //activates Settings panel
		
		System.out.println("really done");
	}
	
	/*
	 * Call back to start a new simulation with user defined simulation Settings
	 */
	public void simulation() {
		//new Simulation for each run from Settings panel
		control = new Control(sets); // sets are critical for Simulation
		control.runSimulation();
	}
	
	public void pushData()
	{		
		addData addBoy = new addData();

		for(int i = 0; i<Control.walls.length;i++)
		{
			String buildName = "Stats#b";
			buildName+=i;
		
			cloudPushing floaty = new cloudPushing(buildName, Control.walls[i].buildingName, Control.walls[i].gridRegion, 
				Control.walls[i].msOfLastUpdate, Control.walls[i].activeInfected, Control.walls[i].activeRoamers);
			floaty.start();

			/*addBoy.addBuild(buildName, Control.walls[i].buildingName, Control.walls[i].gridRegion, 
				Control.walls[i].msOfLastUpdate, Control.walls[i].activeInfected, Control.walls[i].activeRoamers);*/
			
			System.out.println(i);
		}
		
		for(int x = 0; x<Control.model.size();x++)
		{
			String  healthStatus;
			
			switch(Control.model.get(0).state)
			{
				case candidate:
					healthStatus = "candidate";
					break;
				case infected:
					healthStatus = "infected";
					break;
				case recovered:
					healthStatus = "recovered";
					break;
				case died:
					healthStatus = "died";
					break;
				default:
					healthStatus = "n/a";
			
			}
		
			cloudPushing floater = new cloudPushing(Control.model.get(x).name, healthStatus,
				Control.model.get(x).roams, Control.model.get(x).peopleCollisions, Control.model.get(x).infectedBy, 
				Control.model.get(x).infectedTime, Control.model.get(x).deathTime);
			floater.start();
			
			/*addBoy.addPers(Control.model.get(x).name, healthStatus,
				Control.model.get(x).roams, Control.model.get(x).peopleCollisions, Control.model.get(x).infectedBy, 
				Control.model.get(x).infectedTime, Control.model.get(x).deathTime);*/
			
			System.out.println(x);	
		
		}
	}
}


class cloudPushing extends Thread
{
	String sortKey, building,  gridRegion;
	int msOfLastUpdate,  activeInfected, activeRoamers;
	
	String healthStatus;
	boolean isRoamer;
	int collisions;
	String infectedBy;
	int infectionLength, deathTime;
	String dataType;
	
	public cloudPushing(String sortKey,String building, String gridRegion, int msOfLastUpdate, int activeInfected,int activeRoamers)
	{
		this.sortKey = sortKey;
		this.building = building;
		this.gridRegion = gridRegion;
		this.msOfLastUpdate = msOfLastUpdate;
		this.activeInfected = activeInfected;
		this.activeRoamers = activeRoamers;
		dataType = "building";
	}
	
	public cloudPushing(String sortKey, String healthStatus, boolean isRoamer, int collisions, String infectedBy, int infectionLength, int deathTime)
	{
		this.sortKey = sortKey;
		this.healthStatus = healthStatus;
		this.isRoamer = isRoamer;
		this.collisions = collisions;
		this.infectedBy = infectedBy;
		this.infectionLength = infectionLength;
		this.deathTime = deathTime;
		dataType = "person";
	}
	
	public void run()
	{
		addData addBoy = new addData();

		switch(dataType)
		{
		case "building":
			addBoy.addBuild(sortKey, building, gridRegion, 
					msOfLastUpdate, activeInfected, activeRoamers);
			break;
			
		case "person":
			addBoy.addPers(sortKey, healthStatus,
					isRoamer, collisions, infectedBy, 
					infectionLength, deathTime);
			break;
		}
	}

}