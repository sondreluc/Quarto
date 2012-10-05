package QuartoGame;
import java.util.ArrayList;



public class Player {
	public enum PlayerType{RANDOM, NOVICE, HUMAN, MINIMAX3, MINIMAX4};
	private PlayerType playerType;
	private int playerID;
	
	
	public Player(PlayerType pType, int id){
		this.playerType = pType;
		this.playerID = id;
		
	}
	
	
	public Piece pickPiece(Board board){
		
		if(playerType==PlayerType.RANDOM){
			ArrayList<Piece> remainingPieces = board.getRemainingPieces();
			int i = (int) ((Math.random()*remainingPieces.size()));
			Piece p = remainingPieces.remove(i);
			board.setPieces(remainingPieces);
			return p;
		}
		
		else if(playerType==PlayerType.NOVICE){
			ArrayList<Piece> goodPicks = new ArrayList<Piece>();
			ArrayList<Piece> temp =  new ArrayList<Piece>();
			temp.addAll(board.getRemainingPieces());
			for(Piece p: temp){
				if(board.possibleWin(p)==-1){
					goodPicks.add(p);
				}
			}
			if(goodPicks.size()>0){
				int i = (int) ((Math.random()*goodPicks.size()));
				Piece p = goodPicks.get(i);
				board.getRemainingPieces().remove(p);
				return p;
			}
			
			ArrayList<Piece> remainingPieces = board.getRemainingPieces();
			int i = (int) ((Math.random()*remainingPieces.size()));
			Piece p = remainingPieces.remove(i);
			board.setPieces(remainingPieces);
			return p;
			
		}
			
		return null;
		
	}
	
	public void placePiece(Board board, Piece piece){
		if(playerType==PlayerType.RANDOM){
			ArrayList<Integer> places = board.getFreePlaces();
			
			int i = (int) ((Math.random()*places.size()));
			board.placePiece((int)places.get(i), piece);
		}
		
		else if(playerType==PlayerType.NOVICE){
			if(board.possibleWin(piece) != -1){
				board.placePiece(board.possibleWin(piece), piece);
			}
			else{
				ArrayList<Integer> places = board.getFreePlaces();
					
				int i = (int) ((Math.random()*places.size()));
				board.placePiece((int)places.get(i), piece);
			}
		}
	}



	public int getPlayerID() {
		return playerID;
	}


	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

}
