package Capability;

import java.io.Serializable;

public class BaseDevice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deviceID;
	private String deviceType;
	private double deprecationCost;
	
	public BaseDevice(String deviceID, String deviceType, double deprecationCost) {
		this.deviceID = deviceID;
		this.deviceType = deviceType;
		this.deprecationCost = deprecationCost;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public double getDeprecationCost() {
		return deprecationCost;
	}

	public void setDeprecationCost(double deprecationCost) {
		this.deprecationCost = deprecationCost;
	}
	
}
