package project;

import java.util.ArrayList;
import java.util.Arrays;

public class Population {

	private Chromosome[] chroms ;
    ArrayList<Group> groups = new ArrayList<Group>();
    
	public Population() {}
	
	public Population(int len, ArrayList<Group> Gr) {
		chroms = new Chromosome[len];
		groups =Gr;
	}
	
	public Population initalizePopul() {
		for (int i = 0; i < chroms.length; i++) {
			chroms[i] = new Chromosome(groups).initalizeChrom();
			chroms[i].calcFitness();
			chroms[i].calcBenefit();
		}
		sortByFitness();
		return this;
	}
	
	public void sortByFitness() {
	    Arrays.sort(chroms);
	}
	
	public Chromosome[] getChromos() {
		return chroms;
	}

	@Override
	public String toString() {
		String chrom = "";
		for (int i = 0; i < chroms.length; i++) {
			chrom = chrom + "Chromosome #" + (i + 1) + chroms[i].toString() + "\n";
		}
		return chrom;
	}
	
}
