package QuartoGame;
import java.util.ArrayList;
import java.util.Scanner;




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
		
		else if(playerType == PlayerType.HUMAN){
			System.out.println("These are the remainding pieces:"+board.remainingToSting());
			
			Scanner scan = new Scanner(System.in);
			scan.reset();
			System.out.println("Pick a piece for your opponent by writing the index of the piece(zero-indexed):");
			String index = scan.nextLine();
			ArrayList<Piece> remainingPieces = new ArrayList<Piece>();
			remainingPieces.addAll(board.getRemainingPieces());
			if((Integer.parseInt(index) >=0 && remainingPieces.size()>(Integer.parseInt(index)))){
				Piece p = remainingPieces.remove(Integer.parseInt(index));
				board.setPieces(remainingPieces);
				scan.close();
				return p;
			}
			else{
				System.out.println("You entered an invalid index!");
				scan.close();
				return this.pickPiece(board);
			}
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
		else if(playerType==PlayerType.HUMAN){
			
			Scanner scan = new Scanner(System.in);
			scan.reset();
			System.out.println("You are to place "+piece.toString());
			System.out.println("This is the board:");
			
			System.out.println(board.printBoard()+"\n");
			
			System.out.println("The available positions are:"+board.remainingPositionsToString());
			System.out.println("Enter the position you want to place your piece");
			String position = scan.nextLine();
			int pos = Integer.parseInt(position);
			if(board.getFreePlaces().contains((Integer)pos)){
				board.placePiece(pos, piece);
			}
			else{
				System.out.println("Entered position is not free or does not exsist!");
				this.placePiece(board, piece);
			}
			scan.close();
			
		}
	}



	public int getPlayerID() {
		return playerID;
	}


	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public void miniMaxMove(int depth, Board board, Piece givenPiece){
		Node root = new Node(board, true, null, givenPiece, true);
		root.makeTree(depth);
		root.evaluateNode(depth);
		
	}
	
	
	
}
