import java.util.ArrayList;


public class Player {
	public enum PlayerType{RANDOM, NOVICE, HUMAN, MINIMAX3, MINIMAX4};
	private PlayerType playerType;
	
	
	public Player(PlayerType pType){
		this.playerType = pType;
		
	}
	
	
	public Piece pickPiece(Board board){
		
		if(playerType==PlayerType.RANDOM){
			ArrayList<Piece> remainingPieces = board.getRemainingPieces()
			int i = (int) ((Math.random()*remainingPieces.size())+1);
			Piece p = remainingPieces.remove(i);
			board.setPieces(remainingPieces);
			return p;
			
		}
		
		
		
		
		return null;
		
	}
	
	public void placePiece(Board board, Piece piece){
		if(playerType==PlayerType.RANDOM){
			ArrayList<Integer> places = getFreePlaces();
			int i = (int) ((Math.random()*places.size())+1);
			board.placePiece((int)places.get(i), piece);
		}
		
		
	}

}
