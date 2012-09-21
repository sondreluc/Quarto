import java.util.ArrayList;


public class Board {
	private ArrayList<ArrayList<Piece>> board;
	

	public Board(){
		this.board = new ArrayList<ArrayList<Piece>>();
		for(int i=0; i<4; i++){
			ArrayList<Piece> emptyRow = new ArrayList<Piece>();
			emptyRow.add(null);
			emptyRow.add(null);
			emptyRow.add(null);
			emptyRow.add(null);
			board.add(emptyRow);
		}
	}
	
	public void clearBoard(){
		board.clear();
		for(int i=0; i<4; i++){
			ArrayList<Piece> emptyRow = new ArrayList<Piece>();
			emptyRow.add(null);
			emptyRow.add(null);
			emptyRow.add(null);
			emptyRow.add(null);
			board.add(emptyRow);
		}
	}
	
	public void placePiece(int x, int y, Piece piece){
		if(!this.board.isEmpty()){
			if(this.board.get(x).get(y) == null){
				this.board.get(x).set(y, piece);
			}
			else{
				System.out.println("This place is allready taken!");
			}
		}
	}
	
	public Piece getPiece(int x, int y){
		if(!this.board.isEmpty()){
			if(this.board.get(x).get(y) != null){
				return this.board.get(x).get(y);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}

	public ArrayList<ArrayList<Piece>> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<ArrayList<Piece>> board) {
		this.board = board;
	}
	
	
}
