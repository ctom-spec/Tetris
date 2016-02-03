package Tetris;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import static Tetris.Constants.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import java.awt.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;


public class PaneOrganizer {
	
	BorderPane _pane;
	Pane _1Pane;
	Pane _2Pane;
	PaneOrganizer _PaneOrganizer;
	VBox _startPane;
	VBox _scorePane;
	Stage _stage;
	Label _label1;
	Label _p1label;
	Label _p2label;
	Button _home;
	
	public PaneOrganizer(Stage s){
		//Set a new borderpane and set up start pane.
		_pane = new BorderPane();
		_PaneOrganizer=this;
		this.setUpStartPane();
		_stage=s;
	}
	public Pane getRoot(){
		//returns borderpane as pane
		return _pane; 
	}
	public void setGamePane(){
		/*sets up the single player game pane, with its appropriate size and color.
		as well as button for home and label for score. */
		_1Pane = new Pane();
		_1Pane.setPrefSize(GAME_WIDTH,GAME_HEIGHT);
		_1Pane.setStyle("-fx-background-color: black");
		VBox scorePane = new VBox(50);
		scorePane.setPrefSize(SCORE_PANE_WIDTH1,GAME_HEIGHT);
		scorePane.setStyle("-fx-background-color: white");
		scorePane.setFocusTraversable(true);
		_pane.setCenter(_1Pane);
		_label1 = new Label ("Score: 0");
		Button home = new Button("Home");
		home.setOnMousePressed(new HomeHandler());
		scorePane.getChildren().addAll(_label1,home);
		scorePane.setAlignment(Pos.TOP_CENTER);
		_pane.setRight(scorePane);
		
	}
	public void set2PlayerPane(){
		/*sets up the two player game pane, with its approprize size and color.
		as well as button for home and label for score. */
		_2Pane = new Pane();
		_2Pane.setPrefSize(2*GAME_WIDTH,GAME_HEIGHT);
		_2Pane.setStyle("-fx-background-color: black");
		VBox scorePane = new VBox(50);
		scorePane.setStyle("-fx-background-color: white");
		scorePane.setPrefSize(SCORE_PANE_WIDTH2,GAME_HEIGHT);
		_pane.setCenter(_2Pane);
		_p1label = new Label ("Player 1 Score: 0");
		_p2label = new Label ("Player 2 Score: 0");
		Button home = new Button("Home");
		home.setOnMousePressed(new HomeHandler());
		scorePane.getChildren().addAll(_p1label,_p2label,home);
		scorePane.setAlignment(Pos.TOP_CENTER);
		_pane.setRight(scorePane);
		// Game1 game = new Game1(gamePane);

	}

	public void setUpStartPane(){
		/* sets up the home/start pane. has options for 
		single player/two player. */
		VBox startPane = new VBox(50);
		startPane.setPrefSize(GAME_WIDTH,GAME_HEIGHT);
		startPane.setStyle("-fx-background-color: white");
		Button oneP = new Button ("Single Player");
		oneP.setOnMousePressed(new SingleHandler());
		Button twoP = new Button ("Two Player");
		twoP.setOnMousePressed(new TwoHandler());
		startPane.getChildren().addAll(oneP,twoP);
		startPane.setAlignment(Pos.CENTER);
		Pane empty = new Pane();
		_pane.setCenter(startPane);
		_pane.setRight(empty);
	}
	public void startSinglePlayer(){
		//starts the single player game with the correct stage width.
		this.setGamePane();
		_stage.setWidth(GAME_WIDTH+SCORE_PANE_WIDTH1);
		Game game = new Game(_1Pane,_label1);
	}
	public void startTwoPlayer(){
		//starts the two player game with the correct stage width. 
		this.set2PlayerPane();
		_stage.setWidth(2*GAME_WIDTH+SCORE_PANE_WIDTH2);
		Game1 game = new Game1(_2Pane,_p1label,_p2label);
	}
	private class HomeHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle (MouseEvent e){
			//called when home button is clicked in game.
			_PaneOrganizer.setUpStartPane();
			_stage.setWidth(GAME_WIDTH);
		}
	}
	private class SingleHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle (MouseEvent e){
			//called when singleplayer game is clicked. 
			_PaneOrganizer.startSinglePlayer();
			e.consume();
		}
	}
	private class TwoHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle (MouseEvent e){
			//called when two player game is clicked. 
			_PaneOrganizer.startTwoPlayer();
			e.consume();
		}
	}


}
