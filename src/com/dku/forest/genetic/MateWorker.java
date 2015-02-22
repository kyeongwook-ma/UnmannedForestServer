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

import java.util.concurrent.Callable;

import Agent.BaseAgent;

import com.dku.forest.ForestChromosome;
import com.dku.forest.ForestGeneticAlgorithm;

/**
 * MateWorker: This class is used in conjunction with a thread pool.
 * This allows the genetic algorithm to offload all of those calculations
 * to a thread pool.  
 * 
 * @author Jeff Heaton
 * @version 2.1
 */
public class MateWorker<ForestChromosome extends Chromosome<BaseAgent, ForestGeneticAlgorithm>> implements
		Callable<Integer> {
	private final ForestChromosome mother;
	private final ForestChromosome father;
	private final ForestChromosome child1;
	private final ForestChromosome child2;

	public MateWorker(final ForestChromosome mother, final ForestChromosome father,
			final ForestChromosome child1, final ForestChromosome child2) {
		this.mother = mother;
		this.father = father;
		this.child1 = child1;
		this.child2 = child2;
	}

	@SuppressWarnings("unchecked")
	public Integer call() throws Exception {
		this.mother.mate((Chromosome)this.father, 
				(Chromosome)this.child1, 
				(Chromosome)this.child2);
		return null;
	}

}
