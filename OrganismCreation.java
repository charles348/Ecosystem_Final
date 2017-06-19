package fx_simulation;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OrganismCreation{
    
	private final Ecosystem vsimulation;
	private final Random random;
	
	public OrganismCreation(Ecosystem vsimulation, Random random){
            
		this.vsimulation = vsimulation;
		this.random = random;
	}
	
	public Organism createOrganism(OrganismType orgType){
            
		final int x = random.nextInt((int) vsimulation.getCanvasHeight());
		final int y = random.nextInt((int) vsimulation.getCanvasWidth());
		
		switch (orgType){
                    
			case LION:
				return new Lion(vsimulation, new Circle(x, y, 5, Color.RED), 15); 
			case PLANT:
				return new Plant(vsimulation, new Circle(x, y, 5, Color.GREEN));
			case RABBIT:
				return new Rabbit(vsimulation, new Circle(x, y, 5, Color.WHITE), 15);
			case ZEBRA:
				return new Zebra(vsimulation, new Circle(x, y, 5, Color.GREY), 15);
			default:
				System.out.println("Invalid type");
		}
		
		return null;	
	}
}
