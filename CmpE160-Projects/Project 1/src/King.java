import java.awt.Graphics;

import javax.swing.ImageIcon;

public class King extends Piece{

	public King(boolean isBlack){
		this.isBlack=isBlack;
		if(this.isBlack){
			this.icon=new ImageIcon("src/King_B_Icon.png");
		}
		else{
			this.icon=new ImageIcon("src/King_W_Icon.png");
		}
	}

	@Override
	public void drawYourself(Graphics g, int positionX, int positionY, int squareWidth) {
		g.drawImage(this.icon.getImage(), positionX, positionY, null);
	}

	@Override
	public boolean canMove(int x, int y) {
		if(x==0&&y==0)
			return false;
		else if(Math.abs(x)<=1&&Math.abs(y)<=1)
			return true;
		else
			return false;
	}

	@Override
	public boolean canCapture(int x, int y) {
		if(x==0&&y==0)
			return false;
		else if(Math.abs(x)<=1&&Math.abs(y)<=1)
			return true;
		else
			return false;
	}

	@Override
	public boolean isBlocked(int pX, int pY, int x, int y, Piece[][] board) {
		//check for the other king
		/*
		int i=pX+x;
		int j=pY+y;
		char c;
		if(isBlack)
			c='b';
		else
			c='w';
		
		if(i+1<8){
			if(board[i+1][j] instanceof King && board[i+1][j].getColor()!=c)
				return true;
			else if(j+1<8){
				if(board[i+1][j+1] instanceof King && board[i+1][j+1].getColor()!=c)
					return true;
				else if(board[i][j+1] instanceof King && board[i][j+1].getColor()!=c)
					return true;
			}
			else if(j-1>=0){
				if(board[i+1][j-1] instanceof King && board[i+1][j-1].getColor()!=c)
					return true;
				else if(board[i][j-1] instanceof King && board[i][j-1].getColor()!=c)
					return true;
			}
		}
		else if(i-1>=0){
			if(board[i-1][j] instanceof King && board[i-1][j].getColor()!=c)
				return true;
			else if(j+1<8&&(board[i-1][j+1] instanceof King && board[i-1][j+1].getColor()!=c))
				return true;
			else if(j-1>=0&&(board[i-1][j-1] instanceof King && board[i-1][j-1].getColor()!=c))
				return true;
		}
		*/
		return false;
		
	}

	@Override
	public String toString() {
		String color;
		if(this.getColor()=='w')
			color="white";
		else
			color="black";
		return color+"-king";
	}



}
