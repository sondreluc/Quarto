package QuartoGame;

import java.util.ArrayList;


public class Node {

	private Board board;
	private Piece givenPiece;
	private Piece pickedPiece;
	private int value;
	private boolean max;
	private ArrayList<Node> children;
	private Node parent;
	private boolean terminal;
	private boolean root;
	
	public Node(Board board, boolean maxOrMin, Node parent, Piece given, boolean root){
		this.board = board;
		this.value = 0;
		this.max = maxOrMin;
		this.children = new ArrayList<Node>();
		this.parent = parent;
		this.terminal = false;
		this.givenPiece = given;
		this.pickedPiece = null;
		this.root = root;
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

	public boolean isMax() {
		return max;
	}

	public void setMax(boolean max) {
		this.max = max;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	
	public int evaluateNode(int depth){
		if(isTerminal() || depth <= 0){
			if(isMax() && this.board.checkForWinner()){
				this.setValue(100);
			}
			else if( this.board.checkForWinner()){
				this.setValue(-100);
			}
			else{
				this.setValue(0);
			}
			return this.value;
		}
		int childvalue = 0; //Usikker på hva denne bør starte på.
		for(Node n: this.children){
			if(this.isMax()){
				if(childvalue < n.evaluateNode(depth-1)){
					childvalue = n.evaluateNode(depth-1);
				}
			}
			else{
				if(childvalue > n.evaluateNode(depth-1)){
					childvalue = n.evaluateNode(depth-1);
				}
			}
		}
		this.setValue(childvalue);
		return this.value;
	}

	public boolean isTerminal() {
		return this.terminal;
	}

	public void setTerminal() {
		if(this.board.checkForWinner() || this.board.getFreePlaces().size()==0){
			this.terminal = true;
		}
		this.terminal = false;
	}

	public Piece getPickedPiece() {
		return pickedPiece;
	}

	public void setPickedPiece(Piece pickedPiece) {
		this.pickedPiece = pickedPiece;
	}

	public Piece getGivenPiece() {
		return givenPiece;
	}

	public void setGivenPiece(Piece givenPiece) {
		this.givenPiece = givenPiece;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}
	
	public void makeTree(int depth){
		if(depth > 0){
			Board newBoard = new Board();
			for (int i = 0; i < newBoard.getFreePlaces().size(); i++) {
				newBoard.setBoard(this.board.getBoard());
				newBoard.setPieces(this.board.getPieces());
				newBoard.placePiece(newBoard.getFreePlaces().get(i), this.getGivenPiece());
				for(Piece p: newBoard.getRemainingPieces()){
					if(this.isMax()){
						Node newNode = new Node(newBoard, false, this, p, false);
						newNode.makeTree(depth-1);
						this.children.add(newNode);
					}
					else{
						Node newNode = new Node(newBoard, true, this, p, false);
						newNode.makeTree(depth-1);
						this.children.add(newNode);
					}
				}
			}
		}
	}
	
}
