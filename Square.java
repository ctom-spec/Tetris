package Tetris;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import static Tetris.Constants.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*I made this class so that it will make it easier for me to modify certain traits
of the building blocks if need be, instead of just using rectangles to make pieces.
Using this class reduces code by declaring the traits of the square only once. 
Having this class enables me to change all the blocks to whatever I want, for ex.
an Image. */

public class Square extends Piece{
	Rectangle _square;
	public Square(int x, int y,Pane pane){
		/*Square is a rectangle of same width and height of SQAURE_LENGTH.
		Stroke is set to black and the color of it is white by default. */
		super();
		_square = new Rectangle(x,y,SQUARE_LENGTH,SQUARE_LENGTH);
		_square.setFill(Color.WHITE);
		_square.setStroke(Color.BLACK);
		pane.getChildren().addAll(_square);
	}
	public Rectangle getSquare(){
		//returns square as rectangle
		return _square;
	}
	public void setX(int x){
		//sets x of square
		_square.setX(x);
	}
	public void setY(int y){
		//sets y of square
		_square.setY(y);
	}
	public int getX(){
		//gets x position of square
		return (int) _square.getX();
	}
	public int getY(){
		//gets y position of square
		return (int) _square.getY();
	}
	public void setColor(Color color){
		//sets the color of the square
		_square.setFill(color);
	}
	
}