import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Assignment4 extends Application{
	
	private static Scene scene;
	private static HashSet<String> instantKeys = new HashSet<String>();
	
	//Media
	private static Media sound = new Media(new File("sound/streetfighter.wav").toURI().toString());
	private static Media sound2 = new Media(new File("sound/gameOver.wav").toURI().toString());
	private static boolean musicPlayed = false;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle("HUBBM-Racer");
        Group root = new Group();
		scene = new Scene(root);
		theStage.setScene(scene);
		Canvas canvas = new Canvas(600, 600);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		Game.loadGraphs();
		Game.render(gc);
		
		actionHandlers();

		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setVolume(0.5);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
		       public void run() {
		           mediaPlayer.seek(Duration.ZERO);
		         }
		     });

		double deltaTime = 0.01;
		
		new AnimationTimer() {
		

			@Override
			public void handle(long now) {
				
				if (Game.isGameOver(gc)) {
					Game.endTheGame(gc);
					if (!musicPlayed) {
						MediaPlayer mediaPlayer = new MediaPlayer(sound2);
						mediaPlayer.play();
						mediaPlayer.setVolume(1000);
						musicPlayed = true;
					}
				    
				}else {
					Game.tickAndRender(gc, instantKeys, deltaTime);
					musicPlayed = false;
				}
				if (instantKeys.contains("ENTER")) {
					Game.newGame(gc);
					instantKeys.remove("ENTER");					
				}
			}
		}.start();
		
		theStage.show();		
	}
	
		
	private static void actionHandlers() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                instantKeys.add(event.getCode().toString());    
            }
        });
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				instantKeys.remove(event.getCode().toString());
			}
		});
	}
	
}

























