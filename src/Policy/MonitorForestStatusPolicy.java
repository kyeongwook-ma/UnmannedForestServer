package Policy;

import Agent.BaseAgent;
import Agent.BaseAgent.AgentType;
import Agent.Jeep;
import Environment.ForestCell;
import Environment.ForestCell.ForestDensity;
import Environment.ForestCell.GeoFeature;
import Environment.Weather.WeatherCondition;

public class MonitorForestStatusPolicy {
	public static boolean checkValidation(ForestCell c, BaseAgent a, String confType)
	{
		if(a == null)
			return true;
		
		//가시거리가 5km 이하면 헬리콥터는 배치될 수 없다.
		if(c.getWeather().getVisibility() < 5.0 && a.getAgentType().equals(AgentType.Helicopter))
		{
			return false;
		}
		
		// 바람이 20m/s 이상이면 헬리콥터와 UAV, 비행기는 배치될 수 없다.
		if(c.getWeather().getWind().getVelocity() > 20)
		{
			if(a.getAgentType().equals(AgentType.Helicopter) || a.getAgentType().equals(AgentType.UAV) || a.getAgentType().equals(AgentType.AirPlane))
			{
				return false;
			}
		}
		
		//눈오는 날은 JEEP 배치 불가
		if(c.getWeather().getCondition().equals(WeatherCondition.Snowy) && a.getAgentType().equals(AgentType.Jeep))
		{
			return false;
		}
		
		//호수나 강은 JEEP배치 불가
		if(c.getFeatureList().contains(GeoFeature.Lake) || c.getFeatureList().contains(GeoFeature.River))
		{
			if(a.getAgentType().equals(AgentType.Jeep))
				return false;
		}
		
		//연료가 부족한 agent는 제외한다.
		if(confType.equals("OPT"))
		{
			if(a.getCurrentOptFuel() < a.getFuelEfficiency())
			{
				return false;
			}
		}
		
		else if(confType.equals("HUR"))
		{
			if(a.getCurrentHurFuel() < a.getFuelEfficiency())
			{
				return false;
			}
		}
		
		
		return true;
	}

}
