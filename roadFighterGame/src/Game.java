import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.sound.midi.Soundbank;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class Game {
 
	//Constants
	private final static int WIDTH = 600;
	private final static int HEIGHT = 600;
	private final static int GREEN_FIELD_WIDTH = 150;
	private final static int ROAD_WIDTH = 300;
	
	// Sprites of game
	private static Sprite playerCar = new Sprite();
	private static ArrayList<Tree> leftTrees = new ArrayList<Tree>();
	private static ArrayList<Tree> rightTrees = new ArrayList<Tree>();
	private static ArrayList<RivalCar> rivalCars = new ArrayList<RivalCar>();
	private static ArrayList<Sprite> roadLines = new ArrayList<Sprite>();

	//Game speed features
	private static double acceleration = 3000;
	private static double negativeAcceleration = -1000;
	private  static double horizontalAcceleration = 9000;
	private  static double horizontalNegAcceleration = -4000;
	private static double verticalSpeedLimit = 1000;
	private  static double horizontalSpeedLimit = 1000;

	
	
	
	
	//Media
	private final static String treeSrc = "drawable/tree.png";
	private final static String treeShadowSrc = "drawable/treeShadow.png";
	private final static String lineSrc = "drawable/line.png";
	private final static String carSrc = "drawable/car.png";
	private final static String carCrashedSrc = "drawable/crashedCar.png";
	private final static String rivalCarSrc = "drawable/rivalCar.png";
	private final static String overtookRivalCarSrc = "drawable/overtookRivalCar.png";
	private final static String carSrcShadow = "drawable/carShadow.png";
	private final static String levelupSound = "sound/levelup.wav";
	private static Media sound = new Media(new File(levelupSound).toURI().toString());
	private static boolean soundPlayed = false;
	
	//Counters
	private static int score = 0;
	private static int overtookNum = 0;
	private static int level = 1;
	
	private static Random rand = new Random();
	
		
	public static void tickAndRender(GraphicsContext gc, HashSet<String> instantKeys, double time) {
		// If any sprite out of boundary then spawn it
		refreshSprites();
		
		// Reset all the velocities for proper acceleration
		resetVelocities(time, instantKeys);
		
		// Key actions
		if (instantKeys.contains("UP"))
			addVelocityToSprites(0, 1, time, instantKeys);
		if (instantKeys.contains("LEFT"))
			addVelocityToSprites(-1, 0, time, instantKeys);
		if (instantKeys.contains("RIGHT"))
			addVelocityToSprites(1, 0, time, instantKeys);
		

		moveSprites(time);
		
		rivalOvertaken();

        render(gc);
	}

	public static void loadGraphs() {
		//Trees
		Tree treeL, treeR;
		for (int i = 0; i < 6; i++) {
			treeL = new Tree();
			treeR = new Tree();
			treeL.setImage(treeSrc, treeShadowSrc);
			treeR.setImage(treeSrc, treeShadowSrc);
			treeL.setPosition(rand.nextInt((int) (150-20-treeL.getImage().getWidth())), HEIGHT-rand.nextInt(100)-i*200);
			treeR.setPosition(WIDTH-treeR.getImage().getWidth()-rand.nextInt(70), HEIGHT-rand.nextInt(100)-i*200);
			
			rightTrees.add(treeR);
			leftTrees.add(treeL);
		}
		
		//Road lines
		Sprite line;
		for (int i = 0; i < 22; i++) {
			line = new Sprite();
			line.setImage(lineSrc, "");
			line.setPosition(WIDTH/2-(line.getImage().getWidth()/2), HEIGHT - 30*(i+1) );
			roadLines.add(line);
		}
		
		//Main car
		playerCar.setImage(carSrc, carSrcShadow);
		playerCar.setPosition(300-(playerCar.getImage().getWidth()/2), 600-(playerCar.getImage().getHeight())-50);
		
		
		//Rival cars
		RivalCar rival;
		double xPosition = 0;
		double yPosition = 0;
		ArrayList<Sprite> allCars = new ArrayList<>();
		allCars.add(playerCar);
		for (int i = 0; i < 4; i++) {
			rival = new RivalCar();
			rival.setImage(rivalCarSrc, carSrcShadow);
			
			xPosition = 150 + rand.nextInt((int) (300-rival.getImage().getWidth()));
			yPosition = 175*(i-1);
			
			if (i==0) {
				yPosition = -175;
			}

			rival.setPosition(xPosition, yPosition);

			int carIndex = 0;
			while(carIndex < allCars.size()) {
				while(intersects(rival, allCars.get(carIndex))) {
					xPosition = 150 + rand.nextInt((int) (300-rival.getImage().getWidth()));
					rival.setPosition(xPosition, yPosition);
					carIndex=0;
				}
				carIndex++;				
			}
			allCars.add(rival);
			rivalCars.add(rival);			
		}
	}
	
	public static void render(GraphicsContext gc) {
		gc.clearRect(0, 0, 600,600);

		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, 600, 600);
		
		
		gc.setFill(Color.GRAY);
		gc.fillRect(150, 0, 300, 600);
		
		gc.setFill(Color.WHITE);
		gc.fillRect(130, 0, 20, 600);
		
		gc.setFill(Color.WHITE);
		gc.fillRect(450, 0, 20, 600);
		
		for (Sprite line : roadLines) {
			line.drawImage(gc);
		}
		
		playerCar.drawImage(gc);
		
		for (int i = 0; i<rightTrees.size(); i++) {
			rightTrees.get(i).drawImage(gc);
			leftTrees.get(i).drawImage(gc);
		}
		
		for (RivalCar rival : rivalCars) {
			rival.drawImage(gc);
		}
		
		Font theFont = Font.font( "WAREE", FontWeight.BOLD, 20 );
	    gc.setFont( theFont );
	    
	    gc.setFill( Color.WHITE );
	    gc.setStroke( Color.BLACK );
	    gc.fillText( "Score: "+String.valueOf(score)+"\nLevel: "+String.valueOf(level)+"\n%"+(overtookNum*5), 10, 40 );
	    gc.strokeText("Score: "+ String.valueOf(score)+"\nLevel: "+String.valueOf(level)+"\n%"+(overtookNum*5), 10, 40 );
	    
	    
	}
			
	public static void addVelocityToSprites(double x, double y, double time, HashSet<String> instantKeys) {
		
		if (x!=0) {
			if (playerCar.getPositionX()-150 <= 0) {
				playerCar.setVelocityX(0);
				if ((instantKeys.contains("RIGHT") && instantKeys.contains("LEFT")) || !instantKeys.contains("RIGHT")) {
					return;
				}
			}else if (playerCar.getPositionX()+playerCar.getImage().getWidth()+150 >= 600) {
				
				playerCar.setVelocityX(0);
				if ((instantKeys.contains("RIGHT") && instantKeys.contains("LEFT")) || !instantKeys.contains("LEFT")) {
					return;
				}
			}
			if (x*playerCar.getVelocityX() < horizontalSpeedLimit) {
				playerCar.speedUp(x*horizontalAcceleration*time, 0);
			}else {
				playerCar.setVelocityX(x*horizontalSpeedLimit);
			}
		}
		
		else {
			if (leftTrees.get(0).getVelocityY() < verticalSpeedLimit) {
				for (int i = 0; i < leftTrees.size(); i++) {
					leftTrees.get(i).speedUp(0, y*acceleration*time);
					rightTrees.get(i).speedUp(0, y*acceleration*time);
				}
				
				for (Sprite line : roadLines) {
					line.speedUp(0, y*acceleration*time);
				}
				
				for (RivalCar rival : rivalCars) {
					rival.speedUp(0, y*acceleration*time);
				}
				
			}else {
				for (int i = 0; i < leftTrees.size(); i++) {
					leftTrees.get(i).setVelocityY(y*verticalSpeedLimit);
					rightTrees.get(i).setVelocityY(y*verticalSpeedLimit);
				}
				for (Sprite line : roadLines) {
					line.setVelocityY(y*verticalSpeedLimit);;
				}				
				for (RivalCar rival : rivalCars) {
					rival.setVelocityY(y*verticalSpeedLimit);
				}
				
			}

		}
				
	}
	
	public static void moveSprites(double time) {
		playerCar.move(time);
		for (int i = 0; i < leftTrees.size(); i++) {
			leftTrees.get(i).move(time);
			rightTrees.get(i).move(time);
		}
		for (Sprite line : roadLines) {
			line.move(time);
		}
		for (RivalCar rival : rivalCars) {
			rival.move(time);
		}
	}
		
	public static void refreshSprites() {
		
		//Road lines
		for (Sprite line : roadLines) {
			if (line.getPositionY()>HEIGHT+line.getImage().getHeight()) {
				line.setPosition(line.getPositionX(), -30-line.getImage().getHeight());
			}
			
		}
		
		//Trees
		for (int i = 0; i < rightTrees.size(); i++) {
			if (rightTrees.get(i).getPositionY()>HEIGHT+rightTrees.get(i).getImage().getHeight()) {
				rightTrees.get(i).setPosition(WIDTH-rightTrees.get(i).getImage().getWidth()-rand.nextInt(70), 0-rightTrees.get(i).getImage().getHeight());
			}
			if (rightTrees.get(i).getPositionY()<-rightTrees.get(i).getImage().getHeight()) {
				rightTrees.get(i).setPosition(WIDTH-rand.nextInt(125), HEIGHT+rightTrees.get(i).getImage().getHeight());
			}
			if (leftTrees.get(i).getPositionY()>HEIGHT+leftTrees.get(i).getImage().getHeight()) {
				leftTrees.get(i).setPosition(rand.nextInt((int) (GREEN_FIELD_WIDTH-leftTrees.get(i).getImage().getWidth())), 0-leftTrees.get(i).getImage().getHeight());
			}
			if (leftTrees.get(i).getPositionY()<-leftTrees.get(i).getImage().getHeight()) {
				leftTrees.get(i).setPosition(rand.nextInt((int) (GREEN_FIELD_WIDTH-leftTrees.get(i).getImage().getWidth())), HEIGHT+leftTrees.get(i).getImage().getHeight());
			}
		}
		
		//Cars
		ArrayList<Sprite> allCars = new ArrayList<Sprite>();
		allCars.add(playerCar);
		for (RivalCar rival : rivalCars) {
			double xPosition = 0;
			double yPosition = 0;
			
			if (rival.getPositionY() >= HEIGHT+rival.getImage().getHeight()) {
				
				xPosition = GREEN_FIELD_WIDTH + rand.nextInt((int) (ROAD_WIDTH-rival.getImage().getWidth()));
				yPosition = 0-rival.getImage().getHeight();
				rival.setPosition(xPosition, yPosition);
				
				int carIndex = 0;
				while(carIndex < allCars.size()) {
					while(intersects(rival, allCars.get(carIndex))) {
						xPosition = ROAD_WIDTH + rand.nextInt((int) (ROAD_WIDTH-rival.getImage().getWidth()));
						yPosition = -130;
						rival.setPosition(xPosition, yPosition);
						carIndex=0;
					}
					carIndex++;	
				}
				allCars.add(rival);
				rival.setImage(rivalCarSrc, carSrcShadow);
				rival.setOvertook(false);
			}
		}
		
		if (Math.abs(playerCar.getPositionX() - GREEN_FIELD_WIDTH) == 20 || Math.abs(playerCar.getPositionX() - 450-playerCar.getImage().getWidth()) == 20 ) {
			playerCar.setVelocityX(0);
		}		
	}
	
	public static void resetVelocities(double time, HashSet<String> instantKeys) {
		
		// Horizontal direction
		if (playerCar.getPositionX()-GREEN_FIELD_WIDTH <= 0) {
			playerCar.setVelocityX(0);
		}else if (playerCar.getPositionX()+playerCar.getImage().getWidth() + GREEN_FIELD_WIDTH >= WIDTH) {
			playerCar.setVelocityX(0);
		}
		
		double horizontalDirection = Math.abs(playerCar.getVelocityX()) / playerCar.getVelocityX();
		if ((Math.abs(playerCar.getVelocityX()) + time*horizontalNegAcceleration) > 0) {
			playerCar.speedUp(time*horizontalNegAcceleration*horizontalDirection, 0);
		}else {
			playerCar.setVelocityX(0);
		}
		
		// Vertical direction
		if (Math.abs(leftTrees.get(0).getVelocityY())+(negativeAcceleration*time) > 0) {
			for (int i = 0; i < leftTrees.size(); i++) {
				leftTrees.get(i).speedUp(0, negativeAcceleration*time);
				rightTrees.get(i).speedUp(0, negativeAcceleration*time);
			}
			for (Sprite line: roadLines) {
				line.speedUp(0, negativeAcceleration*time);
			}
			for (RivalCar rival : rivalCars) {
				rival.speedUp(0, negativeAcceleration*time);
			}
		}else {
			for (int i = 0; i < leftTrees.size(); i++) {
				leftTrees.get(i).setVelocityY(0);
				rightTrees.get(i).setVelocityY(0);
			}	
			for (Sprite line: roadLines) {
				line.setVelocityY(0);
			}
			for (RivalCar rival : rivalCars) {
				rival.setVelocityY(0);;
			}
		}
		
		
		
			
	}
	
	
	public static boolean intersects(Sprite object1, Sprite object2) {
		if ( (object1.getUpperLeftCorner().get(0) >= object2.getUpperLeftCorner().get(0)) && 
				(object1.getUpperLeftCorner().get(0) <= object2.getUpperRightCorner().get(0)) ) {
			
			if ( (object1.getUpperLeftCorner().get(1) <= object2.getBottomLeftCorner().get(1)) &&
					(object1.getUpperLeftCorner().get(1) >= object2.getUpperLeftCorner().get(1)) ) {
				return true;	
			}else if ( (object1.getBottomLeftCorner().get(1) >= object2.getUpperLeftCorner().get(1)) &&
					(object1.getBottomLeftCorner().get(1) <= object2.getBottomLeftCorner().get(1)) ) {
				return true;
			}	
		}
		
		if ( (object1.getUpperRightCorner().get(0) >= object2.getUpperLeftCorner().get(0)) && 
				(object1.getUpperRightCorner().get(0) <= object2.getUpperRightCorner().get(0)) ) {
			
			if ( (object1.getUpperLeftCorner().get(1) <= object2.getBottomLeftCorner().get(1)) &&
					(object1.getUpperLeftCorner().get(1) >= object2.getUpperLeftCorner().get(1)) ) {
				return true;		
			}else if ( (object1.getBottomLeftCorner().get(1) >= object2.getUpperLeftCorner().get(1)) &&
					(object1.getBottomLeftCorner().get(1) <= object2.getBottomLeftCorner().get(1)) ) {
				return true;
			}
		}
		return false;
	}
	
	
	public static boolean isGameOver(GraphicsContext gc) {
		for (Sprite rival : rivalCars) {
			if (intersects(playerCar, rival)) {
				playerCar.setImage(carCrashedSrc, carSrcShadow);
				rival.setImage(carCrashedSrc, carSrcShadow);
				render(gc);
				return true;
			}
		}
		return false;
	}
	
	
	public static void endTheGame(GraphicsContext gc) {
		
		playerCar.setVelocity(0, 0);
		for (Tree tree : leftTrees) {
			tree.setVelocity(0, 0);
		}
		for (RivalCar rival : rivalCars) {
			rival.setVelocity(0, 0);
		}
		for (Sprite line : roadLines) {
			line.setVelocity(0, 0);
		}
		
		gc.setFill(Color.BLACK);
		gc.fillRect(50, 100, 500, 300);

		
		
	    gc.setFill( Color.WHITE );
	    gc.setStroke( Color.BLACK );
	    Font theFont = Font.font( "", FontWeight.BOLD, 45);
	    gc.setFont( theFont );
	    gc.fillText( "GAME OVER", 150, 170 );
	    
	    theFont = Font.font( "", FontWeight.BOLD, 40 );
	    gc.setFont( theFont );
	    gc.fillText( "Your score is: "+score, 120, 250 );
	    gc.strokeText( "Your score is: "+score, 120, 250 );
	    
	    theFont = Font.font( "", FontWeight.BOLD, 20 );
	    gc.setFont( theFont );
	    gc.fillText( "Press ENTER to restart", 170, 350 );
	    gc.strokeText( "Press ENTER to restart", 170, 350 );
	    
	    

		
	    

	    
	}
	
	
	public static void rivalOvertaken() {
		for (RivalCar rival : rivalCars) {
			if ( (playerCar.getUpperLeftCorner().get(1) < rival.getUpperLeftCorner().get(1)) && !rival.isOvertook()) {
				rival.setImage(overtookRivalCarSrc, carSrcShadow);
				score += level;
				overtookNum++;
				rival.setOvertook(true);
				if ( overtookNum == 20 ) {
					if (!soundPlayed) {
						MediaPlayer mp = new MediaPlayer(sound);
						mp.play();
						soundPlayed = true;
					}
					overtookNum = 0;
					if (level >= 6) {
						level++;
					}else {
						levelUp();
					}
				}
				soundPlayed = false;
			}
			
		}
		
	}
	
	
	public static void newGame(GraphicsContext gc) {
		leftTrees.removeAll(leftTrees);
		rightTrees.removeAll(rightTrees);
		rivalCars.removeAll(rivalCars);
		roadLines.removeAll(roadLines);
		
		gc.clearRect(0, 0, 600, 600);
		loadGraphs();
		render(gc);
		
		score = 0;
		level = 1;
		overtookNum = 0;
		
		acceleration = 3000;
		negativeAcceleration = -1000;
		
		horizontalAcceleration = 9000;
		horizontalNegAcceleration = -4000;
		
		verticalSpeedLimit = 1000;
		horizontalSpeedLimit = 1000;

	}
	
	
	public static void levelUp() {	
		level++;
		
		acceleration +=400;
		negativeAcceleration -= 400;
		
		horizontalAcceleration += 500;
		horizontalNegAcceleration -= 300;
		
		horizontalSpeedLimit += 500;
		verticalSpeedLimit += 200;
	}
	
	
	
}
