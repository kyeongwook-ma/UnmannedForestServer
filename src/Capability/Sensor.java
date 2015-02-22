package Capability;

import java.io.Serializable;

public class Sensor extends BaseDevice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double sensitivity;
	
	public Sensor(String deviceID, String deviceType, double deprecationCost,
			double sensitivity) {
		super(deviceID, deviceType, deprecationCost);
		
		this.sensitivity = sensitivity;
	}

	public double getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(double sensitivity) {
		this.sensitivity = sensitivity;
	}
	
	
	
	
}
