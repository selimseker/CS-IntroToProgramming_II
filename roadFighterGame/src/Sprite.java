import java.io.File;
import java.util.ArrayList;
import javafx.scene.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	
	private Image image;
	private Image shadow;
	
	private double positionX;
	private double positionY;
	private double velocityX;
	private double velocityY;
	
	protected double positionXShadow;
	protected double positionYShadow;
	
	
	public Sprite() {
		this.positionX = 0;
		this.positionY = 0;
		this.velocityX = 0;
		this.velocityY = 0;
	}
	
	
	public void setImage(String fileName, String shadow) {

		this.image = new Image(new File(fileName).toURI().toString());
		this.shadow = new Image(new File(shadow).toURI().toString());
	}
	
	public Image getImage() {
		return image;
	}
	
	public void drawImage(GraphicsContext gc) {
		gc.drawImage(this.shadow, positionXShadow, positionYShadow);
		gc.drawImage(getImage(), positionX, positionY);
		
	}
	
	public void move(double time ) {
		this.positionX += time * this.velocityX;
		this.positionY += time * this.velocityY;
		this.positionXShadow += time * this.velocityX;
		this.positionYShadow += time * this.velocityY;
		
	}
	
	public void speedUp(double x, double y) {
		this.velocityX += x;
		this.velocityY += y;
	}
	
	public void setPosition(double x, double y) {
		this.positionX = x;
		this.positionY = y;
		this.positionXShadow = x+7;
		this.positionYShadow = y;
		
	}

	public void setVelocity(double x, double y) {
		this.velocityX = x;
		this.velocityY = y;
		
	}
		
	public ArrayList<Double> getUpperLeftCorner() {
		ArrayList<Double> coordinates = new ArrayList<Double>();
		coordinates.add(getPositionX());
		coordinates.add(getPositionY());
		return coordinates;
	}
	
	public ArrayList<Double> getUpperRightCorner() {
		ArrayList<Double> coordinates = new ArrayList<Double>();
		coordinates.add(getPositionX()+getImage().getWidth());
		coordinates.add(positionY);
		return coordinates;
	}	
	
	public ArrayList<Double> getBottomLeftCorner() {
		ArrayList<Double> coordinates = new ArrayList<Double>();
		coordinates.add(getPositionX());
		coordinates.add(getPositionY()+getImage().getHeight());
		return coordinates;
	}	
	
	public ArrayList<Double> getBottomRightCorner() {
		ArrayList<Double> coordinates = new ArrayList<Double>();
		coordinates.add(getPositionX()+getImage().getWidth());
		coordinates.add(getPositionY()+getImage().getHeight());
		return coordinates;
	}
	
	
	public double getVelocityX() {
		return velocityX;
	}
	
	public double getVelocityY() {
		return velocityY;
	}
	
	public double getPositionX() {
		return positionX;
	}
	public double getPositionY() {
		return positionY;
	}


	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}


	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
