
public class Move {
	Piece atFrom;
	Piece atTo;
	String from;
	String to;
	char moveType;
	public Move(Piece atFrom, Piece atTo, String from, String to,char moveType) {
		super();
		this.atFrom = atFrom;
		this.atTo = atTo;
		this.from = from;
		this.to = to;
		this.moveType=moveType;
	}
}
