package fx_simulation;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Lion extends Animal{
    
	public Lion(Ecosystem vsimulation, Circle circle, int speed){
            
		super(vsimulation, circle, speed);
	}
	
	public Lion(Ecosystem vsimulation, int x, int y, int speed, Color c){
            
		super(vsimulation, x, y, speed, c);
	}

	@Override
	public void onCollisionEnter(Animal animal){
            
		if (animal instanceof Lion){
                    
			System.out.println("Colided with another lion!");
			return;
		}
		
		System.out.println("Lion ate animal " + animal);
		Random rnd = new Random();
		if (rnd.nextInt(100) <= 70){
                    
			/*
			 * Lions eat zebras and rabbits when they collide with a chance of 70%.
			 */
			eat(animal);
		}
	}

	@Override
	public void eat(Organism organism){
            
		super.eat(organism);
		vsimulation.removeOrganism(organism);
	}
}
