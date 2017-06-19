package fx_simulation;

import javafx.scene.shape.Circle;

/**
 * Represents a living organism in the simulation.
 * @author 
 */
public abstract class Organism{
    
	protected final Ecosystem vsimulation;
	private Circle circle;
	
	public Organism(Ecosystem vsimulation, Circle circle){
            
		this.vsimulation = vsimulation;
		this.circle = circle;
	}
	
	public Circle getCircle(){
            
		return circle;
	}
	
	public void setCircle(Circle circle){
            
		this.circle = circle;
	}
    
    public double getCurrentX(){
        
        return circle.getCenterX();
    }
    
    public double getCurrentY(){
        
        return circle.getCenterY();
    }
    
    public void setCurrentX(double x){
        
    	circle.setCenterX(x);
    }

    public void setCurrentY(double y){
        
    	circle.setCenterY(y);
    }
    
    /**
     * Called on every new cycle of the simulation.
     * Every organism changes accordingly
     */
    public abstract void onNextCycle();
    
    public abstract void onCollisionEnter(Animal animal);
}
