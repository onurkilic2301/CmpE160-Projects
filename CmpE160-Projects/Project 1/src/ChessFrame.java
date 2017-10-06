import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessFrame extends JFrame implements MouseListener{
	//TODO add save and undo buttons
	public static final int SQUARE_WIDTH = 45;
	public static final int BOARD_MARGIN = 50;
	int selectedSquareX = -1;
	int selectedSquareY = -1;
	int threatingX=-1;
	int threatingY=-1;
	private Piece pieces[][] = new Piece[8][8];
	int turnCounter=0;
	private Stack<Move> moves=new Stack<Move>();
	JButton saveButton;
	JButton undoButton;
	JPanel panel;
	public ChessFrame()
	{
		initializeChessBoard();
		setTitle("Chess Game");
		//let the screen size fit the board size
		setSize(SQUARE_WIDTH*8+BOARD_MARGIN*2, SQUARE_WIDTH*8+BOARD_MARGIN*3);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(this);
		saveButton=new JButton("save");
		undoButton=new JButton("undo");
		panel=new JPanel();
		panel.setLayout(null);
		saveButton.setBounds(5/2*BOARD_MARGIN, BOARD_MARGIN+8*SQUARE_WIDTH, 70, 30);
		undoButton.setBounds(11/2*BOARD_MARGIN, BOARD_MARGIN+8*SQUARE_WIDTH, 70, 30);
		panel.add(saveButton);
		panel.add(undoButton);
		this.add(panel);
		saveButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				//System.out.println("You clicked the button");

				save("game.txt");

			}
		});
		undoButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				//System.out.println("You clicked the button");
				undo();
			}
		});
	}

	public void initializeChessBoard()
	{
		for(int i = 0; i<8; i++)
		{
			for(int j = 0; j<8; j++)
			{
				if(j == 1)
				{
					pieces[i][j] = new Pawn(true);
				}
				else if(j == 6)
				{
					pieces[i][j] = new Pawn(false);
				}
				else
				{
					pieces[i][j] = null;
				}
			}
		}
		pieces[0][0]=new Rook(true);
		pieces[7][0]=new Rook(true);
		pieces[0][7]=new Rook(false);
		pieces[7][7]=new Rook(false);
		pieces[1][0]=new Knight(true);
		pieces[6][0]=new Knight(true);
		pieces[1][7]=new Knight(false);
		pieces[6][7]=new Knight(false);
		pieces[2][0]=new Bishop(true);
		pieces[5][0]=new Bishop(true);
		pieces[2][7]=new Bishop(false);
		pieces[5][7]=new Bishop(false);
		pieces[4][0]=new King(true);
		pieces[4][7]=new King(false);
		pieces[3][0]=new Queen(true);
		pieces[3][7]=new Queen(false);

	}

	public char turnDecision(){
		if(turnCounter%2==0)
			return 'w';
		else
			return 'b';
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//Paint squares
		for(int i = 0; i<8; i++){	
			for(int j = 0; j<8; j++){
				g.setColor(Color.black);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
				g.drawString(""+(char)('a'+i), 3*BOARD_MARGIN/2+SQUARE_WIDTH*i-5, 3*BOARD_MARGIN/2+8*SQUARE_WIDTH);
				g.drawString(""+(8-j), BOARD_MARGIN/2, 3*BOARD_MARGIN/2+j*SQUARE_WIDTH);
				g.drawString(""+(char)('a'+i), 3*BOARD_MARGIN/2+SQUARE_WIDTH*i-5,BOARD_MARGIN/2);
				g.drawString(""+(8-j), 3*BOARD_MARGIN/2+8*SQUARE_WIDTH-10, 3*BOARD_MARGIN/2+j*SQUARE_WIDTH);
				if((i+j)%2==0)
					g.setColor(Color.cyan);
				else
					g.setColor(Color.yellow);
				g.fillRect(BOARD_MARGIN+SQUARE_WIDTH*i, BOARD_MARGIN+SQUARE_WIDTH*j, SQUARE_WIDTH, SQUARE_WIDTH);
			}

		}
		//print the board 's lines to show squares
		for(int i = 0; i<=8; i++)
		{
			g.drawLine(BOARD_MARGIN, 
					BOARD_MARGIN+(i)*SQUARE_WIDTH, 
					BOARD_MARGIN+8*SQUARE_WIDTH, 
					BOARD_MARGIN+(i)*SQUARE_WIDTH);
			g.drawLine(BOARD_MARGIN+(i)*SQUARE_WIDTH, 
					BOARD_MARGIN, 
					BOARD_MARGIN+(i)*SQUARE_WIDTH, 
					BOARD_MARGIN+8*SQUARE_WIDTH);

		}
		//print the pieces
		for(int i = 0; i<8; i++)
		{
			for(int j = 0; j<8; j++)
			{
				if(pieces[i][j] != null)
				{
					pieces[i][j].drawYourself(g, i*SQUARE_WIDTH+BOARD_MARGIN, 
							j*SQUARE_WIDTH+BOARD_MARGIN, SQUARE_WIDTH);
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("Clicked");

	}
	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("Pressed");
		//calculate which square is selected 
		selectedSquareX = (e.getX()-BOARD_MARGIN)/SQUARE_WIDTH;
		selectedSquareY = (e.getY()-BOARD_MARGIN)/SQUARE_WIDTH;
		System.out.println(selectedSquareX+","+selectedSquareY);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("Released");
		//calculate which square is targeted
		int targetSquareX = (e.getX()-BOARD_MARGIN)/SQUARE_WIDTH;
		int targetSquareY = (e.getY()-BOARD_MARGIN)/SQUARE_WIDTH;
		System.out.println(targetSquareX+","+targetSquareY+"\n");
		//if these are inside the board
		if(selectedSquareX >= 0 && selectedSquareY >= 0 &&
				selectedSquareX < 8 && selectedSquareY < 8 &&
				targetSquareX >= 0 && targetSquareY >= 0 &&
				targetSquareX < 8 && targetSquareY < 8)
		{
			System.out.println("inside");
			//if selected square is empty or not its turn
			if(pieces[selectedSquareX][selectedSquareY] != null
					&&pieces[selectedSquareX][selectedSquareY].getColor()==turnDecision())
			{
				System.out.println("selected");
				boolean isCastlingDone=false;
				//if castling is wanted to be tried
				if(selectedSquareX==4&&(selectedSquareY==0||selectedSquareY==7)){
					if(targetSquareX==7&&selectedSquareY==targetSquareY){
						System.out.println("trying castling");
						isCastlingDone=castling(true);
					}
					else if(targetSquareX==0&&selectedSquareY==targetSquareY){
						System.out.println("trying castling");
						isCastlingDone=castling(false);
					}
				}
				if(!isCastlingDone){
					String from=(char)('a'+selectedSquareX)+""+(8-selectedSquareY);
					String to=(char)('a'+targetSquareX)+""+(8-targetSquareY);
					move(from,to);
				}
			}
		}
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println("Entered");

	}
	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println("Exited");

	}

	public boolean move(String from, String to){
		//TODO check if coordinates are appropriate
		from=from.toLowerCase();
		to=to.toLowerCase();
		int j1=7-from.charAt(1)%'1';
		int i1=from.charAt(0)%'a';
		int j2=7-to.charAt(1)%'1';
		int i2=to.charAt(0)%'a';
		int diffX=i2-i1;
		int diffY=j2-j1;
		//checking if coordinates are legal 
		if((i1<8&&i1>=0)&&(j1<8&&j1>=0)&&(i2<8&&i2>=0)&&(j2<8&&j2>=0)){
			if(pieces[i1][j1]!=null){
				//Checking the turn owner
				if(pieces[i1][j1].getColor()==turnDecision()){
					//Checking if is blocked
					if(!pieces[i1][j1].isBlocked(i1, j1, diffX, diffY, pieces)){
						System.out.println("is not blocked");
						//Moving the piece to a blank area
						if(pieces[i2][j2]==null){
							System.out.println("no target");
							if(pieces[i1][j1].canMove(diffX, diffY)){
								System.out.println("can move");
								Move move=new Move(pieces[i1][j1],null,i1+""+j1,i2+""+j2,'m');
								//checking if move is promotion
								if(pieces[i1][j1] instanceof Pawn && (j2==7 && turnDecision()=='b'|| j2==0 && turnDecision()=='w')){
									if(turnDecision()=='b')
										pieces[i2][j2]=new Queen(true);
									else										
										pieces[i2][j2]=new Queen(false);
									pieces[i1][j1]=null;
								}
								else{
									pieces[i2][j2]=pieces[i1][j1];
									pieces[i1][j1]=null;
								}
								moves.push(move);
								//checking if King is under threat after the move
								if(!isInCheck()){
									turnCounter++;
									repaint();
									pieces[i2][j2].isMoved=true;
									return true;
								}
								else{
									System.out.println("is in check");
									turnCounter++;
									undo();
									return false;
								}
							}
							else{
								System.out.println("cannot move to that square");
								return false;
							}
						}
						else{
							//Checking the colors
							if(pieces[i1][j1].getColor()!=pieces[i2][j2].getColor()){
								System.out.println("target not same color");
								//Checking if King is being captured
								if(!(pieces[i2][j2] instanceof King)&&pieces[i1][j1].canCapture(diffX, diffY)){
									System.out.println("not king can capture target");
									Move move=new Move(pieces[i1][j1],pieces[i2][j2],i1+""+j1,i2+""+j2,'m');
									//checking if move is promotion
									if(pieces[i1][j1] instanceof Pawn && (j2==7 && turnDecision()=='b'|| j2==0 && turnDecision()=='w')){
										if(turnDecision()=='b')
											pieces[i2][j2]=new Queen(true);
										else										
											pieces[i2][j2]=new Queen(false);
										pieces[i1][j1]=null;
									}
									else{
										pieces[i2][j2]=pieces[i1][j1];
										pieces[i1][j1]=null;
									}
									moves.push(move);
									//checking if King is under threat after the move
									if(!isInCheck()){
										turnCounter++;
										repaint();
										pieces[i2][j2].isMoved=true;
										return true;
									}
									else{
										turnCounter++;
										undo();
										return false;
									}
								}
								else{
									System.out.println("either king or cannot capture target");

									return false;
								}
							}
							else{
								System.out.println("target same color");
								return false;
							}	
						}
					}
					else{
						System.out.println("is blocked");
						return false;
					}
				}
				else {
					System.out.println("is not your turn");
					return false;
				}
			}
			else{
				System.out.println("you did not pick a piece");
				return false;
			}
		}
		else{
			System.out.println("illegal coordinates");
			return false;
		}
	}

	public String at(String pos){	
		pos=pos.toLowerCase();
		int j=7-pos.charAt(1)%'1';
		int i=pos.charAt(0)%'a';
		if(this.pieces[i][j]!=null)
			return this.pieces[i][j].toString();
		else
			return "";
	}

	public boolean isInCheck(){
		int posX=-1;
		int posY=-1;
		char turnOwner=turnDecision();
		//Finding Kings position
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(pieces[i][j] instanceof King &&pieces[i][j].getColor()==turnOwner){
					posX=i;
					posY=j;
					break;
				}
			}
			if(posX!=-1)
				break;
		}
		//check for knight threats
		if((posX+2<8&&posY+1<8)&&pieces[posX+2][posY+1] instanceof Knight ){
			if(pieces[posX+2][posY+1].getColor()!=turnOwner){
				threatingX=posX+2;
				threatingY=posY+1;
				return true;
			}
		}
		else if((posY+2<8&&posX+1<8)&&pieces[posX+1][posY+2] instanceof Knight){
			if(pieces[posX+1][posY+2].getColor()!=turnOwner){
				threatingX=posX+1;
				threatingY=posY+2;
				return true;
			}

		}

		else if((posX-2>=0&&posY-1>=0)&&pieces[posX-2][posY-1] instanceof Knight){
			if(pieces[posX-2][posY-1].getColor()!=turnOwner){
				threatingX=posX-2;
				threatingY=posY-1;
				return true;
			}
		}
		else if((posY-2>=0&&posX-1>=0)&&pieces[posX-1][posY-2] instanceof Knight){
			if(pieces[posX-1][posY-2].getColor()!=turnOwner){
				threatingX=posX-1;
				threatingY=posY-2;
				return true;
			}
		}

		else if((posX-2>=0&&posY+1<8)&&pieces[posX-2][posY+1] instanceof Knight){
			if(pieces[posX-2][posY+1].getColor()!=turnOwner){
				threatingX=posX-2;
				threatingY=posY+1;
				return true;
			}
		}
		else if((posY-1>=0&&posX+2<8)&&pieces[posX+2][posY-1] instanceof Knight){
			if(pieces[posX+2][posY-1].getColor()!=turnOwner){
				threatingX=posX+2;
				threatingY=posY-1;
				return true;
			}
		}

		else if((posX+1<8&&posY-2>=0)&&pieces[posX+1][posY-2] instanceof Knight){
			if(pieces[posX+1][posY-2].getColor()!=turnOwner){
				threatingX=posX+1;
				threatingY=posY-2;
				return true;
			}
		}
		else if((posY+2<8&&posX-1>=0)&&pieces[posX-1][posY+2] instanceof Knight ){
			if(pieces[posX-1][posY+2].getColor()!=turnOwner){
				threatingX=posX-1;
				threatingY=posY+2;
				return true;
			}
		}


		//Check for bishop or crosswise queen threads
		int i=posX+1;
		int j=posY+1;
		while(i<8&&j<8){
			if(pieces[i][j]!=null){
				if(pieces[i][j].getColor()!=turnOwner){
					if(pieces[i][j] instanceof Bishop||pieces[i][j] instanceof Queen){
						threatingX=i;
						threatingY=j;
						return true;
					}
					else 
						break;
				}
				else
					break;
			}
			i++;
			j++;
		}
		i=posX-1;
		j=posY+1;
		while(i>=0&&j<8){
			if(pieces[i][j]!=null){
				if(pieces[i][j].getColor()!=turnOwner){
					if(pieces[i][j] instanceof Bishop||pieces[i][j] instanceof Queen){
						threatingX=i;
						threatingY=j;
						return true;
					}
					else 
						break;
				}
				else
					break;
			}
			i--;
			j++;
		}
		i=posX+1;
		j=posY-1;
		while(i<8&&j>=0){
			if(pieces[i][j]!=null){
				if(pieces[i][j].getColor()!=turnOwner){
					if(pieces[i][j] instanceof Bishop||pieces[i][j] instanceof Queen){
						threatingX=i;
						threatingY=j;
						return true;
					}
					else 
						break;
				}
				else
					break;
			}
			i++;
			j--;
		}
		i=posX-1;
		j=posY-1;
		while(i>=0&&j>=0){
			if(pieces[i][j]!=null){
				if(pieces[i][j].getColor()!=turnOwner){
					if(pieces[i][j] instanceof Bishop||pieces[i][j] instanceof Queen){
						threatingX=i;
						threatingY=j;
						return true;
					}
					else 
						break;
				}
				else
					break;
			}
			i--;
			j--;
		}
		//check for rook and horizontal vertical queen threats
		i=posX+1;
		j=posY;
		while(i<8){
			if(pieces[i][j]!=null){
				if(pieces[i][j].getColor()!=turnOwner){
					if(pieces[i][j] instanceof Rook||pieces[i][j] instanceof Queen){
						threatingX=i;
						threatingY=j;
						return true;
					}
					else 
						break;
				}
				else
					break;
			}
			i++;
		}
		i=posX-1;
		j=posY;
		while(i>=0){
			if(pieces[i][j]!=null){
				if(pieces[i][j].getColor()!=turnOwner){
					if(pieces[i][j] instanceof Rook||pieces[i][j] instanceof Queen){
						threatingX=i;
						threatingY=j;
						return true;
					}
					else 
						break;
				}
				else
					break;
			}
			i--;
		}
		i=posX;
		j=posY+1;
		while(j<8){
			if(pieces[i][j]!=null){
				if(pieces[i][j].getColor()!=turnOwner){
					if(pieces[i][j] instanceof Rook||pieces[i][j] instanceof Queen){
						threatingX=i;
						threatingY=j;
						return true;
					}
					else 
						break;
				}
				else
					break;
			}
			j++;
		}
		i=posX;
		j=posY-1;
		while(j>=0){
			if(pieces[i][j]!=null){
				if(pieces[i][j].getColor()!=turnOwner){
					if(pieces[i][j] instanceof Rook||pieces[i][j] instanceof Queen){
						threatingX=i;
						threatingY=j;
						return true;
					}
					else 
						break;
				}
				else
					break;
			}
			j--;
		}
		//check for pawn threats
		i=posX;
		j=posY;
		if(turnOwner=='w'){
			if(i+1<8&& pieces[i+1][j-1] instanceof Pawn){
				if(pieces[i+1][j-1].getColor()!=turnOwner){
					threatingX=i+1;
					threatingY=j-1;
					return true;
				}
			}
			if(i-1>=0&& pieces[i-1][j-1] instanceof Pawn){
				if(pieces[i-1][j-1].getColor()!=turnOwner){
					threatingX=i-1;
					threatingY=j-1;
					return true;
				}
			}
		}
		else{
			if((i+1<8)&& pieces[i+1][j+1] instanceof Pawn){
				if(pieces[i+1][j+1].getColor()!=turnOwner){
					threatingX=i+1;
					threatingY=j+1;
					return true;
				}
			}
			else if(i-1>=0&& pieces[i-1][j+1] instanceof Pawn){
				if(pieces[i-1][j+1].getColor()!=turnOwner){
					threatingX=i-1;
					threatingY=j+1;
					return true;
				}
			}
		}
		//check for the other King
		if((i+1<8 && pieces[i+1][j] instanceof King) && pieces[i+1][j].getColor()!=turnOwner)
			return true;
		else if((i+1<8 && j+1<8) &&pieces[i+1][j+1] instanceof King && pieces[i+1][j+1].getColor()!=turnOwner)
			return true;
		else if((j+1<8 &&pieces[i][j+1] instanceof King) && pieces[i][j+1].getColor()!=turnOwner)
			return true;
		else if((i+1<8 &&j-1>=0)&&pieces[i+1][j-1] instanceof King && pieces[i+1][j-1].getColor()!=turnOwner)
			return true;
		else if((j-1>=0 && pieces[i][j-1] instanceof King) && pieces[i][j-1].getColor()!=turnOwner)
			return true;
		else if((i-1>=0 && pieces[i-1][j] instanceof King) && pieces[i-1][j].getColor()!=turnOwner)
			return true;
		else if((i-1>=0&&j+1<8)&&pieces[i-1][j+1] instanceof King && pieces[i-1][j+1].getColor()!=turnOwner)
			return true;
		else if((i-1>=0&&j-1>=0)&&pieces[i-1][j-1] instanceof King && pieces[i-1][j-1].getColor()!=turnOwner)
			return true;
		return false;

	}
	public boolean isCheckmate(){
		if(isInCheck()){
			int posX=-1;
			int posY=-1;
			//threat's position
			int threatingX=this.threatingX;
			int threatingY=this.threatingY;
			char turnOwner=turnDecision();
			//finding King's position
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					if(pieces[i][j] instanceof King &&pieces[i][j].getColor()==turnOwner){
						posX=i;
						posY=j;
						break;
					}
					if(posX!=-1)
						break;
				}
			}
			//checking if King can move away from threat
			String from=(char)('a'+posX)+""+(8-posY);
			String to=(char)('a'+posX+1)+""+(8-posY+1);
			if(posX+1<8&&posY-1>=0&&move(from,to)){
				undo();
				return false;
			}
			to=(char)('a'+posX+1)+""+(8-posY);
			if(posX+1<8&&move(from,to)){
				undo();
				return false;
			}
			to=(char)('a'+posX+1)+""+(8-posY-1);
			if(posX+1<8&&posY+1>=0&&move(from,to)){
				undo();
				return false;
			}
			to=(char)('a'+posX)+""+(8-posY+1);
			if(posY-1>=0&&move(from,to)){
				undo();
				return false;
			}
			to=(char)('a'+posX)+""+(8-posY-1);
			if(posY+1<8&&move(from,to)){
				undo();
				return false;
			}
			to=(char)('a'+posX-1)+""+(8-posY-1);
			if(posX-1>=0&&posY+1<8&&move(from,to)){
				undo();
				return false;
			}
			//checking if threat piece can be captured or blocked
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					if(pieces[i][j]!=null&&pieces[i][j].getColor()==turnOwner){
						if(pieces[threatingX][threatingY] instanceof Knight){
							from=(char)('a'+i)+""+(8-j);
							to=(char)('a'+threatingX)+""+(8-threatingY);
							if(move(from,to)){
								undo();
								return false;
							}
						}
						else if(Math.abs(posX-threatingX)==Math.abs(posY-threatingY)){
							int i2=threatingX;
							int j2=threatingY;
							while(i2!=posX&&j2!=posY){
								from=(char)('a'+i)+""+(8-j);
								to=(char)('a'+i2)+""+(8-j2);
								if(pieces[i][j].canMove(i2-i,j2-j))	{
									if(move(from,to)){
										undo();
										return false;
									}
								}
								i2+=Math.abs(posX-threatingX)/(posX-threatingX);
								j2+=Math.abs(posY-threatingY)/(posY-threatingY);
							}
						}
						else if(Math.abs(posX-threatingX)==0){
							int i2=threatingX;
							int j2=threatingY;
							while(j2!=posY){
								from=(char)('a'+i)+""+(8-j);
								to=(char)('a'+i2)+""+(8-j2);
								if(pieces[i][j].canMove(i2-i,j2-j))	{
									if(move(from,to)){
										undo();
										return false;
									}
								}
								j2+=Math.abs(posY-threatingY)/(posY-threatingY);
							}
						}
						else if(Math.abs(posY-threatingY)==0){
							int i2=threatingX;
							int j2=threatingY;
							while(j2!=posY){
								from=(char)('a'+i)+""+(8-j);
								to=(char)('a'+i2)+""+(8-j2);
								if(pieces[i][j].canMove(i2-i,j2-j))	{
									if(move(from,to)){
										undo();
										return false;
									}
								}
								i2+=Math.abs(posX-threatingX)/(posX-threatingX);
							}
						}
					}
				}
			}
			System.out.println("checkmate");
			return true;
		}
		return false;
	}
	public boolean castling(boolean isKingSide){
		char turnOwner=turnDecision();
		Move move1;
		Move move2;
		//White king side Castling
		if(turnOwner=='w'&&isKingSide==true){
			if(pieces[4][7] instanceof King && pieces[4][7].getColor()=='w'){
				if(pieces[7][7] instanceof Rook && pieces[7][7].getColor()=='w'){
					if(pieces[5][7]==null&&pieces[6][7]==null){
						if(!this.isInCheck()){
							move1=new Move(pieces[4][7],null,4+""+7,6+""+7,'c');
							move2=new Move(pieces[7][7],null,7+""+7,5+""+7,'c');
							pieces[6][7]=pieces[4][7];
							pieces[4][7]=null;
							pieces[5][7]=pieces[7][7];
							pieces[7][7]=null;
							moves.push(move1);
							moves.push(move2);
							repaint();
							turnCounter++;
							return true;
						}
						else
							return false;

					}
					else
						return false;
				}
				else
					return false;
			}
			else
				return false;
		}
		//White queen side Castling
		else if(turnOwner=='w'&&isKingSide==false){
			if(pieces[4][7] instanceof King && pieces[4][7].getColor()=='w'){
				if(pieces[0][7] instanceof Rook && pieces[0][7].getColor()=='w'){
					if((pieces[1][7]==null&&pieces[2][7]==null)&&pieces[3][7]==null){
						if(!this.isInCheck()){
							move1=new Move(pieces[4][7],null,4+""+7,2+""+7,'c');
							move2=new Move(pieces[0][7],null,0+""+7,3+""+7,'c');
							pieces[2][7]=pieces[4][7];
							pieces[4][7]=null;
							pieces[3][7]=pieces[0][7];
							pieces[0][7]=null;
							moves.push(move1);
							moves.push(move2);
							repaint();
							turnCounter++;
							return true;
						}
						else
							return false;
					}
					else
						return false;
				}
				else
					return false;
			}
			else
				return false;
		}
		//Black king side Castling
		else if(turnOwner=='b'&&isKingSide==true){
			if(pieces[4][0] instanceof King && pieces[4][0].getColor()=='b'){
				if(pieces[7][0] instanceof Rook && pieces[7][0].getColor()=='b'){
					if(pieces[5][0]==null&&pieces[6][0]==null){
						if(!this.isInCheck()){
							move1=new Move(pieces[4][0],null,4+""+0,6+""+0,'c');
							move2=new Move(pieces[7][0],null,7+""+0,5+""+0,'c');
							pieces[6][0]=pieces[4][0];
							pieces[4][0]=null;
							pieces[5][0]=pieces[7][0];
							pieces[7][0]=null;
							moves.push(move1);
							moves.push(move2);
							repaint();
							turnCounter++;
							return true;
						}
						else 
							return false;
					}
					else
						return false;
				}
				else
					return false;
			}
			else
				return false;
		}
		//black queen side Castling
		else if(turnOwner=='b'&&isKingSide==false){
			if(pieces[4][0] instanceof King && pieces[4][0].getColor()=='b'){
				if(pieces[0][0] instanceof Rook && pieces[0][0].getColor()=='b'){
					if((pieces[1][0]==null&&pieces[2][0]==null)&&pieces[3][0]==null){
						if(!this.isInCheck()){
							move1=new Move(pieces[4][0],null,4+""+0,2+""+0,'c');
							move2=new Move(pieces[0][0],null,0+""+0,3+""+0,'c');
							pieces[2][0]=pieces[4][0];
							pieces[4][0]=null;
							pieces[3][0]=pieces[0][0];
							pieces[0][0]=null;
							moves.push(move1);
							moves.push(move2);
							repaint();							
							turnCounter++;
							return true;
						}
						else 
							return false;
					}
					else
						return false;
				}
				else
					return false;
			}
			else
				return false;
		}
		else
			return false;
	}
	public void save(String fileName){
		try{
			PrintStream p=new PrintStream(new File(fileName));
			if(turnCounter%2==1)
				p.println("black");
			else
				p.println("white");
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					if(pieces[i][j]!=null){
						p.println(pieces[i][j].toString()+"-"+(char)('a'+i)+(8-j));
					}
				}
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}

	}
	public static ChessFrame load(String fileName) {
		ChessFrame theFrame=new ChessFrame();

		try{
			Scanner s=new Scanner(new File(fileName));
			theFrame.pieces=new Piece[8][8];
			if(s.next().equalsIgnoreCase("black"))
				theFrame.turnCounter=1;
			else
				theFrame.turnCounter=0;
			while(s.hasNext()){
				Piece piece;
				String str=s.next();
				boolean isBlack;
				if(str.charAt(0)=='w')
					isBlack=false;
				else
					isBlack=true;
				str=str.substring(str.indexOf('-')+1);
				if(str.startsWith("pawn"))
					piece=new Pawn(isBlack);
				else if(str.startsWith("rook"))
					piece=new Rook(isBlack);
				else if(str.startsWith("bishop"))
					piece=new Bishop(isBlack);
				else if(str.startsWith("knight"))
					piece=new Knight(isBlack);
				else if(str.startsWith("queen"))
					piece=new Queen(isBlack);
				else if(str.startsWith("king"))
					piece=new King(isBlack);
				else
					piece=null;
				str=str.substring(str.indexOf('-')+1);
				int j=7-str.charAt(1)%'1';
				int i=str.charAt(0)%'a';
				theFrame.pieces[i][j]=piece;
			}

		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return theFrame;

	}

	public void undo(){
		if(!moves.isEmpty()){
			Move move=moves.pop();
			//undo castling
			if(move.moveType=='c'){
				int i1=move.from.charAt(0)%'0';
				int j1=move.from.charAt(1)%'0';
				int i2=move.to.charAt(0)%'0';
				int j2=move.to.charAt(1)%'0';
				pieces[i1][j1]=move.atFrom;
				pieces[i2][j2]=move.atTo;
				move=moves.pop();
				i1=move.from.charAt(0)%'0';
				j1=move.from.charAt(1)%'0';
				i2=move.to.charAt(0)%'0';
				j2=move.to.charAt(1)%'0';
				pieces[i1][j1]=move.atFrom;
				pieces[i2][j2]=move.atTo;
			}
			//undo other moves
			else{
				int i1=move.from.charAt(0)%'0';
				int j1=move.from.charAt(1)%'0';
				int i2=move.to.charAt(0)%'0';
				int j2=move.to.charAt(1)%'0';
				pieces[i1][j1]=move.atFrom;
				pieces[i2][j2]=move.atTo;
				if(pieces[i1][j1] instanceof Pawn && (pieces[i1][j1].getColor()=='w'&&j1==6||pieces[i1][j1].getColor()=='b'&&j1==1)){
					pieces[i1][j1].isMoved=false;
				}
			}
			turnCounter--;
			repaint();
		}
	}
}
