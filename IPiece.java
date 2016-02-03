package Tetris;
import javafx.scene.layout.Pane;
import static Tetris.Constants.*;
import javafx.scene.paint.Color;

public class IPiece extends Piece{

	public IPiece(int x, int y, Pane pane){
		//Set the shape of the piece.
		super(x,y,pane);
		super.setColor(Color.RED);
		super.getB().setY(y-SQUARE_LENGTH);
		super.getC().setY(y-2*SQUARE_LENGTH);
		super.getD().setY(y-3*SQUARE_LENGTH);
		
	}

	public void rotate(){
		//set center of rotation and rotate
		super.setCenterX(super.getC().getX());
		super.setCenterY(super.getB().getY());
		super.rotate();
	}
	
} 