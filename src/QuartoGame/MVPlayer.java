package QuartoGame;

import java.util.ArrayList;
import java.util.Scanner;

public class MVPlayer extends AbstractPlayer{

	public MVPlayer(PlayerType pType, int id) {
		super(pType, id);
		// TODO Auto-generated constructor stub
	}
	public MVPlayer(PlayerType pType, int id, int d) {
		super(pType, id, d);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Piece pickPiece(Board board, Scanner scanner, boolean doMiniMax) {
		if(playerType==PlayerType.RANDOM){
			ArrayList<Piece> rp = board.getRemainingPieces();
			int i = (int) ((Math.random()*rp.size()));
			Piece p = rp.remove(i);
			board.getRemainingPieces().remove(p);
			return p;
		}
		else if(playerType==PlayerType.NOVICE || (playerType==PlayerType.MINIMAXD && !doMiniMax)){
			ArrayList<Piece> rp = new ArrayList<Piece>();
			ArrayList<Piece> grp = new ArrayList<Piece>();
			rp.addAll(board.getRemainingPieces());
			for (Piece p : rp) {
				if(board.possibleWin(p)==-1){
					grp.add(p);
				}
			}
			if(grp.size()>0){
				int i = (int) ((Math.random()*grp.size()));
				Piece p = grp.remove(i);
				board.getRemainingPieces().remove(p);
				return p;
			}
			int i = (int) ((Math.random()*rp.size()));
			Piece p = rp.remove(i);
			board.getRemainingPieces().remove(p);
			return p;	
		}
		else if((playerType==PlayerType.MINIMAXD) && doMiniMax){
			if(getBestPick()!=null){
				board.getRemainingPieces().remove(getBestPick());
				return getBestPick();
			}
			else{
				return pickPiece(board, scanner, !doMiniMax);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void placePiece(Board board, Piece piece, Scanner scanner,
			boolean doMiniMax, boolean wrongPick) {
		if(playerType==PlayerType.RANDOM){
			    ArrayList<Integer> fp = board.getFreePlaces();
	            if(fp.size()> 0){
	                int i = (int) ((Math.random()*fp.size()));
	                board.placePiece((int)fp.get(i), piece);
	            }
		}
		else if(playerType==PlayerType.NOVICE || (playerType==PlayerType.MINIMAXD && !doMiniMax)){
		       if(board.possibleWin(piece) != -1){
	                board.placePiece(board.possibleWin(piece), piece);
	            }
	            else{
	            	ArrayList<Integer> fp = board.getFreePlaces();
	   	            if(fp.size()> 0){
	   	                int i = (int) ((Math.random()*fp.size()));
	   	                board.placePiece((int)fp.get(i), piece);
	   	            }
	            }
		}
		else if((playerType==PlayerType.MINIMAXD) && doMiniMax){
		    MVNode mostValuableNode = this.miniMaxMove(this.miniMaxDepth, board, piece, this.playerID);
            if(mostValuableNode==null){
                board.placePiece(board.getFreePlaces().get(0), piece);
            }
            else{
                board.placePiece(mostValuableNode.getPlacementIndex(), piece);
                this.setBestPick(mostValuableNode.getPickedPiece());
            }
		
		}
		
		// TODO Auto-generated method stub
		
	}
	
	   public MVNode miniMaxMove(int depth, Board board, Piece givenPiece, int playerID){
	        Board copy = new Board();
	        copy.setBoard(board.getBoard());
	        copy.setPieces(board.getPieces());

	        MVNode root = new MVNode(copy, true, null, givenPiece, true, 0, playerID);
	        root.abPrune(depth, -999999999, 999999999);
	        
	        if(root.isTerminalNode()){
	            return root;
	        }
	        else {
	            if(root.getChildren().size()==0){
	                return null;
	            }
	            MVNode best = root.getChildren().remove(0);
	            
	            double bestValue = best.getValue();
	            for(MVNode child : root.getChildren()){
	            	//System.out.println("chils value: " + child.getValue());
	                if(child.getValue()>bestValue){
	                    bestValue = child.getValue();
	                    best = child;
	    	        	//System.out.println("Best pick value: " + best);
	                    this.setBestPick(child.getGivenPiece());
	                }
	            }

	            return best;
	        }
	    }
}
