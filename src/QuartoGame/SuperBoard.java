package QuartoGame;

import java.util.ArrayList;

import QuartoGame.Piece.Color;
import QuartoGame.Piece.Consistensy;
import QuartoGame.Piece.Height;
import QuartoGame.Piece.Shape;

public class SuperBoard extends Board {
	
	private boolean[] winningPieceSorts = new boolean[8];
	private boolean[][] possibleWins = new boolean[10][8];
	private int[] pieceCounts = new int[10];
	private ArrayList<Piece> notWinningPieces = new ArrayList<Piece>();
	
	private boolean log = false;
	
	
	public SuperBoard() {
		super();
		this.notWinningPieces = (ArrayList<Piece>) getRemainingPieces().clone();
	}
	
	public SuperBoard(boolean log){
		super();
		this.notWinningPieces = (ArrayList<Piece>) getRemainingPieces().clone();
		this.log = log;
	}
	
	@Override
	public void placePiece(int place, Piece piece) {
		if(!this.getBoard().isEmpty()){
			if(this.getBoard().get(place) == null){
				this.getBoard().set(place, piece);
				this.getPieces().remove(piece);
				this.notWinningPieces.remove(piece);
				
				int col = place % 4;
				int row = (int) Math.floor((double)place / 4);
				boolean dl = col + row == 3;
				boolean dr = col == row;
				boolean updated = false;
				pieceCounts[row]++;
				pieceCounts[4 + col]++;
				if(dl) pieceCounts[8]++;
				else if(dr) pieceCounts[9]++;
				
				if(pieceCounts[row] > 2){
					updateWinningSorts(row, piece);
					updated = true;
				}
				if(pieceCounts[4 + col] > 2){
					updateWinningSorts(4 + col, piece);
					updated = true;
				}
				if(dl && pieceCounts[8] > 2){
					updateWinningSorts(8, piece);
					updated = true;
				}
				else if(dr && pieceCounts[9] > 2){
					updateWinningSorts(9, piece);
					updated = true;
				}
				if(updated){
					notWinningPieces.clear();
					for(Piece p : getRemainingPieces()){
						if(p.getColor() == Color.WHITE){
							if(winningPieceSorts[0]) continue;
						}
						else if(winningPieceSorts[1]) continue;
						if(p.getHeight() == Height.SHORT){
							if(winningPieceSorts[2]) continue;
						}
						else if(winningPieceSorts[3]) continue;
						if(p.getShape() == Shape.ROUND){
							if(winningPieceSorts[4]) continue;
						}
						else if(winningPieceSorts[5]) continue;
						if(p.getConsistensy() == Consistensy.HOLLOW){
							if(winningPieceSorts[6]) continue;
						}
						else if(winningPieceSorts[7]) continue;
						
						notWinningPieces.add(p);
					}
				}
			}
			else{
				System.out.println("This place is allready taken!");
			}
		}
	}
	
	public ArrayList<Piece> getNotWinningPieces(){
		return notWinningPieces;
	}
	
	public boolean[] getWinningPieceSorts(){
		return winningPieceSorts;
	}
	
	public boolean[][] getPossibleWins(){
		return possibleWins;
	}
	
	public int[] getPieceCounts(){
		return pieceCounts;
	}
	
	private void updateWinningSorts(int rowIndex, Piece piece){
		if(pieceCounts[rowIndex] == 3){
			boolean color = true;
	    	boolean height = true;
	    	boolean shape = true;
	    	boolean consistency = true;
	    	Color c = piece.getColor();
	    	Height h = piece.getHeight();
	    	Shape s = piece.getShape();
	    	Consistensy cons = piece.getConsistensy();
	    	
	    	ArrayList<Piece> pieces;
	    	if(rowIndex < 4) pieces = getRow(rowIndex);
	    	else if(rowIndex < 8) pieces = getColumn(rowIndex - 4);
	    	else if(rowIndex == 8) pieces = getDiagonalLeft();
	    	else pieces = getDiagonalRight();
	    	for(int i = 0; i < 4; i++){
	    		if(pieces.get(i) != null && pieces.get(i) != piece){ // sjekk her for feil
		    		if(color && pieces.get(i).getColor() != c) color = false;
		    		if(height && pieces.get(i).getHeight() != h) height = false;
		    		if(shape && pieces.get(i).getShape() != s) shape = false;
		    		if(consistency && pieces.get(i).getConsistensy() != cons) consistency = false;
	    		}
	    	}
	    	if(color){
	    		int sortIndex = c == Color.WHITE? 0 : 1;
	    		possibleWins[rowIndex][sortIndex] = true;
	    		winningPieceSorts[sortIndex] = true;
	    	}
	    	if(height){
	    		int sortIndex = h == Height.SHORT? 2 : 3;
	    		possibleWins[rowIndex][sortIndex] = true;
	    		winningPieceSorts[sortIndex] = true;
	    	}
	    	if(shape){
	    		int sortIndex = s == Shape.ROUND? 4 : 5;
	    		possibleWins[rowIndex][sortIndex] = true;
	    		winningPieceSorts[sortIndex] = true;
	    	}
	    	if(consistency){
	    		int sortIndex = cons == Consistensy.HOLLOW? 6 : 7;
	    		possibleWins[rowIndex][sortIndex] = true;
	    		winningPieceSorts[sortIndex] = true;
	    	}
		}
		else{
			for(int i=0; i < possibleWins[rowIndex].length; i++){
				if(possibleWins[rowIndex][i]){
					possibleWins[rowIndex][i] = false;
					winningPieceSorts[i] = false;
					for(int j=0; j < possibleWins.length; j++){
						if(j != rowIndex && possibleWins[j][i]){
							winningPieceSorts[i] = true;
							break;
						}
					}
				}
			}
		}
	}

	public void setSuper(SuperBoard gameState) {
		this.notWinningPieces.clear();
		this.notWinningPieces.addAll(gameState.getNotWinningPieces());//.clone();
		for(int i = 0; i < gameState.getWinningPieceSorts().length; i++){
			this.winningPieceSorts[i] = gameState.getWinningPieceSorts()[i];
		}
		
		for(int i = 0; i < gameState.getPossibleWins().length; i++){
			for(int j = 0; j < gameState.getPossibleWins()[0].length; j++){
				this.possibleWins[i][j] = gameState.getPossibleWins()[i][j];
			}
		}
		
		for(int i = 0; i < gameState.getPieceCounts().length; i++){
			this.pieceCounts[i] = gameState.getPieceCounts()[i];
		}
	}
}
