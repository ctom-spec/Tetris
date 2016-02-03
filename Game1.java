package Tetris;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import static Tetris.Constants.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;



// This class takes care of everything necessary for the two player game.


public class Game1 {
	Pane _pane;
	Piece _current;
	Game1 _game;
	Timeline _timeline;
	Square[][] _data;
	Piece _current1;
	Square[][] _data1;
	Label _label1;
	Label _label2;
	int _score1;
	int _score2;

	public Game1(Pane pane, Label label1, Label label2){
		/*Create a two different 2d array to store the Squares in. 
		Create the keyframe to put into timeline.
		Pass in KeyHandler to pane's setOnKeyPressed method
		*/
		
		_label1=label1;
		_label2=label2;
		_pane=pane;
		_game=this;
		_data = new Square[GAME_HEIGHT/SQUARE_LENGTH][GAME_WIDTH/SQUARE_LENGTH];
		_data1 = new Square[GAME_HEIGHT/SQUARE_LENGTH][GAME_WIDTH/SQUARE_LENGTH];
		this.setBounds();
		KeyFrame move = new KeyFrame(Duration.seconds(1),new MoveTimeHandler());
		_timeline = new Timeline(move);
		_timeline.setCycleCount(Timeline.INDEFINITE);
		_timeline.play();
		pane.setOnKeyPressed(new KeyHandler());
		pane.setFocusTraversable(true);


	}
	public void setBounds(){
		/*goes through all the rows and columns, and puts squares on the left,right, and bottom border
		of both players' screens. */
		
		_current = this.makeRandPiece(); //player 1's current piece
		_current1 = this.makeRandPiece1(); // player 2's current piece 

		//sets player 1 bounds
		for (int i=0;i<(GAME_HEIGHT/SQUARE_LENGTH);i++){
			Square left = new Square(0,i*SQUARE_LENGTH,_pane);
			Square right = new Square(GAME_WIDTH-SQUARE_LENGTH,i*SQUARE_LENGTH,_pane);
			_data[i][0]=left;
			_data[i][(GAME_WIDTH/SQUARE_LENGTH)-1]=right;
			if (i>0&&i<(GAME_WIDTH/SQUARE_LENGTH)-1){
				Square bottom = new Square (i*SQUARE_LENGTH,GAME_HEIGHT-SQUARE_LENGTH,_pane);
				_data[(GAME_HEIGHT/SQUARE_LENGTH)-1][i]=bottom;
			}

		}
		//player 2 bounds
		for (int i=0;i<(GAME_HEIGHT/SQUARE_LENGTH);i++){
			Square left = new Square(GAME_WIDTH,i*SQUARE_LENGTH,_pane);
			Square right = new Square(2*GAME_WIDTH-SQUARE_LENGTH,i*SQUARE_LENGTH,_pane);
			_data1[i][0]=left;
			_data1[i][(GAME_WIDTH/SQUARE_LENGTH)-1]=right;
			if (i>0&&i<(GAME_WIDTH/SQUARE_LENGTH)-1){
				Square bottom = new Square (i*SQUARE_LENGTH+GAME_WIDTH,GAME_HEIGHT-SQUARE_LENGTH,_pane);
				_data1[(GAME_HEIGHT/SQUARE_LENGTH)-1][i]=bottom;
			}

		}
	}

	public Piece makeRandPiece(){
		//creates a random piece on player 1's screen
		int rand = (int) (Math.random()*7);
		switch (rand){
			case 0: return new IPiece(GAME_WIDTH/2,0,_pane);
			case 1: return new OPiece(GAME_WIDTH/2,0,_pane);
			case 2: return new SPiece(GAME_WIDTH/2,0,_pane);
			case 3: return new LPiece(GAME_WIDTH/2,0,_pane);
			case 4: return new JPiece(GAME_WIDTH/2,0,_pane);
			case 5: return new TPiece(GAME_WIDTH/2,0,_pane);
			case 6: return new ZPiece(GAME_WIDTH/2,0,_pane);
			default: return new ZPiece(GAME_WIDTH/2,0,_pane);
		}
	}
	public Piece makeRandPiece1(){
		//creates a random piec eon player 2's screen
		int rand = (int) (Math.random()*7);
		switch (rand){
			case 0: return new IPiece(GAME_WIDTH/2+GAME_WIDTH,0,_pane);
			case 1: return new OPiece(GAME_WIDTH/2+GAME_WIDTH,0,_pane);
			case 2: return new SPiece(GAME_WIDTH/2+GAME_WIDTH,0,_pane);
			case 3: return new LPiece(GAME_WIDTH/2+GAME_WIDTH,0,_pane);
			case 4: return new JPiece(GAME_WIDTH/2+GAME_WIDTH,0,_pane);
			case 5: return new TPiece(GAME_WIDTH/2+GAME_WIDTH,0,_pane);
			case 6: return new ZPiece(GAME_WIDTH/2+GAME_WIDTH,0,_pane);
			default: return new ZPiece(GAME_WIDTH/2+GAME_WIDTH,0,_pane);
		}
	}
	private class PauseHandler implements EventHandler<KeyEvent>{
		//Event handler for when the game is paused. displays scores and can unpause
		public void handle (KeyEvent e){
			KeyCode keyPressed = e.getCode();
			if (keyPressed == KeyCode.P){
				_pane.setOnKeyPressed(new KeyHandler());
				_label1.setText("Player1 score: "+Integer.toString(_score1));
				_label2.setText("Player2 score: "+Integer.toString(_score2));
				_timeline.play();
			}
		}
	}
	private class OverHandler implements EventHandler<KeyEvent>{
		//Event handler for when the game is over, can't do anything
		@Override
		public void handle (KeyEvent e){
		
		}
	}

	private class KeyHandler implements EventHandler<KeyEvent>{
		/*Event handler for when the game is playing, player 1 plays with 
		w,a,s,d,f and player 2 plays with arrow keys and space bar.*/
		public void handle (KeyEvent e){
			KeyCode keyPressed = e.getCode();

			if (keyPressed == KeyCode.D){
				_game._current.moveRight();
				if (!canMove(_game._current)){
					_game._current.moveLeft();
				}
			}
			else if (keyPressed ==KeyCode.A){
				_game._current.moveLeft();
				if (!canMove(_game._current)){
					_game._current.moveRight();
				}
			}
			else if (keyPressed ==KeyCode.S){
				_game._current.moveDown();
				if (!canMove(_game._current)){
					_game._current.moveUp();
					_game.updateData();
					_game.clearLine();
					_current=_game.makeRandPiece();
				}
			}
			else if (keyPressed == KeyCode.F){
				while (canMove(_game._current)){
					_game._current.moveDown();
				}
				_game._current.moveUp();
				_game.updateData();
				_game.clearLine();
				_current=_game.makeRandPiece();
			}
			else if (keyPressed == KeyCode.W){
				_game._current.rotate();
				if (!canMove(_game._current)){
					_game._current.rotate();
					_game._current.rotate();
					_game._current.rotate();
				}
			}

			if (keyPressed == KeyCode.RIGHT){
				_game._current1.moveRight();
				if (!canMove1(_game._current1)){
					_game._current1.moveLeft();
				}
			}
			else if (keyPressed ==KeyCode.LEFT){
				_game._current1.moveLeft();
				if (!canMove1(_game._current1)){
					_game._current1.moveRight();
				}
			}
			else if (keyPressed ==KeyCode.DOWN){
				_game._current1.moveDown();
				if (!canMove1(_game._current1)){
					_game._current1.moveUp();
					_game.updateData1();
					_game.clearLine1();
					_current1=_game.makeRandPiece1();
				}
			}
			else if (keyPressed == KeyCode.SPACE){
				while (canMove1(_game._current1)){
					_game._current1.moveDown();
				}
				_game._current1.moveUp();
				_game.updateData1();
				_game.clearLine1();
				_current1=_game.makeRandPiece1();
			}
			else if (keyPressed == KeyCode.UP){
				_game._current1.rotate();
				if (!canMove1(_game._current1)){
					_game._current1.rotate();
					_game._current1.rotate();
					_game._current1.rotate();
				}
			}
			else if (keyPressed == KeyCode.P){
				_timeline.stop();
				_label1.setText("Paused");
				_label2.setText("");
				_pane.setOnKeyPressed(new PauseHandler());
			}
			
			e.consume();
		}
	}
	private class MoveTimeHandler implements EventHandler<ActionEvent>{
		public void handle (ActionEvent e){
			//Moves both pieces down every frame. 
			_game._current.moveDown();
			if (!canMove(_game._current)){
				_game._current.moveUp();
				_game.updateData();
				_game.clearLine();
				_current=_game.makeRandPiece();
			}
			_game._current1.moveDown();
			if (!canMove1(_game._current1)){
				_game._current1.moveUp();
				_game.updateData1();
				_game.clearLine1();
				_current1=_game.makeRandPiece1();
			}
			for (int i=1; i< _data[i].length-1;i++){
				if (_data[0][i]!=null){
					_timeline.stop();
					_pane.setOnKeyPressed(new OverHandler());
					_label1.setText("Player 2 Win!");
					_label2.setText("");
				}
			}
			for (int i=1; i< _data1[i].length-1;i++){
				if (_data1[0][i]!=null){
					_timeline.stop();
					_pane.setOnKeyPressed(new OverHandler());
					_label1.setText("Player 1 Win!");
					_label2.setText("");
			}
			e.consume();
			}
		}
	}
	public void updateData(){
		//updates player 1's array to correspond with the graphics. 
		int ax = _game._current.getA().getX();
		int ay = _game._current.getA().getY();
		int bx = _game._current.getB().getX();
		int by = _game._current.getB().getY();
		int cx = _game._current.getC().getX();
		int cy = _game._current.getC().getY();
		int dx = _game._current.getD().getX();
		int dy = _game._current.getD().getY();
		if (ay>=0){
			_data[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]=_game._current.getA();
		}
		if (by>=0){
			_data[(by/SQUARE_LENGTH)][(bx/SQUARE_LENGTH)]=_game._current.getB();
		}
		if (cy>=0){
			_data[(cy/SQUARE_LENGTH)][(cx/SQUARE_LENGTH)]=_game._current.getC();
		}
		if (dy>=0){
			_data[(dy/SQUARE_LENGTH)][(dx/SQUARE_LENGTH)]=_game._current.getD();
		}
	}
	public void updateData1(){
		//updates player 2's array to correspond with the graphics. 
		int ax = _game._current1.getA().getX()-GAME_WIDTH;
		int ay = _game._current1.getA().getY();
		int bx = _game._current1.getB().getX()-GAME_WIDTH;
		int by = _game._current1.getB().getY();
		int cx = _game._current1.getC().getX()-GAME_WIDTH;
		int cy = _game._current1.getC().getY();
		int dx = _game._current1.getD().getX()-GAME_WIDTH;
		int dy = _game._current1.getD().getY();
		if (ay>=0){
			_data1[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]=_game._current1.getA();
		}
		if (by>=0){
			_data1[(by/SQUARE_LENGTH)][(bx/SQUARE_LENGTH)]=_game._current1.getB();
		}
		if (cy>=0){
			_data1[(cy/SQUARE_LENGTH)][(cx/SQUARE_LENGTH)]=_game._current1.getC();
		}
		if (dy>=0){
			_data1[(dy/SQUARE_LENGTH)][(dx/SQUARE_LENGTH)]=_game._current1.getD();
		}
	}

	public boolean canMove(Piece p){
		/*This method checks if player 1's move is valid. This method is called AFTER a move has already
		been made, so we can get the current X and Y positions of the piece to see if there is something
		occupying the place in the array. All the >= cases are to make sure that it doesn't throw an
		index out of bounds error.*/
		int ax = p.getA().getX();
		int ay = p.getA().getY();
		int bx = p.getB().getX();
		int by = p.getB().getY();
		int cx = p.getC().getX();
		int cy = p.getC().getY();
		int dx = p.getD().getX();
		int dy = p.getD().getY();
		if (by < 0&&ay>=0){
			if(_data[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]==null){
				return true;
			}
			return false;
		}
		else if (cy < 0 &&ay>=0&&by>=0){
			if(_data[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]==null&&
			   _data[(by/SQUARE_LENGTH)][(bx/SQUARE_LENGTH)]==null){
				return true;
			}
			return false;
		}
		else if (dy < 0 &&ay>=0&&by>=0&&cy>=0){
			if(_data[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]==null&&
				_data[(by/SQUARE_LENGTH)][(bx/SQUARE_LENGTH)]==null&&
				_data[(cy/SQUARE_LENGTH)][(cx/SQUARE_LENGTH)]==null){
				return true;
			}
			return false;
		}
		else if (ay>=0&&by>=0&&cy>=0&&dy>=0){
			if(_data[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]==null&&
				_data[(by/SQUARE_LENGTH)][(bx/SQUARE_LENGTH)]==null&&
				_data[(cy/SQUARE_LENGTH)][(cx/SQUARE_LENGTH)]==null&&
				_data[(dy/SQUARE_LENGTH)][(dx/SQUARE_LENGTH)]==null){
				return true;
			}
			return false;
		}
		return false;
	}
	public boolean canMove1(Piece p){
		/*This method checks if player 2's move is valid. This method is called AFTER a move has already
		been made, so we can get the current X and Y positions of the piece to see if there is something
		occupying the place in the array. All the >= cases are to make sure that it doesn't throw an
		index out of bounds error.*/
		int ax = p.getA().getX()-GAME_WIDTH;
		int ay = p.getA().getY();
		int bx = p.getB().getX()-GAME_WIDTH;
		int by = p.getB().getY();
		int cx = p.getC().getX()-GAME_WIDTH;
		int cy = p.getC().getY();
		int dx = p.getD().getX()-GAME_WIDTH;
		int dy = p.getD().getY();
		if (by < 0&&ay>=0){
			if(_data1[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]==null){
				return true;
			}
			return false;
		}
		else if (cy < 0 &&ay>=0&&by>=0){
			if(_data1[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]==null&&
			   _data1[(by/SQUARE_LENGTH)][(bx/SQUARE_LENGTH)]==null){
				return true;
			}
			return false;
		}
		else if (dy < 0 &&ay>=0&&by>=0&&cy>=0){
			if(_data1[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]==null&&
				_data1[(by/SQUARE_LENGTH)][(bx/SQUARE_LENGTH)]==null&&
				_data1[(cy/SQUARE_LENGTH)][(cx/SQUARE_LENGTH)]==null){
				return true;
			}
			return false;
		}
		else if (ay>=0&&by>=0&&cy>=0&&dy>=0){
			if(_data1[(ay/SQUARE_LENGTH)][(ax/SQUARE_LENGTH)]==null&&
				_data1[(by/SQUARE_LENGTH)][(bx/SQUARE_LENGTH)]==null&&
				_data1[(cy/SQUARE_LENGTH)][(cx/SQUARE_LENGTH)]==null&&
				_data1[(dy/SQUARE_LENGTH)][(dx/SQUARE_LENGTH)]==null){
				return true;
			}
			return false;
		}
		return false;
	}
	public void clearLine(){
		/*Goes through player 1's 2d array list to see if there is a full horizontal line. If there is,
		I make the squares occuyping those places go away by setting their Y's to 1000 (I'm sure there's 
		a better way to do it, but it works), and deleting them from the array. Then I move all of the
		rows above that row down one row.  */
		
		for (int i=0; i<_data.length-1;i++){
			for (int j=0; j<_data[i].length-1;j++){
				if (_data[i][j]==null){
					break;
				}
				else{
					if(j==_data[i].length-2){
						for (int k=1;k<_data[i].length-1;k++){
							_data[i][k].setY(1000);
							_data[i][k]=null;
						}
						_score1++;
						for (int h=i;h>0;h--){
							for(int l=1;l<_data[h].length-1;l++){
								if(_data[h-1][l]!=null){
									_data[h-1][l].setY(_data[h-1][l].getY()+SQUARE_LENGTH);
								}
								_data[h][l]=_data[h-1][l];
								
							}
						}
						i--;
					}

				}
			}
		}
	}
	public void clearLine1(){
		/*Goes player 2's 2d array list to see if there is a full horizontal line. If there is,
		I make the squares occuyping those places go away by setting their Y's to 1000 (I'm sure there's 
		a better way to do it, but it works), and deleting them from the array. Then I move all of the
		rows above that row down one row.   */
		
		for (int i=0; i<_data1.length-1;i++){
			for (int j=0; j<_data1[i].length-1;j++){
				if (_data1[i][j]==null){
					break;
				}
				else{
					if(j==_data1[i].length-2){
						for (int k=1;k<_data1[i].length-1;k++){
							_data1[i][k].setY(1000);
							_data1[i][k]=null;
						}
						_score2++;
						for (int h=i;h>0;h--){
							for(int l=1;l<_data1[h].length-1;l++){
								if(_data1[h-1][l]!=null){
									_data1[h-1][l].setY(_data1[h-1][l].getY()+SQUARE_LENGTH);
								}
								_data1[h][l]=_data1[h-1][l];
								
							}
						}
						i--;
					}

				}
			}
		}
	}	
}
