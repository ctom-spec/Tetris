package Tetris;
import javafx.scene.layout.Pane;
import static Tetris.Constants.*;
import javafx.scene.paint.Color;

public class JPiece extends Piece{

	public JPiece(int x, int y, Pane pane){
		//set the shape of the piece 
		super(x,y,pane);
		super.setColor(Color.YELLOW);
		super.getB().setX(x-SQUARE_LENGTH);
		super.getC().setY(y-SQUARE_LENGTH);
		super.getD().setY(y-2*SQUARE_LENGTH);
		
	}

	public void rotate(){
		//set center of rotation and rotate
		super.setCenterX(super.getC().getX());
		super.setCenterY(super.getC().getY());
		super.rotate();
	}
	
}