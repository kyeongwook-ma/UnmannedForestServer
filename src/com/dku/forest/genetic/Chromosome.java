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
import java.util.HashSet;
import java.util.Set;

import Agent.AgentInstances;
import Agent.BaseAgent;

import com.dku.exception.NeuralNetworkError;
import com.dku.forest.ForestChromosome;
/**
 * Chromosome: Implements a chromosome to genetic algorithm.
 * This is an abstract class.  Other classes are provided in this
 * book that use this base class to train neural networks or
 * provide an answer to the traveling salesman problem.
 * 
 * Lifeforms in this genetic algorithm consist of one single 
 * chromosome each.  Therefore, this class represents a virtual
 * lifeform.  The chromosome is a string of objects that represent
 * one solution.  For a neural network, this string of objects
 * usually represents the weight and threshold matrix.
 * 
 * Chromosomes are made up of genes.  These are of the generic type
 * GENE_TYPE.  For a neural network this type would most likely
 * be double values.  
 * 
 * @author Jeff Heaton
 * @version 2.1
 */

abstract public class Chromosome<BaseAgent, ForestGeneticAlgorithm extends GeneticAlgorithm<ForestChromosome>>
		implements Comparable<Chromosome<BaseAgent, ForestGeneticAlgorithm >> {

	private double cost;
	private BaseAgent[] genes;	
	private ForestGeneticAlgorithm geneticAlgorithm;	
	abstract public void calculateCost() throws NeuralNetworkError;
	

	public int compareTo(final Chromosome<BaseAgent, ForestGeneticAlgorithm> other) {
		if (getCost() > other.getCost()) {
			return 1;
		} else {
			return -1;
		}
	}

	public double getCost() {
		return this.cost;
	}
	
	public BaseAgent getGene(final int gene) {
		return this.genes[gene];
	}
	
	public BaseAgent[] getGenes() {
		return this.genes;
	}
	
	public ForestGeneticAlgorithm getGeneticAlgorithm() {
		return this.geneticAlgorithm;
	}
	
	private BaseAgent getNotTaken(final Chromosome<BaseAgent, ForestGeneticAlgorithm> source,
			final Set<BaseAgent> taken) {
		final int geneLength = source.size();

		for (int i = 0; i < geneLength; i++) {
			final BaseAgent trial = source.getGene(i);
			if (!taken.contains(trial)) {
				taken.add(trial);
				return trial;
			}
		}

		return null;
	}	

	public void mate(final Chromosome<BaseAgent, ForestGeneticAlgorithm> father,
			final Chromosome<BaseAgent, ForestGeneticAlgorithm> offspring1,
			final Chromosome<BaseAgent, ForestGeneticAlgorithm> offspring2)
			throws NeuralNetworkError {		
		final int geneLength = getGenes().length;
		final int cutpoint1 = (int) (Math.random() * (geneLength - getGeneticAlgorithm()
				.getCutLength()));
		final int cutpoint2 = cutpoint1 + getGeneticAlgorithm().getCutLength();
		final Set<BaseAgent> taken1 = new HashSet<BaseAgent>();
		final Set<BaseAgent> taken2 = new HashSet<BaseAgent>();
	
		for (int i = 0; i < geneLength; i++) {
			if ((i < cutpoint1) || (i > cutpoint2)) {
			} else {
				offspring1.setGene(i, father.getGene(i));
				offspring2.setGene(i, this.getGene(i));
				taken1.add(offspring1.getGene(i));
				taken2.add(offspring2.getGene(i));
			}
		}
	
		for (int i = 0; i < geneLength; i++) {
			if ((i < cutpoint1) || (i > cutpoint2)) {
				if (getGeneticAlgorithm().isPreventRepeat()) {
					offspring1.setGene(i, getNotTaken(this, taken1));
					offspring2.setGene(i, getNotTaken(father, taken2));
				} else {
					offspring1.setGene(i, this.getGene(i));
					offspring2.setGene(i, father.getGene(i));
				}
			}
		}
	
		if (Math.random() < this.geneticAlgorithm.getMutationPercent()) {
			offspring1.mutate();
		}
		if (Math.random() < this.geneticAlgorithm.getMutationPercent()) {
			offspring2.mutate();
		}		
	
		offspring1.calculateCost();
		offspring2.calculateCost();

	}
	
	abstract public void mutate();
	
	public void setCost(final double cost) {
		this.cost = cost;
	}
	
	public void setGene(final int gene, final BaseAgent value) {
		this.genes[gene] = value;
	}
	
	public void setGenes(final BaseAgent[] genes) throws NeuralNetworkError {
		this.genes = genes;
	}
	
	public final void setGenesDirect(final BaseAgent[] genes)
			throws NeuralNetworkError {
		this.genes = genes;
	}
	
	public void setGeneticAlgorithm(final ForestGeneticAlgorithm geneticAlgorithm) {
		this.geneticAlgorithm = geneticAlgorithm;
	}
	
	private int size() {
		return this.genes.length;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[Chromosome: cost=");
		builder.append(getCost());
		return builder.toString();
	}

}
