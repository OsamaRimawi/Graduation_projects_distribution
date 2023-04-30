package project;

import java.util.ArrayList;

public class Chromosome implements Comparable<Chromosome>{
	
    ArrayList<Group> groups = new ArrayList<Group>();
    private int[] genes;
    private double fitness = 0; //number of conflicting groups ID (the less the better)
    private int benefit = 0; // how much solution is close to the order of selection
    
	public Chromosome() {}
	
	public Chromosome(ArrayList<Group> Gr){
		groups = Gr;
		genes = new int [groups.size()];
	}


	public Chromosome initalizeChrom() {

		for (int i = 0; i < groups.size(); i++) {
			double rn = Math.random();
			if (rn > 0 && rn < 0.333) {
				genes[i] = groups.get(i).getOption1();
			} else if (rn > 0.333 && rn < 0.666) {
				genes[i] = groups.get(i).getOption2();
			} else
				genes[i] = groups.get(i).getOption3();
		}

		return this;
	}
	
	public double calcFitness(){
		fitness = 0;
		for (int i=0; i< genes.length; i++) {
			for (int j= i+1; j< genes.length; j++) {
				if (genes[i] ==  genes[j]) {
					fitness++;
					break;
				}
			}
		}
		return fitness;
	}
	
	public int calcBenefit(){
		benefit = 0;
		for (int i=0; i< genes.length; i++) {
			if (genes[i] == groups.get(i).getOption1())
				benefit = benefit + 3;
			else if (genes[i] == groups.get(i).getOption2())
				benefit = benefit + 2;
			else if (genes[i] == groups.get(i).getOption1())
				benefit = benefit + 1;
			}
		return benefit;
		}
	
	public double getFitness() {
		return fitness;
	}
	
	public int getBenefit() {
		return benefit;
	}
	
	public int[] getGenes() {
		return genes;
	}

	@Override
	public String toString() {
		String gene = "[";
		for (int i = 0; i < genes.length; i++) {
			gene = gene + genes[i] + " ";
		}
		gene = gene + "]";
		return gene + " Fitness = " + fitness + " Benefit = " + benefit;
	}
	
    public int compareTo(Chromosome o) {
    	if (this.fitness == o.fitness) // if we have equal Fitness sort based on Benefit 
        	return (int)(o.benefit - this.benefit);
    	
    	return (int)(this.fitness - o.fitness);
    	}
}

