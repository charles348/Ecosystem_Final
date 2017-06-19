package fx_simulation;

import java.util.Random;

import javafx.scene.shape.Circle;

public class Zebra extends Animal{
    
	public Zebra(Ecosystem vsimulation, Circle shape, int speed){
            
		super(vsimulation, shape, speed);
	}

	@Override
	public void eat(Organism organism){
            
		super.eat(organism);
		
		/*
		 * When a zebra is grazing, there is a 10% chance of a new zebra being created at the same location.
		 */
		Random rnd = new Random();
		if (rnd.nextInt(100) <= 10){
                    
			Organism org = vsimulation.newOrganism(OrganismType.ZEBRA);
			org.setCurrentX(getCurrentX());
			org.setCurrentY(getCurrentY());
		}
	}
}
