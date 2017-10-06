import java.awt.Graphics;

import javax.swing.ImageIcon;

public abstract class Piece {
	protected boolean isBlack;
	public boolean isMoved=false;
	public ImageIcon icon;
	public abstract void drawYourself(Graphics g, int positionX, int positionY, int squareWidth);
	public abstract boolean canMove(int x, int y);
	public abstract boolean canCapture(int x, int y);
	public abstract boolean isBlocked(int pX,int pY, int x, int y,Piece[][] board);
	public abstract String toString();
	public char getColor() {
		if(this.isBlack)
			return 'b';
		else
			return 'w';
	}
}
