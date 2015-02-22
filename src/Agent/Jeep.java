package Agent;

import java.io.Serializable;

public class Jeep extends BaseAgent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Jeep(String agentID, String agentType, double operatorCost,
			double deprecationCost, double fuelCost, double visionSensitivity,
			double infraredSensitivity, double GPSSensitibity,
			double visionDeprecation, double infraredDeprecation,
			double GPSDeprecation, double normalMovingDistance,
			double rainMovingDistance, double mountainMovingDistance,
			double mountainRainMovingDistance,
			int initialFuel, int fuelEfficiency) {
		super(agentID, agentType, operatorCost, deprecationCost, fuelCost,
				visionSensitivity, infraredSensitivity, GPSSensitibity,
				visionDeprecation, infraredDeprecation, GPSDeprecation,
				normalMovingDistance, rainMovingDistance, mountainMovingDistance,
				mountainRainMovingDistance, initialFuel, fuelEfficiency);
		
	}

}
