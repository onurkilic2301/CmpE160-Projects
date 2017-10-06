import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Bishop extends Piece{
	
	public Bishop(boolean isBlack){
		this.isBlack=isBlack;
		if(this.isBlack){
			this.icon=new ImageIcon("src/Bishop_B_Icon.png");
		}
		else{
			this.icon=new ImageIcon("src/Bishop_W_Icon.png");
		}
	}
	
	@Override
	public void drawYourself(Graphics g, int positionX, int positionY, int squareWidth) {
		g.drawImage(this.icon.getImage(), positionX, positionY, null);
	}

	@Override
	public boolean canMove(int x, int y) {
		if(x==y||x==-1*y)
			return true;
		return false;
	}

	@Override
	public boolean canCapture(int x, int y) {
		if((x==y||x==-1*y))
			return true;
		return false;
	}

	@Override
	public boolean isBlocked(int pX, int pY, int x, int y, Piece[][] board) {
		if(Math.abs(x)==1&&Math.abs(y)==1)
			return false;
		else if(this.canMove(x,y)&&(x!=0&&y!=0)){
			int i=pX+Math.abs(x)/x;
			int j=pY+Math.abs(y)/y;
			while(i!=pX+x&&j!=pY+y){
				if(board[i][j]!=null)
					return true;
				i+=Math.abs(x)/x;
				j+=Math.abs(y)/y;
			}
			return false;
		}
		else 
			return true;
	}

	@Override
	public String toString() {
		String color;
		if(this.getColor()=='w')
			color="white";
		else
			color="black";
		return color+"-bishop";
	}

}
