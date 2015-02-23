package com.dku.forest;

import java.util.ArrayList;

import Agent.AgentInstances;
import Agent.BaseAgent;
import Controller.CurrentScore;
import Environment.CellInstances;
import Environment.ForestCell;
import Environment.ForestCell.ForestDensity;
import Environment.ForestCell.GeoFeature;
import Environment.Weather.WeatherCondition;
import Policy.MonitorForestStatusPolicy;

import com.dku.exception.NeuralNetworkError;
import com.dku.forest.genetic.Chromosome;

public class ForestChromosome extends
		Chromosome<BaseAgent, ForestGeneticAlgorithm> {

	private ArrayList<BaseAgent> iniList = AgentInstances.getinitList();
	private ArrayList<BaseAgent> tempList = new ArrayList<BaseAgent>();
	private ArrayList<BaseAgent> curList = AgentInstances.getAgentList();
	private ArrayList<BaseAgent> optList = new ArrayList<BaseAgent>();
	private double[][] costMatrix = new double[9][11];
	private double[][] benefitMatrix = new double[9][11];
	private double[][] scoreMatrix = new double[9][11];
	private static int cellSize = CellInstances.getCellList().size();
	private double optScore;
	private double prevScore;
	private double curScore;
	final BaseAgent genes[] = new BaseAgent[iniList.size()];

	ForestChromosome(final ForestGeneticAlgorithm owner) {
		this.setGeneticAlgorithm(owner);	
		
		for (int i = 0; i < iniList.size(); i++) {
			curList.set(i, iniList.get(i));
			optList.add(iniList.get(i));
		}
		
		for (int i = 0; i < 181440; i++) {
			if (this.placementTest(curList)) {								
				break;
			} else {
				curList = this.next_permutation(curList);
				curList = this.next_permutation(curList);				
			}			
		}
		
		for (int i = 0; i < curList.size(); i++) {			
			genes[i] = curList.get(i);
		}
		setGenes(genes);
		calculateCost();		
	}

	@Override
	public void calculateCost() throws NeuralNetworkError {
		// TODO Auto-generated method stub
		this.costMatrix = this.makeCostMatrix();
		this.benefitMatrix = this.makeBenefitMatrix();
		this.scoreMatrix = this.makeScoreMatrix(costMatrix, benefitMatrix);

		if (placementTest(curList)) {
			curScore = this.getScoreValueFromMatrix(curList);
			if (prevScore < curScore) {
				for (int j = 0; j < curList.size(); j++) {
					optList.set(j, curList.get(j));
				}
				prevScore = curScore;
				optScore = curScore;
				CurrentScore.OptimalScore = optScore;
			}
		} else
			optScore = 0;
		setCost(optScore);
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
		final int length = this.getGenes().length;
		final int iswap1 = (int) (Math.random() * length);
		final int iswap2 = (int) (Math.random() * length);
		final BaseAgent temp = getGene(iswap1);
		setGene(iswap1, getGene(iswap2));
		setGene(iswap2, temp);
	}

	public double[][] makeCostMatrix() {
		double cost = 0;
		double[][] costMatrix = new double[9][11];
		ArrayList<BaseAgent> agentList = AgentInstances.getinitList();
		ArrayList<ForestCell> cellList = CellInstances.getCellList();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 11; j++) {
				cost = (agentList.get(j).getFuelCost() * 0.3)
						+ (agentList.get(j).getDeprecationCost() * 0.3)
						+ (agentList.get(j).getSumOfDeviceDeprecation() * 0.1);

				if (cellList.get(i).getDensity().equals(ForestDensity.High)
						&& agentList.get(j).getAgentType()
								.equals("Jeep")) {
					cost = cost + (agentList.get(j).getOperatorCost() * 0.02);

				}

				// there must be operator in airplane
				if (agentList.get(j).getAgentType().equals("AirPlane")) {
					cost = cost + (agentList.get(j).getOperatorCost() * 0.02);
				}

				costMatrix[i][j] = cost;
				cost = 0;
			}
		}

		return costMatrix;
	}

	public double[][] makeBenefitMatrix() {
		double correctness = 0;
		double completeness = 0;
		double[][] benefitMatrix = new double[9][11];
		ArrayList<BaseAgent> agentList = AgentInstances.getinitList();
		ArrayList<ForestCell> cellList = CellInstances.getCellList();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 11; j++) {
				correctness = (agentList.get(j).getAvgOfSensor() * 0.3);
				if (cellList.get(i).getFeatureList()
						.contains(GeoFeature.Mountain)) {

					if (cellList.get(i).getWeather().getCondition()
							.equals(WeatherCondition.Rainy)) {
						completeness = (agentList.get(j)
								.getMountainRainMovingDistance() / CellInstances.monitoringDistance) * 0.7;
					}

					else {
						completeness = (agentList.get(j)
								.getMountainMovingDistance() / CellInstances.monitoringDistance) * 0.7;
					}
				}

				else {

					if (cellList.get(i).getWeather().getCondition()
							.equals(WeatherCondition.Rainy)) {
						completeness = (agentList.get(j)
								.getRainMovingDistance() / CellInstances.monitoringDistance) * 0.7;
					}

					else {
						completeness = (agentList.get(j)
								.getNormalMovingDistance() / CellInstances.monitoringDistance) * 0.7;
					}
				}
				benefitMatrix[i][j] = correctness + completeness;
			}
		}

		return benefitMatrix;
	}

	public double[][] makeScoreMatrix(double[][] costMatrix,
			double[][] benefitMatrix) {
		double[][] scoreMatrix = new double[9][11];

		for (int i = 0; i < scoreMatrix.length; i++) {
			for (int j = 0; j < scoreMatrix[0].length; j++) {
				scoreMatrix[i][j] = ((benefitMatrix[i][j] - costMatrix[i][j] + 1) * 0.5);
			}
		}
		return scoreMatrix;
	}

	public double getScoreValueFromMatrix(ArrayList<BaseAgent> list) {

		double score = 0;
		for (int i = 0; i < cellSize; i++) {
			score += this.scoreMatrix[i][Integer.parseInt(list.get(i)
					.getAgentID()) - 1];
		}
		return score;

	}

	public ArrayList<BaseAgent> next_permutation(ArrayList<BaseAgent> staticList) {
		int i,j;

		for (i = staticList.size() - 2; i >= 0; i--) {
			if (Integer.parseInt(staticList.get(i).getAgentID()) < Integer
					.parseInt(staticList.get(i + 1).getAgentID()))
				break;
		}
		
		if (i < 0) {
			for (int k = 0; k < tempList.size(); k++) {
				staticList.set(k, tempList.get(k));
			}
			System.out.println("reset :" + staticList);
			for(int o=0; o<1000000; o++){
				System.out.println();
			}			
			return staticList;
		}
		

		for (j = staticList.size() - 1; j > i; j--) {
			if (Integer.parseInt(staticList.get(j).getAgentID()) > Integer
					.parseInt(staticList.get(i).getAgentID()))
				break;
		}
		swap(staticList, i++, j);

		for (j = staticList.size() - 1; j > i; i++, j--) {
			swap(staticList, i, j);
		}

		return staticList;
	}

	public void swap(ArrayList<BaseAgent> curList, int x, int y) {
		BaseAgent temp;
		temp = curList.get(x);
		curList.set(x, curList.get(y));
		curList.set(y, temp);

	}

	public ArrayList<BaseAgent> getOptList() {
		return optList;
	}

	public void setOptList(ArrayList<BaseAgent> optList) {
		this.optList = optList;
	}

	public boolean placementTest(ArrayList<BaseAgent> curList) {
		boolean isValidConfiguration = false;

		for (int i = 0; i < cellSize; i++) // ��ġ ���ɼ� check�ϴ� �κ�
		{
			ForestCell cell = CellInstances.getCell(i);
			BaseAgent agent = curList.get(i);

			if (MonitorForestStatusPolicy.checkValidation(cell, agent, "OPT")) {
				isValidConfiguration = true;

			} else {
				isValidConfiguration = false;
				break;
			}

		}
		return isValidConfiguration;
	}
}
