import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Queen extends Piece {
	
	public Queen(boolean isBlack){
		this.isBlack=isBlack;
		if(this.isBlack){
			this.icon=new ImageIcon("src/Queen_B_Icon.png");
		}
		else{
			this.icon=new ImageIcon("src/Queen_W_Icon.png");
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
		else if((x==0&&Math.abs(y)>0)||(y==0&&Math.abs(x)>0))
			return true;
		else
			return false;
	}

	@Override
	public boolean canCapture(int x, int y) {
		if(x==y||x==-1*y)
			return true;
		else if((x==0&&Math.abs(y)>0)||(y==0&&Math.abs(x)>0))
			return true;
		else
			return false;
	}


	@Override
	public boolean isBlocked(int pX, int pY, int x, int y, Piece[][] board) {
		if(Math.abs(x)==1&&Math.abs(y)==1)
			return false;
		else if((Math.abs(x)==1&&y==0)||(Math.abs(y)==1&&x==0))
			return false;
		else if(this.canMove(x,y)&&(Math.abs(x)!=Math.abs(y))){
			if(Math.abs(x)>0){
				for(int i=pX+Math.abs(x)/x;i!=pX+x;i+=Math.abs(x)/x){
					if(board[i][pY]!=null)
						return true;
				}
				return false;
			}
			else{
				for(int i=pY+Math.abs(y)/y;i!=pY+y;i+=Math.abs(y)/y){
					if(board[pX][i]!=null)
						return true;
				}
				return false;
				
			}
		}
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
		return color+"-queen";
	}


	
}
