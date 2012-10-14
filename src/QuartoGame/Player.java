package QuartoGame;
import java.util.ArrayList;
import java.util.Scanner;





public class Player {
	public enum PlayerType{RANDOM, NOVICE, HUMAN, MINIMAX3, MINIMAX4};
	private PlayerType playerType;
	private int playerID;
	private Piece bestPick;
	
	
	public Player(PlayerType pType, int id){
		this.playerType = pType;
		this.playerID = id;
		this.bestPick = null;
		
	}
	
	
	public Piece pickPiece(Board board, Scanner scanner, boolean doMiniMax){
		
		if(playerType==PlayerType.RANDOM){
			ArrayList<Piece> remainingPieces = board.getRemainingPieces();
			int i = (int) ((Math.random()*remainingPieces.size()));
			Piece p = remainingPieces.remove(i);
			board.getRemainingPieces().remove(p);
			return p;
		}
		
		else if(playerType==PlayerType.NOVICE || (playerType==PlayerType.MINIMAX3&&!doMiniMax) || (playerType==PlayerType.MINIMAX4&&!doMiniMax)){
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
			board.getRemainingPieces().remove(p);
			return p;
			
		}
		
		else if((playerType == PlayerType.MINIMAX3 || playerType == PlayerType.MINIMAX4 )  && doMiniMax){
			if(bestPick!=null){
				board.getRemainingPieces().remove(bestPick);
				return bestPick;
			}
			else{
				return pickPiece(board, scanner, !doMiniMax);
			}
		}

		
		else if(playerType == PlayerType.HUMAN){
			
			System.out.println("These are the remainding pieces:"+board.remainingToSting());
			System.out.println("Pick a piece for your opponent by writing the index of the piece(zero-indexed):");
			boolean placed = false;
			while(scanner.hasNextInt() && !placed){
				int index = scanner.nextInt();
				ArrayList<Piece> remainingPieces = new ArrayList<Piece>();
				remainingPieces.addAll(board.getRemainingPieces());
				if(index >=0 && remainingPieces.size() > index ){
					Piece p = remainingPieces.remove(index);
					board.setPieces(remainingPieces);
					placed=true;
					return p;
				}
				else{
					System.out.println("You entered an invalid index!");
					placed=true;
					return this.pickPiece(board, scanner, doMiniMax);
				}
			}
		}
			
		return null;
		
	}
	
	public void placePiece(Board board, Piece piece, Scanner scanner, boolean doMiniMax, boolean wrongPick){
		if(playerType==PlayerType.RANDOM){
			ArrayList<Integer> places = board.getFreePlaces();
			if(places.size()> 0){
				int i = (int) ((Math.random()*places.size()));
				board.placePiece((int)places.get(i), piece);
			}
			
		}
		
		else if(playerType==PlayerType.NOVICE || (playerType==PlayerType.MINIMAX3&&!doMiniMax) || (playerType==PlayerType.MINIMAX4&&!doMiniMax)){
			if(board.possibleWin(piece) != -1){
				board.placePiece(board.possibleWin(piece), piece);
			}
			else{
				ArrayList<Integer> places = board.getFreePlaces();
				int i = (int) ((Math.random()*places.size()));
				board.placePiece((int)places.get(i), piece);
			}
		}
		
		else if(playerType == PlayerType.MINIMAX3 && doMiniMax){
						
			Node node = this.miniMaxMove(3, board, piece);
			if(node==null){
				board.placePiece(board.getFreePlaces().get(0), piece);
			}
			else{
				board.placePiece(node.getPlacementIndex(), piece);
				this.setBestPick(node.getPickedPiece());
			}

		}
		else if(playerType == PlayerType.MINIMAX4){
			Node node = this.miniMaxMove(4, board, piece);
			if(node==null){
				board.placePiece(board.getFreePlaces().get(0), piece);
			}
			else{
				board.placePiece(node.getPlacementIndex(), piece);
				this.setBestPick(node.getPickedPiece());
			}
		}
		
		else if(playerType==PlayerType.HUMAN){
			
			if(!wrongPick){
				System.out.println("You are to place "+piece.toString());
				System.out.println("This is the board:");
				System.out.println(board.printBoard()+"\n");
			}
			System.out.println("The available positions are:"+board.remainingPositionsToString());
			System.out.println("Enter the position you want to place your piece");
			boolean placed = false;
			while(scanner.hasNextInt() && !placed){
				int pos = scanner.nextInt();
				if(board.getFreePlaces().contains(pos)){
					System.out.println("success");
					board.placePiece(pos, piece);
					placed = true;
					break;
				}
				else{
					System.out.println("Entered position is not free or does not exist! Pick another one");
					this.placePiece(board, piece, scanner, doMiniMax, true);
					break;
				}
			}
			
		}
	}



	public int getPlayerID() {
		return playerID;
	}


	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public Node miniMaxMove(int depth, Board board, Piece givenPiece){
		Board copy = new Board();
		copy.setBoard(board.getBoard());
		copy.setPieces(board.getPieces());
	
		Node root = new Node(copy, true, null, givenPiece, true, 0);
		root.alphabetaprun(depth, -999999999, 999999999);
		
		if(root.isTerminal()){
			return root;
		}
		else {
			if(root.getChildren().size()==0){
				return null;
			}
			Node best = root.getChildren().remove(0);

			double bestValue = best.getValue();

			for(Node child : root.getChildren()){
				if(child.getValue()>bestValue){
					bestValue = child.getValue();
					best = child;
					this.setBestPick(child.getGivenPiece());
				}
			}

			return best;
		}
	}


	public Piece getBestPick() {
		return bestPick;
	}


	public void setBestPick(Piece bestPick) {
		this.bestPick = bestPick;
	}
	
}
