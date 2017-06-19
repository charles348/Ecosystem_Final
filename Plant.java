package fx_simulation;

import java.util.Random;

import javafx.scene.shape.Circle;

/**
 * A plant in the simulation.
 * @author 
 */
public class Plant extends Organism{
    
	private final Random random = new Random();
	
	public Plant(Ecosystem vsimulation, Circle shape){
            
		super(vsimulation, shape);
	}

	@Override
	public void onNextCycle(){
            
		/*
		 * Occasionally a fire burns the forest. In case of a fire each plant has a chance of being burned (5%). 
		 */
		if (random.nextInt(100) <= 5){
                    
			System.out.println("Plant burned!");
			vsimulation.removeOrganism(this);
		}
	}

	@Override
	public void onCollisionEnter(Animal animal){
            
		System.out.println("Plant eaten by an animal");
		animal.eat(this);
	}
}
