import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Rook extends Piece{
	public Rook(boolean isBlack){
		this.isBlack=isBlack;
		if(this.isBlack){
			this.icon=new ImageIcon("src/Rook_B_Icon.png");
		}
		else{
			this.icon=new ImageIcon("src/Rook_W_Icon.png");
		}
	}
	
	@Override
	public void drawYourself(Graphics g, int positionX, int positionY, int squareWidth) {
		g.drawImage(this.icon.getImage(), positionX, positionY, null);
		
	}

	@Override
	public boolean canMove(int x, int y) {
		if((x==0&&Math.abs(y)>0)||(y==0&&Math.abs(x)>0))
			return true;
		return false;
	}

	@Override
	public boolean canCapture(int x, int y) {
		if((x==0&&Math.abs(y)>0)||(y==0&&Math.abs(x)>0))
			return true;
		return false;
	}

	@Override
	public boolean isBlocked(int pX, int pY, int x, int y, Piece[][] board) {
		if((Math.abs(x)==1&&y==0)||(Math.abs(y)==1&&x==0))
			return false;
		else if(this.canMove(x,y)){
			if(Math.abs(x)>0){
				for(int i=pX+Math.abs(x)/x;i!=pX+x&&(i<8&&i>=0);i+=Math.abs(x)/x){
					if(board[i][pY]!=null)
						return true;
				}
				return false;
			}
			else{
				for(int i=pY+Math.abs(y)/y;i!=pY+y&&(i<8&&i>=0);i+=Math.abs(y)/y){
					if(board[pX][i]!=null)
						return true;
				}
				return false;
				
			}
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
		return color+"-rook";
	}

	

}
