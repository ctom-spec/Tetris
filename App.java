package Tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  * Tetris! 
  * Extra credit: 
  * 1.Two player mode
  * 2.Home button to reset and pick mode of game
  * 3.Keeps track of score
  * 4.Keeps track of number of lines cleared for Tetris! (4+ lines)
  * 5.Change scene according to mode of game
  * 6.Speed of the fall increases. This game is incredibly hard.
  * 7.Remote control with the Myo armband, video available here: www.junho.co
  * @author <jhong2>
  *
  */

public class App extends Application {

    @Override
	public void start(Stage stage) {
    	stage.setTitle("Tetris");
    	stage.show();
    	PaneOrganizer organizer = new PaneOrganizer(stage);
      //set scene size to game width+100 and game height. 
    	Scene scene = new Scene (organizer.getRoot(),Constants.GAME_WIDTH+100,Constants.GAME_HEIGHT);
    	stage.setScene(scene);
    }

	/*
	* Here is the mainline! No need to change this.  
	*/
	public static void main(String[] argv) {
        	// launch is a method inherited from Application
		launch(argv);
	}
}
