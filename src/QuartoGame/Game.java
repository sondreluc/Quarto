package QuartoGame;

import java.util.Scanner;

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
	
	public void playTurn(boolean log, Scanner scanner, boolean doMiniMax){
		
		if(log){
			System.out.println();
			System.out.println("Remaining pieces:"+board.remainingToSting());
		}
		
		Piece piece = this.activePlayer.pickPiece(board, scanner, doMiniMax);
		this.board.setActivePiece(piece);
		if(log){
			System.out.println("Player #"+this.activePlayer.getPlayerID()+" picks "+piece.toString());
		}
		
		if(this.activePlayer == this.player1)
			this.activePlayer = this.player2;
		else
			this.activePlayer = this.player1;
		
		if(log){
			System.out.println("Player #"+this.activePlayer.getPlayerID()+"'s turn to place piece "+piece.toString());
		}
		
		this.activePlayer.placePiece(board, piece, scanner, doMiniMax, false);
		
		if(log){
			System.out.println("Player #"+this.activePlayer.getPlayerID()+" has placed "+piece.toString());	
		}
		
	}
	
	public void printBoard(){
		System.out.println(board.printBoard());
		
	}
	
	public boolean isFinished(boolean log){
		return board.checkForWinner(log);

	}

	
	
	public static void main(String[] args){
		int gameCount = 0;
		int playerOneWins = 0;
		int playerTwoWins = 0;
		int ties = 0;
		
		
		int games = 5000;
		boolean log = false;
		boolean debug = false;
		boolean printBoard = false;
		
		Player p1 = new Player(PlayerType.MINIMAX3, 1);
		Player p2 = new Player(PlayerType.RANDOM, 2);
		
		System.out.println("Plays "+games+" games!");
		while(gameCount<games){
			System.out.print(".");
			Game newGame = new Game(p1, p2);
			
			boolean finished = false;
			
			Scanner scanner = new Scanner(System.in);
			int count = 1;
			
			while(!finished){
				if(count<7){
					if(log){
						System.out.println();
						System.out.println("Round: "+count);
					}
					newGame.playTurn(debug, scanner, false);
					
					if(printBoard){
							newGame.printBoard();
					}
					
					if(newGame.isFinished(log)){
						if(log){
							System.out.println();
							System.out.println("The winner is: Player #"+newGame.activePlayer.getPlayerID()+"!");
						}
						finished = true;
						if(newGame.activePlayer.getPlayerID()==1){
							playerOneWins++;
						}
						else{
							playerTwoWins++;
						}
					}
					else if(newGame.getBoard().getPieces().size() == 0){
						System.out.println(newGame.getBoard().getPieces().size());
						finished = true;
						ties++;
						if(log){
							System.out.println("Its a tie!");
						}
					}	
				}
				else{
					if(log){
						System.out.println();
						System.out.println("Round: "+count+" Minimax engage");
					}

					newGame.playTurn(debug, scanner, true);

					if(printBoard){
						newGame.printBoard();
					}
					
					if(newGame.isFinished(log)){
						if(log){
							System.out.println();
							System.out.println("The winner is: Player #"+newGame.activePlayer.getPlayerID()+"!");
						}
						finished = true;
						if(newGame.activePlayer.getPlayerID()==1){
							playerOneWins++;
						}
						else{
							playerTwoWins++;
						}
					}
					else if(newGame.getBoard().getPieces().size() == 0){
						finished = true;
						ties++;
						if(log){
							System.out.println("Its a tie!");
						}
					}	
				}
				count++;
			}
			gameCount++;
		}
		System.out.println("DONE!");
		System.out.println("Player 1 won: "+playerOneWins+" times!");
		System.out.println("Player 2 won: "+playerTwoWins+" times!");
		System.out.println("The game tied: "+ties+" times!");
	}

}
