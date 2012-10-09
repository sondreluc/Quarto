package QuartoGame;

import QuartoGame.Player.PlayerType;

public class Game {
	
	private Board board;
	private Player player1;
	private Player player2;
	private Player activePlayer;
	
	public Game(Player p1, Player p2){
		this.board = new Board();
		this.player1 = p1;
		this.player2 = p2;
		this.activePlayer = p1;
	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void playTurn(boolean log){
		
		Piece piece = this.activePlayer.pickPiece(board);
		if(log){
			System.out.println();
			System.out.println("Remainding pieces:"+board.remainingToSting());
			System.out.println("Player #"+this.activePlayer.getPlayerID()+" picks "+piece.toString());
			
		}
		
		if(this.activePlayer == this.player1)
			this.activePlayer = this.player2;
		else
			this.activePlayer = this.player1;
		
		if(log){
			System.out.println("Player #"+this.activePlayer.getPlayerID()+"'s turn to place piece "+piece.toString());
		}
		
		this.activePlayer.placePiece(board, piece);
		
		if(log){
			System.out.println("Player #"+this.activePlayer.getPlayerID()+" has placed "+piece.toString());	
		}
	}
	
	public void printBoard(){
		System.out.println(board.printBoard());
		
	}
	
	public boolean isFinished(){
		return board.checkForWinner();

	}

	
	
	public static void main(String[] args){
		
		Game newGame = new Game(new Player(PlayerType.NOVICE, 1), new Player(PlayerType.NOVICE, 2));
		
		boolean finished = false;
				
		while(!finished){
			newGame.playTurn(true);
			
			newGame.printBoard();
			
			if(newGame.isFinished()){
				finished = true;
				System.out.println("The winner is: Player #"+newGame.activePlayer.getPlayerID()+"!");
			}
			else if(newGame.getBoard().getPieces().size() == 0){
				finished = true;
				System.out.println("Its a tie!");
			}	
			
		}
		
	}

}
