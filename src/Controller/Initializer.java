package Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import Agent.AgentInstances;
import Agent.BaseAgent;
import Agent.Jeep;
import Environment.CellInstances;
import Environment.ForestCell;

public class Initializer {

	public static void initialize() {		
		makeAgent();
	}
	
	public static void change(ArrayList<ForestCell> cellList) {
		makeEnv(cellList);		
	}

	private static void makeEnv(ArrayList<ForestCell> cellList) {
		CellInstances.setCellList(cellList);
	}

	private static void makeAgent() {

		BufferedReader in;

		try {
			in = new BufferedReader(new FileReader("./Data/agentSpec.txt"));

			String s;
			while ((s = in.readLine()) != null) {

				if (s.startsWith("//"))
					continue;
				String[] spec;
				spec = s.split("\t");

				if (spec[1].equals("Jeep")) {
					Jeep ag = new Jeep(spec[0], spec[1],
							Double.parseDouble(spec[2]),
							Double.parseDouble(spec[3]),
							Double.parseDouble(spec[4]),
							Double.parseDouble(spec[5]),
							Double.parseDouble(spec[6]),
							Double.parseDouble(spec[7]),
							Double.parseDouble(spec[8]),
							Double.parseDouble(spec[9]),
							Double.parseDouble(spec[10]),
							Double.parseDouble(spec[11]),
							Double.parseDouble(spec[12]),
							Double.parseDouble(spec[13]),
							Double.parseDouble(spec[14]),
							Integer.parseInt(spec[15]),
							Integer.parseInt(spec[16]));
					AgentInstances.getAgentList().add(ag);
					AgentInstances.getinitList().add(ag);
					AgentInstances.gettempList().add(ag);

				}

				else {
					BaseAgent ag = new BaseAgent(spec[0], spec[1],
							Double.parseDouble(spec[2]),
							Double.parseDouble(spec[3]),
							Double.parseDouble(spec[4]),
							Double.parseDouble(spec[5]),
							Double.parseDouble(spec[6]),
							Double.parseDouble(spec[7]),
							Double.parseDouble(spec[8]),
							Double.parseDouble(spec[9]),
							Double.parseDouble(spec[10]),
							Double.parseDouble(spec[11]),
							Double.parseDouble(spec[12]),
							Double.parseDouble(spec[13]),
							Double.parseDouble(spec[14]),
							Integer.parseInt(spec[15]),
							Integer.parseInt(spec[16]));
					AgentInstances.getAgentList().add(ag);
					AgentInstances.getinitList().add(ag);
					AgentInstances.gettempList().add(ag);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
