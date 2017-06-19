package fx_simulation;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents an animal in our simulation
 * @author 
 */
public abstract class Animal extends Organism{
    public static final int NORTH = 0;
    public static final int NORTH_EAST = 1;
    public static final int EAST = 2;
    public static final int SOUTH_EAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTH_WEST = 5;
    public static final int WEST = 6;
    public static final int NORTH_WEST = 7;   
    
    protected int speed;
    protected int direction;
    
    private int cycleFed;
    
    public Animal(Ecosystem vsimulation, Circle circle, int speed){
    	super(vsimulation, circle);
    	
        this.speed = speed;
        
        Random rnd = new Random();
        direction = rnd.nextInt(8);}
    
    
    public Animal(Ecosystem vsimulation, int x, int y, int speed, Color c){
		super(vsimulation, new Circle(x, y, 5, c));
	}
    
    public void move(){
        Random rnd = new Random();
        if(rnd.nextInt(100) < 95){
            return;
        }
        
        changeDirection();
        
        setCurrentX(getCurrentX() + getStepX());
        setCurrentY(getCurrentY() + getStepY());
    }
    
    public void stop(){
        speed = 0;
    }
    
    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    public int getStepX()
    {
        switch (direction)
        {
            case NORTH:
            case SOUTH:
                return 0;
            case WEST:
            case NORTH_WEST:
            case SOUTH_WEST:
                return speed * -1;
            case EAST:
            case NORTH_EAST:
            case SOUTH_EAST:
                return speed;
            default:
                return speed;
        }
    }
    
    public int getStepY(){
        switch (direction)
        {
            case EAST:
            case WEST:
                return 0;
            case NORTH:
            case NORTH_WEST:
            case NORTH_EAST:
                return speed * -1;
            case SOUTH:
            case SOUTH_WEST:
            case SOUTH_EAST:
                return speed;
            default:
                return speed;
        }
    }
    
    public void changeDirection(){
        Random rnd = new Random();
        int x = rnd.nextInt(100) + 1;
        if(x < 8){
            int new_dir;
            
            do
            {
                new_dir = rnd.nextInt(8);
            } while(new_dir == direction);
            
            direction = new_dir;
        }
    }
    
    /**
     * 
     * @param currentCycle
     */
    public void checkStarvation(int currentCycle){
    	if (currentCycle - cycleFed >= 50){
    		vsimulation.removeOrganism(this);
    	}
    }
    
    /**
     * 
     * @param organism
     */
    public void eat(Organism organism)
    {
    	cycleFed = vsimulation.getCurrentCycle();
    }
    
    @Override
    public void onCollisionEnter(Animal animal){
    	System.out.println(animal + " colided with " + this);
    }
    
    @Override
    public void onNextCycle(){
    	/*
    	 * Animals die of starvation if they don’t eat something for
    	 * 50 continuous cycles of the simulations. 
    	 */
    	checkStarvation(vsimulation.getCurrentCycle());
    	
    	/*
    	 * The animals (lions, zebras, and rabbits) should move randomly on the forest,
    	 * but should never move outside of it. They should pause for a random amount
    	 * of time at each location, before moving to their next location. 
    	 */	
    	move();
    	
    	for (Organism organism : vsimulation.getOrganisms()){
    		// skip self.
    		if (organism == this)
    		{
    			continue;
    		}
    		
    		if (getCurrentX() == organism.getCurrentX() && getCurrentY() >= organism.getCurrentY())
    		{
    			organism.onCollisionEnter(this);
    		}
    	}
        
        if (getCurrentX() <= 0){
        	setCurrentX(0);
        }
        
        if (getCurrentX() >= vsimulation.getCanvasWidth()){
            
        	setCurrentX(vsimulation.getCanvasWidth() - 25);
        }
        
        if (getCurrentY() <= 0){
        	setCurrentY(0);
        }
        if (getCurrentY() >= vsimulation.getCanvasHeight()){
        	setCurrentY(vsimulation.getCanvasHeight() - 25);
        }
    }
}