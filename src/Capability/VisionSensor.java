package Capability;

import java.io.Serializable;

public class VisionSensor extends Sensor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VisionSensor(String deviceID, String deviceType,
			double deprecationCost, double sensitivity) {
		super(deviceID, deviceType, deprecationCost, sensitivity);
		// TODO Auto-generated constructor stub
	}

}
