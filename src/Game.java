
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

	public boolean checkForWinner(){
		
		for (int i = 0; i < 4; i++) {
			ArrayList<Piece> row = board.getRow(i);
			Piece first = row.get(0);
			boolean color = true;
			boolean height = true;
			boolean shape = true;
			boolean consistensy = true;
			
			for(int j = 1; j < 4; j++){
				if(!first.getColor().equals(row.get(j).getColor())){
					color = false;
				}
				if(!first.getHeight().equals(row.get(j).getHeight())){
					height = false;
				}
				if(!first.getShape().equals(row.get(j).getShape())){
					shape = false;
				}
				if(!first.getConsistensy().equals(row.get(j).getConsistensy())){
					consistensy = false;
				}
			}
			
			if(color || height || shape || consistensy){
				return true;
			}
		}
		
		for (int i = 0; i < 4; i++) {
			ArrayList<Piece> col = board.getColumn(i);
			Piece first = col.get(0);
			boolean color = true;
			boolean height = true;
			boolean shape = true;
			boolean consistensy = true;
			
			for(int j = 1; j < 4; j++){
				if(!first.getColor().equals(col.get(j).getColor())){
					color = false;
				}
				if(!first.getHeight().equals(col.get(j).getHeight())){
					height = false;
				}
				if(!first.getShape().equals(col.get(j).getShape())){
					shape = false;
				}
				if(!first.getConsistensy().equals(col.get(j).getConsistensy())){
					consistensy = false;
				}
			}
			
			if(color || height || shape || consistensy){
				return true;
			}
		}
		
		return false;
	}


	
}
