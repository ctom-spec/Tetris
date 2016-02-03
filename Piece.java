package Tetris;
import javafx.scene.layout.Pane;
import static Tetris.Constants.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;



/*I made this class to reduce a bunch of code by making the specific pieces
inherit some things that all pieces share. They all consist of four squares,
needs a center of rotation, color, methods for moving, and a rotate method. */

public abstract class Piece{
	Square _a;
	Square _b;
	Square _c;
	Square _d;
	int _centerX;
	int _centerY;
	public Piece (){
		//constructor to use when making a single square
	}
	public Piece(int x, int y, Pane pane){
		//makes 4 new squares
		_a = new Square(x,y,pane);
		_b = new Square(x,y,pane);
		_c = new Square(x,y,pane);
		_d = new Square(x,y,pane);
		_centerX=0;
		_centerY=0;
	}
	public void setCenterX(int x){
		//sets x center of rotation 
		_centerX=x;
	}
	public void setCenterY(int y){
		//sets y center of rotation
		_centerY=y;
	}
	public Square getA(){
		//returns square a as square.
		return _a;
	}
	public Square getB(){
		//returns square b as square.
		return _b;
	}
	public Square getC(){
		//returns square c as square.
		return _c;
	}
	public Square getD(){
		//returns square d as square.
		return _d;
	}
	public void setColor(Color color){
		//sets color of all 4 squares
		_a.setColor(color);
		_b.setColor(color);
		_c.setColor(color);
		_d.setColor(color);
	}
	public void moveDown(){
		//moves all 4 squares down 
		_a.setY(_a.getY()+SQUARE_LENGTH);
		_b.setY(_b.getY()+SQUARE_LENGTH);
		_c.setY(_c.getY()+SQUARE_LENGTH);
		_d.setY(_d.getY()+SQUARE_LENGTH);
	}
	public void moveRight(){
		//moves all 4 squares to the right
		_a.setX(_a.getX()+SQUARE_LENGTH);
		_b.setX(_b.getX()+SQUARE_LENGTH);
		_c.setX(_c.getX()+SQUARE_LENGTH);
		_d.setX(_d.getX()+SQUARE_LENGTH);
	}
	public void moveLeft(){
		//moves all 4 squares to the left
		_a.setX(_a.getX()-SQUARE_LENGTH);
		_b.setX(_b.getX()-SQUARE_LENGTH);
		_c.setX(_c.getX()-SQUARE_LENGTH);
		_d.setX(_d.getX()-SQUARE_LENGTH);
	}
	public void moveUp(){
		//moves all 4 squares up. 
		_a.setY(_a.getY()-SQUARE_LENGTH);
		_b.setY(_b.getY()-SQUARE_LENGTH);
		_c.setY(_c.getY()-SQUARE_LENGTH);
		_d.setY(_d.getY()-SQUARE_LENGTH);
	}
	public void rotate(){
		//rotates all 4 pieces counter clockwise. 
		int ax = getA().getX();
		int ay = getA().getY();
		int bx = getB().getX();
		int by = getB().getY();
		int cx = getC().getX();
		int cy = getC().getY();
		int dx = getD().getX();
		int dy = getD().getY();
		getA().setX(_centerX-_centerY+ay);
		getA().setY(_centerY+_centerX-ax);
		getB().setX(_centerX-_centerY+by);
		getB().setY(_centerY+_centerX-bx);
		getC().setX(_centerX-_centerY+cy);
		getC().setY(_centerY+_centerX-cx);
		getD().setX(_centerX-_centerY+dy);
		getD().setY(_centerY+_centerX-dx);
	}
	
}