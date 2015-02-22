package com.dku.forest;

import java.util.ArrayList;

import Agent.BaseAgent;

import com.dku.exception.NeuralNetworkError;
import com.dku.forest.genetic.GeneticAlgorithm;

public class ForestGeneticAlgorithm extends GeneticAlgorithm<ForestChromosome> {
	
	ArrayList<BaseAgent> list;
	
	public ForestGeneticAlgorithm(
			final int populationSize, final double mutationPercent,
			final double percentToMate, final double matingPopulationPercent,
			final int cutLength) throws NeuralNetworkError {
		this.setMutationPercent(mutationPercent);
		this.setMatingPopulation(matingPopulationPercent);
		this.setPopulationSize(populationSize);
		this.setPercentToMate(percentToMate);
		this.setCutLength(cutLength);
		this.setPreventRepeat(true);
		
	    setChromosomes(new ForestChromosome[getPopulationSize()]);
		for (int i = 0; i < getChromosomes().length; i++) {
			final ForestChromosome c = new ForestChromosome(this);
			setChromosome(i, c);
			setList(c.getOptList());			
		}		
		sortChromosomes();
	}
	
	

	public ArrayList<BaseAgent> getList() {
		return list;
	}

	public void setList(ArrayList<BaseAgent> list) {
		this.list = list;
	}
}
