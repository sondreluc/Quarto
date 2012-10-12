package QuartoGame;

import java.util.ArrayList;


public class Node {

	private Board board;
	private Piece givenPiece;
	private Piece pickedPiece;
	private int placementIndex;
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
		this.placementIndex = 0;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public double getValue() {
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
	
	
	public int evaluateNode(){
	
		if(isMax() && this.board.checkForWinner(false)){
			this.setValue(100);
		}
		else if( this.board.checkForWinner(false)){
			this.setValue(-100);
		}
		else{
			this.setValue(0);
		}
		return this.value;
	
	}

	public boolean isTerminal() {
		return this.terminal;
	}

	public void setTerminal() {
		if(this.board.checkForWinner(false) || this.board.getFreePlaces().size()==0){
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
	
	public int alphabetaprun(int depth, int min, int max){
		if(depth == 0 || this.isTerminal()){
			
			return this.evaluateNode();
			
		}
		else{
			
			Board newBoard = new Board();
			System.out.println("before set:"+this.board.getRemainingPieces().size());
			newBoard.setBoard(this.board.getBoard());
			newBoard.setPieces(this.board.getPieces());
			
			if(this.isMax()){
				int value = min;

				for (int i = 0; i < newBoard.getFreePlaces().size(); i++) {
					ArrayList<Piece> rem = new ArrayList<Piece>();
					rem.addAll(this.board.getRemainingPieces());
					newBoard.placePiece(newBoard.getFreePlaces().get(i), this.getGivenPiece());

					for(Piece p: rem){
						
						Node newNode = new Node(newBoard, false, this, p, false);
						newNode.setPlacementIndex(i);
						int tempVal = newNode.alphabetaprun(depth-1, value, max);
						this.getChildren().add(newNode);
						if(tempVal > value){
							value = tempVal;
						}
						if(value > max){
							this.setValue(max);
							return max;
						}
					}
				}
				return value;
			}
			else{
				int value = max;

				for (int i = 0; i < this.board.getFreePlaces().size(); i++) {
					newBoard.setBoard(this.board.getBoard());
					newBoard.setPieces(this.board.getPieces());
					newBoard.placePiece(newBoard.getFreePlaces().get(i), this.getGivenPiece());
					ArrayList<Piece> rem = new ArrayList<Piece>();
					rem.addAll(newBoard.getRemainingPieces());
					for(Piece p: rem){
						
						Node newNode = new Node(newBoard, false, this, p, false);
						newNode.setPlacementIndex(i);
						int tempVal = newNode.alphabetaprun(depth-1, min, value);
						this.getChildren().add(newNode);
						if(tempVal < value){
							value = tempVal;
						}
						if(value < min){
							this.setValue(min);
							return min;
						}
					}
				}
				return value;
			}
		}
	}

	public int getPlacementIndex() {
		return placementIndex;
	}

	public void setPlacementIndex(int placementIndex) {
		this.placementIndex = placementIndex;
	}
	
}
