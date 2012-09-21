
public class Node {

	private Board board;
	private int value;
	
	public Node(){
		this.board = new Board();
		this.value = 0;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
