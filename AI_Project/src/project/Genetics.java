package project;

import java.util.ArrayList;

public class Genetics {
	public static final int NUM_OF_BEST_CHROMOSOMES = 2;
	public static final int TOURNAMENT_SIZE = 6;
	public static final double MUTATION_RATE = 0.2;
	public static final int POPULATION_SIZE = 8;

	ArrayList<Group> groups = new ArrayList<Group>();

	public Genetics() {
	}

	public Genetics(ArrayList<Group> Gr) {
		groups = Gr;
	}

	public Population Reproduction(Population oldPopul) {
		return populationMutation(populationCrossover(oldPopul));
	}

	public Population populationCrossover(Population oldPopul) {
		Population newPopul = new Population(POPULATION_SIZE, groups);
		for (int i = 0; i < NUM_OF_BEST_CHROMOSOMES; i++) {
			newPopul.getChromos()[i] = oldPopul.getChromos()[i];
		}
		for (int i = NUM_OF_BEST_CHROMOSOMES; i < oldPopul.getChromos().length; i++) {
			Chromosome c1 = tournamentSelection(oldPopul).getChromos()[0];
			Chromosome c2 = tournamentSelection(oldPopul).getChromos()[0];
			newPopul.getChromos()[i] = chromosomeCrossover(c1, c2);
		}
		newPopul.sortByFitness();
		return newPopul;
	}

	public Population populationMutation(Population oldPopul) {
		Population newPopul = new Population(POPULATION_SIZE, groups);
		for (int i = 0; i < NUM_OF_BEST_CHROMOSOMES; i++) {
			newPopul.getChromos()[i] = oldPopul.getChromos()[i];
		}
		for (int i = NUM_OF_BEST_CHROMOSOMES; i < oldPopul.getChromos().length; i++) {
			newPopul.getChromos()[i] = chromosomeMutation(oldPopul.getChromos()[i]);
		}
		newPopul.sortByFitness();
		return newPopul;
	}

	private Chromosome chromosomeCrossover(Chromosome c1, Chromosome c2) {
		Chromosome newChrom = new Chromosome(groups);
		for (int i = 0; i < groups.size(); i++) {
			if (Math.random() > 0.5) {
				newChrom.getGenes()[i] = c1.getGenes()[i];
			} 
			else
				newChrom.getGenes()[i] = c2.getGenes()[i];
		}
		newChrom.calcBenefit();
		newChrom.calcFitness();
		return newChrom;
	}

	private Chromosome chromosomeMutation(Chromosome oldChrom) {
		Chromosome newChrom = new Chromosome(groups);
		for (int i = 0; i < groups.size(); i++) {
			if (Math.random() < 0.25) {
				double rn = Math.random();
				if (rn > 0 && rn < 0.333) {
					newChrom.getGenes()[i] = groups.get(i).getOption1();
				} else if (rn > 0.333 && rn < 0.666) {
					newChrom.getGenes()[i] = groups.get(i).getOption2();
				} else
					newChrom.getGenes()[i] = groups.get(i).getOption3();
			} 
			else
				newChrom.getGenes()[i] = oldChrom.getGenes()[i];
		}
		newChrom.calcBenefit();
		newChrom.calcFitness();
		return newChrom;
	}

	private Population tournamentSelection(Population oldPopul) {
		Population newPopul = new Population(TOURNAMENT_SIZE, groups);
		for (int i = 0; i < TOURNAMENT_SIZE; i++) {
			int rn = (int) (Math.random() * oldPopul.getChromos().length);
			newPopul.getChromos()[i] = oldPopul.getChromos()[rn];
		}
		newPopul.sortByFitness();
		return newPopul;
	}
}
