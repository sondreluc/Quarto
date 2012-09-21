
import java.util.ArrayList;


public class Game {
	
	private Board board;
	private ArrayList<Piece> pieces;
	
	public Game(){
		this.board = new Board();
		this.pieces = new ArrayList<Piece>();
		for(Piece.Color c: Piece.Color.values()){
			for(Piece.Height h: Piece.Height.values()){
				for(Piece.Shape s: Piece.Shape.values()){
					for(Piece.Consistensy con: Piece.Consistensy.values()){
						pieces.add(new Piece(c, h, s, con));
					}
				}
			}
		}
	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}


	
}
