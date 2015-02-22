/**
 * Introduction to Neural Networks with Java, 2nd Edition
 * Copyright 2008 by Heaton Research, Inc. 
 * http://www.heatonresearch.com/books/java-neural-2/
 * 
 * ISBN13: 978-1-60439-008-7  	 
 * ISBN:   1-60439-008-5
 *   
 * This class is released under the:
 * GNU Lesser General Public License (LGPL)
 * http://www.gnu.org/copyleft/lesser.html
 */
package com.dku.forest.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import Agent.BaseAgent;

import com.dku.exception.NeuralNetworkError;
import com.dku.forest.ForestGeneticAlgorithm;

abstract public class GeneticAlgorithm<ForestChromosome extends Chromosome<BaseAgent, ForestGeneticAlgorithm>> {
	
	private int populationSize;	
	private double mutationPercent;	
	private double percentToMate;	
	private double matingPopulation;	
	private boolean preventRepeat;	
	private int cutLength;
	private ExecutorService pool;
	private ForestChromosome[] chromosomes;
	
	public ForestChromosome getChromosome(final int i) {
		return this.chromosomes[i];
	}
	
	public ForestChromosome[] getChromosomes() {
		return this.chromosomes;
	}
	
	public int getCutLength() {
		return this.cutLength;
	}
	
	public double getMatingPopulation() {
		return this.matingPopulation;
	}
	
	public double getMutationPercent() {
		return this.mutationPercent;
	}
	
	public double getPercentToMate() {
		return this.percentToMate;
	}
	
	public ExecutorService getPool() {
		return this.pool;
	}
	
	public int getPopulationSize() {
		return this.populationSize;
	}
	
	public boolean isPreventRepeat() {
		return this.preventRepeat;
	}
	
	public void iteration() throws NeuralNetworkError {

		final int countToMate = (int) (getPopulationSize() * getPercentToMate());
		final int offspringCount = countToMate * 2;
		int offspringIndex = getPopulationSize() - offspringCount;
		final int matingPopulationSize = (int) (getPopulationSize() * getMatingPopulation());

		final Collection<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();

		// mate and form the next generation
		for (int i = 0; i < countToMate; i++) {
			final ForestChromosome mother = this.chromosomes[i];
			final int fatherInt = (int) (Math.random() * matingPopulationSize);
			final ForestChromosome father = this.chromosomes[fatherInt];
			final ForestChromosome child1 = this.chromosomes[offspringIndex];
			final ForestChromosome child2 = this.chromosomes[offspringIndex + 1];

			final MateWorker<ForestChromosome> worker = new MateWorker<ForestChromosome>(
					mother, father, child1, child2);

			try {
				if (this.pool != null) {
					tasks.add(worker);
				} else {
					worker.call();
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}

			// mother.mate(father,chromosomes[offspringIndex],chromosomes[offspringIndex+1]);
			offspringIndex += 2;
		}

		if (this.pool != null) {
			try {
				this.pool.invokeAll(tasks, 120, TimeUnit.SECONDS);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

		// sort the next generation
		sortChromosomes();
	}

	public void setChromosome(final int i, final ForestChromosome value) {
		this.chromosomes[i] = value;
	}
	
	public void setChromosomes(final ForestChromosome[] chromosomes) {
		this.chromosomes = chromosomes;
	}
	
	public void setCutLength(final int cutLength) {
		this.cutLength = cutLength;
	}
	
	public void setMatingPopulation(final double matingPopulation) {
		this.matingPopulation = matingPopulation;
	}
	
	public void setMutationPercent(final double mutationPercent) {
		this.mutationPercent = mutationPercent;
	}
	
	public void setPercentToMate(final double percentToMate) {
		this.percentToMate = percentToMate;
	}
	
	public void setPool(final ExecutorService pool) {
		this.pool = pool;
	}
	
	public void setPopulationSize(final int populationSize) {
		this.populationSize = populationSize;
	}
	
	public void setPreventRepeat(final boolean preventRepeat) {
		this.preventRepeat = preventRepeat;
	}

	public void sortChromosomes() {
		Arrays.sort(this.chromosomes);
	}

}
