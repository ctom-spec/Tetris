package Tetris;

import javafx.scene.layout.Pane;
import static Tetris.Constants.*;
import javafx.scene.paint.Color;


public class OPiece extends Piece{
	
	public OPiece(int x, int y, Pane pane){
		//set the shape of the piece 
		super(x,y,pane);
		super.setColor(Color.PINK);
		super.getB().setX(x+SQUARE_LENGTH);
		super.getC().setY(y-SQUARE_LENGTH);
		super.getD().setX(x+SQUARE_LENGTH);
		super.getD().setY(y-SQUARE_LENGTH);
		
	}
	public void rotate(){
		
	}

}