package fx_simulation;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Rabbit extends Animal
{
	public Rabbit(Ecosystem vsimulation, Circle shape, int speed)
	{
		super(vsimulation, shape, speed);
	}
	
	public Rabbit(Ecosystem vsimulation, int x, int y, int speed, Color c)
	{
		super(vsimulation, x, y, speed, c);
	}
	
	@Override
	public void eat(Organism organism){
            
		super.eat(organism);
		
		/*
		 * When a rabbit is grazing, there is a 20% chance of a new rabbit being created at the same location.
		 */		
		Random rnd = new Random();
		if (rnd.nextInt(100) <= 20){
                    
			vsimulation.newOrganism(OrganismType.RABBIT);
		}
	}
}
