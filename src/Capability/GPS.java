package Capability;

import java.io.Serializable;

public class GPS extends Sensor implements Serializable{

	/**
	 * implements Serializable
	 */
	private static final long serialVersionUID = 1L;

	public GPS(String deviceID, String deviceType, double deprecationCost,
			double sensitivity) {
		super(deviceID, deviceType, deprecationCost, sensitivity);

	}

}
