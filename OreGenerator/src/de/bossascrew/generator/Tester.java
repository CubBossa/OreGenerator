package de.bossascrew.generator;

import de.bossascrew.generator.utils.Dimension;
import de.bossascrew.generator.utils.RandomDistribution;
import de.bossascrew.generator.utils.Level;
import de.bossascrew.generator.utils.Ore;

public class Tester {

	public static void main(String[] args) {

		float genauigkeit = 1f;
		
		int stone = 0;
		int infStone = 0;
		int coal = 0;
		int iron = 0;
		int redst = 0;
		int lapis = 0;
		
		for(float i = 0; i < 8640 * genauigkeit; i++) {
			float Prozent = i / (8640 * genauigkeit) * 100;
			System.out.println(Prozent + "% geschafft");
			
			RandomDistribution randGen = new RandomDistribution();
			for(Ore o : Level.ONE.getOres(Dimension.OVERWORLD)) {
				if(o.prob != 0)
					randGen.addNumber(o.mat, o.prob);
			}
			switch (randGen.getDistributedRandomNumber()) {
			case INFESTED_STONE: infStone ++; break;
			case STONE: stone++; break;
			case COAL_ORE: coal++; break;
			case IRON_ORE: iron++; break;
			case REDSTONE_ORE: redst++; break;
			case LAPIS_ORE: lapis++; break;
			default:
				break;
			}
		}
		
		
		// 10.000 stein in xminuten
		// 144 stein in 1min.
		
		System.out.println("Resultate pro Stunde: ______________");
		System.out.println("stone: " + stone / genauigkeit);
		System.out.println("infStone: " + infStone / genauigkeit);
		System.out.println("coal: " + coal / genauigkeit);
		System.out.println("iron: " + iron / genauigkeit);
		System.out.println("redst: " + redst / genauigkeit);
		System.out.println("lapis: " + lapis / genauigkeit);
	}

}
