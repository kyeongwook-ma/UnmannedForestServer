package Capability;

import java.io.Serializable;

public class InfraredSensor extends Sensor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfraredSensor(String deviceID, String deviceType, double deprecationCost,
			double sensitivity) {
		super(deviceID, deviceType, deprecationCost, sensitivity);
	}

}
