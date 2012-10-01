package QuartoGame;

import QuartoGame.Player.PlayerType;

public class Game {
	
	private Board board;
	private Player player1;
	private Player player2;
	
	public Game(Player p1, Player p2){
		this.board = new Board();
		this.player1 = p1;
		this.player2 = p2;
	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	
	
	public static void main(String[] args){
		
		Game newGame = new Game(new Player(PlayerType.RANDOM), new Player(PlayerType.RANDOM));
		
	}

	
}
