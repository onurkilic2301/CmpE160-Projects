import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Knight extends Piece {
	
	public Knight(boolean isBlack){
		this.isBlack=isBlack;
		if(this.isBlack){
			this.icon=new ImageIcon("src/Knight_B_Icon.png");
		}
		else{
			this.icon=new ImageIcon("src/Knight_W_Icon.png");
		}
	}
	@Override
	public void drawYourself(Graphics g, int positionX, int positionY, int squareWidth) {
		g.drawImage(this.icon.getImage(), positionX, positionY, null);
	}

	@Override
	public boolean canMove(int x, int y) {
		if((Math.abs(x)==2&&Math.abs(y)==1)||(Math.abs(y)==2&&Math.abs(x)==1))
			return true;
		else
			return false;
	}

	@Override
	public boolean canCapture(int x, int y) {
		if((Math.abs(x)==2&&Math.abs(y)==1)||(Math.abs(y)==2&&Math.abs(x)==1))
			return true;
		else
			return false;
	}
	@Override
	public boolean isBlocked(int pX, int pY, int x, int y, Piece[][] board) {
		return false;
	}
	
	@Override
	public String toString() {
		String color;
		if(this.getColor()=='w')
			color="white";
		else
			color="black";
		return color+"-knight";
	}

	
	

}
