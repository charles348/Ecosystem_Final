package fx_simulation;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Ecosystem extends Application{
    
    private final List<Organism> organisms = new CopyOnWriteArrayList<Organism>();
    private OrganismCreation orgCreation;
    private int currentCycle;
    private Pane canvas;
    private Scene scene;
    
    private int lionCount;
    private int plantCount;
    private int rabbitCount;
    private int zebraCount;
    
    private Random rnd;
    
    private Label currentCycleLabel;
    
    public static void main(String [] args){
        
        launch(args);
    }

    @Override
    public void start(Stage stage){   	
        
    	init(stage);
    }
    
    private void init(Stage stage){
        
    	HBox plantHBox = new HBox();
    	Label plantLable = new Label("Plant Count ");
    	TextField plantTextField = new TextField("");
    	plantHBox.getChildren().addAll(plantLable, plantTextField);
    	plantHBox.setAlignment(Pos.CENTER);
    	
    	HBox lionHBox = new HBox();
    	Label lionLable = new Label("Lion Count ");
    	TextField lionTextField = new TextField("");
    	lionHBox.getChildren().addAll(lionLable, lionTextField);
    	lionHBox.setAlignment(Pos.CENTER);
    	
    	HBox zebraHBox = new HBox();
    	Label zebraLable = new Label("Zebra Count ");
    	TextField zebraTextField = new TextField("");
    	zebraHBox.getChildren().addAll(zebraLable, zebraTextField);
    	zebraHBox.setAlignment(Pos.CENTER);
    	
    	HBox rabbitHBox = new HBox();
    	Label rabbitLable = new Label("Rabbit Count ");
    	TextField rabbitTextField = new TextField("");
    	rabbitHBox.getChildren().addAll(rabbitLable, rabbitTextField);
    	rabbitHBox.setAlignment(Pos.CENTER);
    	
    	final VBox spec = new VBox(10);
    	spec.setAlignment(Pos.CENTER);
        spec.getChildren().addAll(plantHBox, lionHBox, zebraHBox, rabbitHBox);
        
        final Button startButton = new Button("Start");
        startButton.setOnAction((ActionEvent e) -> 
        {
        	plantCount = validateText(plantTextField);
        	lionCount = validateText(lionTextField);
        	zebraCount = validateText(zebraTextField);
        	rabbitCount = validateText(rabbitTextField);
        	
        	if (plantCount > 0 && lionCount > 0
        			&& zebraCount > 0 && rabbitCount > 0)
        	{
            	setMainStage(stage);
        	}
        });
        
        final VBox vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(spec, startButton);
        
        scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
    
    private int validateText(TextField textField){
        
    	try{    	
            
    		return Integer.parseInt(textField.getText());
    	}
    	catch (NumberFormatException e)
    	{
    		System.out.println("NFE! Returning 1");
    		return 1;
    	}
    }
    
    private void setMainStage(Stage stage){
        
        canvas = new Pane();
        canvas.setMinHeight(500);
        canvas.setMinWidth(500);
        canvas.setMaxHeight(500);
        canvas.setMaxWidth(500);
        canvas.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        rnd = new Random();
        this.orgCreation = new OrganismCreation(this, rnd);
        
        for (int i = 0; i < plantCount; i++){
            
        	addOrganism(orgCreation.createOrganism(OrganismType.PLANT));
        }
        
        for (int i = 0; i < lionCount; i++){
            
        	addOrganism(orgCreation.createOrganism(OrganismType.LION));
        }
        
        for (int i = 0; i < zebraCount; i++){
            
        	addOrganism(orgCreation.createOrganism(OrganismType.ZEBRA));
        }
        
        for (int i = 0; i < plantCount; i++){
            
        	addOrganism(orgCreation.createOrganism(OrganismType.RABBIT));
        }
        
        stage.setTitle("Ecosystem Simulation");
        
        currentCycleLabel = new Label("Current Cycle: " + currentCycle);       
        final Button nextCycleButton = new Button("Next Cycle");
        nextCycleButton.setOnAction((ActionEvent e) -> 
        {
        	simulateOnce();
        });
        
        final HBox buttonHb1 = new HBox(10);
        buttonHb1.setAlignment(Pos.CENTER);
        buttonHb1.getChildren().addAll(nextCycleButton, currentCycleLabel);
        
        final VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(buttonHb1);
        vbox.getChildren().add(canvas);
        
        scene = new Scene(vbox, 600, 600);
        stage.setScene(scene);
        stage.show();
        
        //simulate();
    }
    
    public void addOrganism(Organism organism){
        
    	organisms.add(organism);
    	canvas.getChildren().add(organism.getCircle());
    }
    
    public void removeOrganism(Organism organism){
        
    	organisms.remove(organism);
    	canvas.getChildren().remove(organism.getCircle());
    }
    
    public List<Organism> getOrganisms(){
        
    	return organisms;
    }
    
    public Organism newOrganism(OrganismType orgType){
        
    	Organism organism = orgCreation.createOrganism(orgType);
    	addOrganism(organism);  	
    	return organism;
    }
    
    public int getCurrentCycle(){
        
    	return currentCycle;
    }
    
    public double getCanvasWidth(){
        
    	return canvas.getMaxWidth();
    }
    
    public double getCanvasHeight(){
        
    	return canvas.getMaxHeight();
    }
    
    public void simulate(){
        
        Timeline timeline = new Timeline();      
        timeline.currentTimeProperty().addListener(new InvalidationListener()
        {	
            public void invalidated(Observable ov){
                
                simulateOnce();
            }
        }
        );
	
        Duration time = new Duration(10000);
        KeyFrame keyFrame = new KeyFrame(time);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(20);
        timeline.play();
    }
    
    public void simulateOnce(){
        
    	currentCycle++;
    	currentCycleLabel.setText("Current Cycle: " + currentCycle);
        for (Organism organism : organisms){
            
        	organism.onNextCycle();
        }
		
		/*
		 * New plants are created randomly (1% chance in each cycle of the simulation).
		 */
		if (rnd.nextInt(100) <= 1){
                    
			newOrganism(OrganismType.PLANT);
			System.out.println("New plant created!");
		}
    }
}
