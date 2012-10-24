package QuartoGame;

import java.util.ArrayList;

import QuartoGame.Piece.Color;
import QuartoGame.Piece.Consistensy;
import QuartoGame.Piece.Height;
import QuartoGame.Piece.Shape;

public class SuperNode {
    private Board gameState;
    private ArrayList<SuperNode> children;
    private boolean isMe;
    private boolean isPlace;
    
    private int lastPlace = -1;
    private boolean superBoard;

    public SuperNode(Board gameState, boolean isMe, boolean isPlace, int place){
        this.gameState = gameState;
        this.isMe = isMe;
        this.isPlace = isPlace;
        this.lastPlace = place;
        this.superBoard = gameState instanceof SuperBoard;
    }

    public int bestPlace(int depth, boolean heuristic){
        ArrayList<Integer> bestPlaces = new ArrayList<Integer>();
        int bestValue = -10000;
        if(!superBoard){
        	int size = gameState.getFreePlaces().size();
        	for (int i = 0; i < size; i++) {
                Board newGameState = new Board();
                newGameState.setBoard(gameState.getBoard());
                newGameState.setPieces(gameState.getPieces());
                newGameState.placePiece(gameState.getFreePlaces().get(i), gameState.getActivePiece());
                SuperNode newNode = new SuperNode(newGameState, true, false, gameState.getFreePlaces().get(i));
                int newValue = newNode.getValue(depth - 1, -10000, 10000, true, heuristic);
                if(newValue > bestValue) {
                    bestValue = newValue;
                    bestPlaces.clear();
                    bestPlaces.add(gameState.getFreePlaces().get(i));
                }
                else if(newValue == bestValue){
                	bestPlaces.add(gameState.getFreePlaces().get(i));
                }
            }
        }
        else{
        	int size = gameState.getFreePlaces().size();
        	for (int i = 0; i < size; i++) {
                Board newGameState = new SuperBoard();
                ((SuperBoard)newGameState).setSuper((SuperBoard) gameState);
                newGameState.setBoard(gameState.getBoard());
                newGameState.setPieces(gameState.getPieces());
                newGameState.placePiece(gameState.getFreePlaces().get(i), gameState.getActivePiece());
                SuperNode newNode = new SuperNode(newGameState, true, false, gameState.getFreePlaces().get(i));
                int newValue = newNode.getValue(depth - 1, -10000, 10000, true, heuristic);
                if(newValue > bestValue) {
                    bestValue = newValue;
                    bestPlaces.clear();
                    bestPlaces.add(gameState.getFreePlaces().get(i));
                }
                else if(newValue == bestValue){
                	bestPlaces.add(gameState.getFreePlaces().get(i));
                }
            }
        }
        return bestPlaces.get((int) Math.floor(bestPlaces.size() * Math.random()));
    }

    public Piece bestPiece(int depth, boolean heuristic){
        
        int bestValue = -10000;
        ArrayList<Piece> bestPieces = new ArrayList<Piece>();
        if(!superBoard){
        	int size = gameState.getRemainingPieces().size();
        	for (int i = 0; i < size; i++) {
                Board newGameState = new Board();

                newGameState.setBoard(gameState.getBoard());
                newGameState.setPieces(gameState.getPieces());
                newGameState.setActivePiece(gameState.getRemainingPieces().get(i));
                SuperNode newNode = new SuperNode(newGameState, false, true, -1);
                int newValue = newNode.getValue(depth, -10000, 10000, true, heuristic);
                if(newValue > bestValue) {
                	bestValue = newValue;
                    bestPieces.clear();
                    bestPieces.add(gameState.getRemainingPieces().get(i));
                }
                else if(newValue == bestValue){
                	bestPieces.add(gameState.getRemainingPieces().get(i));
                }
            }
        }
        else{
        	int size = ((SuperBoard)gameState).getNotWinningPieces().size();
        	if(size == 0) return gameState.getRemainingPieces().get(0);
        	for (int i = 0; i < size; i++) {
                Board newGameState = new SuperBoard();
                ((SuperBoard)newGameState).setSuper((SuperBoard) gameState);
                newGameState.setBoard(gameState.getBoard());
                newGameState.setPieces(gameState.getPieces());
                newGameState.setActivePiece(((SuperBoard)gameState).getNotWinningPieces().get(i));
                SuperNode newNode = new SuperNode(newGameState, false, true, -1);
                int newValue = newNode.getValue(depth, -10000, 10000, true, heuristic);
                if(newValue > bestValue) {
                	bestValue = newValue;
                    bestPieces.clear();
                    bestPieces.add(((SuperBoard)gameState).getNotWinningPieces().get(i));
                }
                else if(newValue == bestValue){
                	bestPieces.add(((SuperBoard)gameState).getNotWinningPieces().get(i));
                }
            }
        }
        
        return bestPieces.get((int) Math.floor(bestPieces.size() * Math.random()));
    }

    private int getValue(int depth, int initAlpha, int initBeta, boolean checkForWin, boolean heuristic){
    	if(!isPlace){
    		if(checkForWin && checkForWin()){
	            return isMe? 9999 : -9999;
	        }
    		else{
    			if(superBoard){
    				if(((SuperBoard)gameState).getNotWinningPieces().size() == 0)
    					return isMe? -9998 : 9999;
    				else if(depth == 0){
        	            if(heuristic) return heuristicValueOfGameState();
        	            else return 0;
        			}
    				checkForWin = false;
    			}
    			else if(depth == 0){
    	            return 0;
    			}
    		}
        }
      	int alpha = initAlpha;
        int beta = initBeta;
        makeChildren();

        if (isMe){
        	int newDepth = depth;
        	if(isPlace) newDepth--;
        	for(SuperNode child : children){
                // Se her etter feil ;)
            	alpha = Math.max(alpha, child.getValue(newDepth, alpha, beta, checkForWin, heuristic));
                if (beta <= alpha) break; // (Beta cut-off)
            }
            return alpha;
        }
        else{
        	int newDepth = depth;
        	if(isPlace) newDepth--;
        	for(SuperNode child : children){
            	beta = Math.min(beta, child.getValue(newDepth, alpha, beta, checkForWin, heuristic));
                if (beta <= alpha) break; // (Alpha cut-off)
            }
            return beta;
        }
    }

    private void makeChildren(){
        if(isPlace){
            children = new ArrayList<SuperNode>();
            int size = gameState.getFreePlaces().size();

            for (int i = 0; i < size; i++) {
            	Board newGameState;
            	if(!superBoard) newGameState = new Board();
            	else{
            		newGameState = new SuperBoard();
            		((SuperBoard)newGameState).setSuper((SuperBoard) gameState);
            	}

                newGameState.setBoard(gameState.getBoard());
                newGameState.setPieces(gameState.getPieces());
                newGameState.placePiece(gameState.getFreePlaces().get(i), gameState.getActivePiece());
                children.add(new SuperNode(newGameState, isMe, false, gameState.getFreePlaces().get(i)));
            }
        }
        else{
            children = new ArrayList<SuperNode>();
            
            if(!superBoard){
            	int size = gameState.getRemainingPieces().size();
            	for (int i = 0; i < size; i++) {
	                Board newGameState = new Board();
	
	                newGameState.setBoard(gameState.getBoard());
	                newGameState.setPieces(gameState.getPieces());
	                newGameState.setActivePiece(gameState.getRemainingPieces().get(i));
	                children.add(new SuperNode(newGameState, !isMe, true, -1));
	            }
            }
            else{
            	int size = ((SuperBoard)gameState).getNotWinningPieces().size();
            	for (int i = 0; i < size; i++) {
	                Board newGameState = new SuperBoard();
	                ((SuperBoard)newGameState).setSuper((SuperBoard) gameState);
	                newGameState.setBoard(gameState.getBoard());
	                newGameState.setPieces(gameState.getPieces());
	                newGameState.setActivePiece(((SuperBoard)gameState).getNotWinningPieces().get(i));
	                children.add(new SuperNode(newGameState, !isMe, true, -1));
	            }
            }
        }
    }
    
    private int heuristicValueOfGameState(){
		SuperBoard state = (SuperBoard) gameState;
		int fortegn = state.getNotWinningPieces().size() % 2 == 1? 1 : -1;
		fortegn *= isMe? 1 : -1;
		return fortegn * ( 9000 / state.getNotWinningPieces().size());
    }
    
    private boolean checkForWin(){
		int col = lastPlace % 4;
		int row = (int) Math.floor((double)lastPlace / 4);
		if(checkRow(gameState.getColumn(col))) return true;
		if(checkRow(gameState.getRow(row))) return true;
		if(col==row && checkRow(gameState.getDiagonalRight())) return true;
		else if(col + row == 3 && checkRow(gameState.getDiagonalLeft())) return true;
		
		return false;
    }
    
    private boolean checkRow(ArrayList<Piece> pieces){
    	if(pieces.get(0) == null) return false;
    	Color c = pieces.get(0).getColor();
    	Height h = pieces.get(0).getHeight();
    	Shape s = pieces.get(0).getShape();
    	Consistensy cons = pieces.get(0).getConsistensy();
    	boolean color = true;
    	boolean height = true;
    	boolean shape = true;
    	boolean consistency = true;
    	for(int i = 1; i < 4; i++){
    		if(pieces.get(i) == null) return false;
    		if(color && pieces.get(i).getColor() != c) color = false;
    		if(height && pieces.get(i).getHeight() != h) height = false;
    		if(shape && pieces.get(i).getShape() != s) shape = false;
    		if(consistency && pieces.get(i).getConsistensy() != cons) consistency = false;
    	}
    	return color || height || shape || consistency;
    }
}
