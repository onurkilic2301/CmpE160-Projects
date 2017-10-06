import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Pawn extends Piece{
	
	public Pawn(boolean isBlack)
	{
		this.isBlack = isBlack;
		if(this.isBlack){
			this.icon=new ImageIcon("src/Pawn_B_Icon.png");
		}
		else{
			this.icon=new ImageIcon("src/Pawn_W_Icon.png");
		}
	}

	@Override
	public void drawYourself(Graphics g, int positionX, int positionY, int squareWidth) {
		g.drawImage(this.icon.getImage(), positionX, positionY, null);
	/*	
		if(isBlack)
		{
			g.setColor(Color.black);
		}
		else
		{
			g.setColor(Color.white);
		}
		
		g.fillOval(positionX+(int)(squareWidth*2.0/6.0), 
				positionY+squareWidth/8, 
				squareWidth/3, squareWidth/3);
		g.fillRect(positionX+(int)(squareWidth*4.0/10.0), 
				positionY+squareWidth/4, 
				squareWidth/5, squareWidth/2);
		g.fillRect(positionX+(int)(squareWidth*1.0/4.0), 
				positionY+(int)(squareWidth*3.0/5.0), 
				squareWidth/2, squareWidth/5);
		*/
	}

	@Override
	public boolean canMove(int x, int y) {
		// TODO Auto-generated method stub
		if(isBlack)
		{
			if(y == 1 && x == 0)
			{
				return true;
			}
			else if((y == 2 && x ==0) && !this.isMoved){
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(y == -1 && x == 0)
			{
				return true;
			}
			else if((y == -2 && x ==0) && !this.isMoved){
				return true;
			}
			else
			{
				return false;
			}
		}
		
	}

	@Override
	public boolean canCapture(int x, int y) {
		if(isBlack)
		{
			if((x == -1 || x == 1) && y == 1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if((x == -1 || x == 1) && y == -1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	
	@Override
	public boolean isBlocked(int pX, int pY, int x, int y, Piece[][] board) {
		if(Math.abs(x)>0&&Math.abs(y)>0)
			return false;
		else if(this.isBlack&&board[pX][pY+1]!=null&&pY+1<=7)
			return true;
		else if(!this.isBlack&&board[pX][pY-1]!=null&&pY-1>=0)
			return true;
		else
			return false;
	}
	@Override
	public String toString() {
		String color;
		if(this.getColor()=='w')
			color="white";
		else
			color="black";
		return color+"-pawn";
	}

	

}
