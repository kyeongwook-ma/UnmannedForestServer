package Controller;

import java.util.ArrayList;

import Agent.BaseAgent;

import com.dku.forest.ForestGeneticAlgorithm;

public class MonitorForestStatusController {
	
	public static final int POPULATION_SIZE = 100;
	public static final double MUTATION_PERCENT = 0.10;
	public static final double percentToMate = 0.25;
	public static final double matingPopulation = 0.5;
	public static final int cutLenght = 2;	
	
//	AgentInstances agentList = new AgentInstances();		
	private ArrayList<BaseAgent> optList2 = new ArrayList<BaseAgent>();
	public ArrayList<Double> minList = new ArrayList<Double>();
	public ArrayList<Double> maxList = new ArrayList<Double>();	
	public double optCost;				
	
	protected ForestGeneticAlgorithm genetic;	
	
	
	
	public ArrayList<BaseAgent> getOptimal_Genetic(){
		
		this.genetic = new ForestGeneticAlgorithm(POPULATION_SIZE,	MUTATION_PERCENT,
				percentToMate, matingPopulation, cutLenght);		
		
		// curlist가 적합한 curlist 인지를 판단 아니면 다시 mutate
		this.genetic.iteration();
		optCost = genetic.getChromosome(POPULATION_SIZE-1).getCost();
		setOptCost(optCost);
		optList2 = genetic.getList();		
		
		return optList2;	
	}
	
	public double getOptCost() {
		return optCost;
	}

	public void setOptCost(double optCost) {
		this.optCost = optCost;
	}	
}