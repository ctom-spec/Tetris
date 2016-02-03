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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;



// This class takes care of everything necessary for the single player game.

public class Game {

	Pane _pane;
	Piece _current;
	Game _game;
	Timeline _timeline;
	Square[][] _data;
	int _score;
	PaneOrganizer _PaneOrganizer;
	Label _label;
	boolean _gameOver;
	int _count;
	int _div;

	public Game(Pane pane, Label label){
		/*Create a 2d array to store the Squares in. 
		Create the keyframe to put into timeline.
		Pass in KeyHandler to pane's setOnKeyPressed method
		set gameOver to false, count to 0, div to 0, score to 0.  */
		_pane=pane;
		_game=this;
		_data = new Square[GAME_HEIGHT/SQUARE_LENGTH][GAME_WIDTH/SQUARE_LENGTH];
		this.setBounds();
		KeyFrame move = new KeyFrame(Duration.seconds(.1),new MoveTimeHandler());
		_timeline = new Timeline(move);
		_timeline.setCycleCount(Timeline.INDEFINITE);
		_timeline.play();
		pane.setOnKeyPressed(new KeyHandler());
		pane.setFocusTraversable(true);
		_label=label;
		_gameOver=false;
		_count=0;//counts the number of frames passed
		_div=10;//number to use when speeding up game
		_score=0;

	}
	public void setBounds(){
		//goes through all the rows and columns, and puts squares on the left,right, and bottom border of the pane.
		_current = this.makeRandPiece();

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
	}

	public Piece makeRandPiece(){
		//using Math.random and switch to randomly pick a new piece.
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

	private class PauseHandler implements EventHandler<KeyEvent>{
		//EventHandler for when the game is paused. It displays scores and  can only unpause. 
		@Override
		public void handle (KeyEvent e){
			KeyCode keyPressed = e.getCode();
			if (keyPressed == KeyCode.P){
				_timeline.play();
				_label.setText("Score: "+Integer.toString(_score));
				_pane.setOnKeyPressed(new KeyHandler());

			}
			e.consume();
		}
	}
	private class OverHandler implements EventHandler<KeyEvent>{
		//EventHandler for when the game is over. It can't do anything. 
		@Override
		public void handle (KeyEvent e){
		
		}
	}

	private class KeyHandler implements EventHandler<KeyEvent>{
		/*EventHandler for when the game is playing. 
		When an arrow key is pressed, current piece moves that direction
		and if it can't move that direction, it moves back. 
		pressing space drops the piece immediately and makes a new piece. 
		pressing up arrow rotates the piece. pressing p pauses the game. */
		public void handle (KeyEvent e){
			KeyCode keyPressed = e.getCode();

			if (keyPressed == KeyCode.RIGHT){
				_game._current.moveRight();
				if (!canMove(_game._current)){
					_game._current.moveLeft();
				}
			}
			else if (keyPressed ==KeyCode.LEFT){
				_game._current.moveLeft();
				if (!canMove(_game._current)){
					_game._current.moveRight();
				}
			}
			else if (keyPressed ==KeyCode.DOWN){
				_game._current.moveDown();
				if (!canMove(_game._current)){
					_game._current.moveUp();
					_game.updateData();
					_game.clearLine();
					_current=_game.makeRandPiece();
				}
			}
			else if (keyPressed == KeyCode.SPACE){
				while (canMove(_game._current)){
					_game._current.moveDown();
				}
				_game._current.moveUp();
				_game.updateData();
				_game.clearLine();
				for (int i=1; i< _data[i].length-1;i++){
					if (_data[0][i]!=null){
						_timeline.stop();
						_pane.setOnKeyPressed(new OverHandler());
						_label.setText("Game Over "+"\n Score: "+Integer.toString(_score));
						//set label to game over
						_gameOver = true;
					}
				}
				if (!_gameOver){
					_current=_game.makeRandPiece();
					//Only makes a new piece if the game is not over. 
				}

			}
			else if (keyPressed == KeyCode.UP){
				_game._current.rotate();
				if (!canMove(_game._current)){
					_game._current.rotate();
					_game._current.rotate();
					_game._current.rotate();
				}
			}
			else if (keyPressed == KeyCode.P){
				_timeline.stop();
				_label.setText("Paused");
				_pane.setOnKeyPressed(new PauseHandler());
			}
			e.consume();
		}
	}
	private class MoveTimeHandler implements EventHandler<ActionEvent>{
		public void handle (ActionEvent e){
			/*Called every .1 seconds, every 10 times it is called the current piece
			moves down and checks if game is over. To increase the speed of the fall 
			progressively, I made the variable _div (which was set to 10 in the constructor)
			to subtract by 1 every 5 seconds.*/
			_count++;
			if (_count%_div==0){
				_game._current.moveDown();
				if (!canMove(_game._current)){
					_game._current.moveUp();
					_game.updateData();
					_game.clearLine();
					for (int i=1; i< _data[i].length-1;i++){
						if (_data[0][i]!=null){
							_timeline.stop();
							_pane.setOnKeyPressed(new OverHandler());
							_label.setText("Game Over "+"\n Score: "+Integer.toString(_score));
							_gameOver=true;
						}
					}
					if (!_gameOver){
						_current=_game.makeRandPiece();
					}
				}
			}
			if (_count%50==0&&_div>1){
				_div--;
				_count=0;
				
			}
		e.consume();

		}
	}
	public void updateData(){
		/*This method updates the array to correspond with the graphical elements.*/
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

	public boolean canMove(Piece p){
		/*This method checks if a move is valid. This method is called AFTER a move has already
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
		//checks all 4 squares of the piece
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
	public void clearLine(){
		/*Goes through the 2d array list to see if there is a full horizontal line. If there is,
		I make the squares occuyping those places go away by setting their Y's to 1000 (I'm sure there's 
		a better way to do it, but it works), and deleting them from the array. Then I move all of the
		rows above that row down one row. Also keeps track of the line count, so that if more than 4
		lines were cleared in one move, you get 5 extra points.  */
		int tetrisCount =0;
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
							//deleting the row
						}
						_score++;
						tetrisCount++;
						_label.setText("Score: "+Integer.toString(_score));;
						for (int h=i;h>0;h--){
							for(int l=1;l<_data[h].length-1;l++){
								if(_data[h-1][l]!=null){
									_data[h-1][l].setY(_data[h-1][l].getY()+SQUARE_LENGTH);
								}
								//moving all the rows down
								_data[h][l]=_data[h-1][l];
								
							}
						}
						i--;
					}

				}
			}
		}
		if (tetrisCount>=4){
			_score+=5;
			_label.setText("Score: "+Integer.toString(_score)+"\nTetris!!! "+"\n5 extra points");
		}
	}
}
