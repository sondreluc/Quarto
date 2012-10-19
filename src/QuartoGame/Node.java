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
	private int playerID;
	
	public Node(Board board, boolean maxOrMin, Node parent, Piece given, boolean root, int placement, int playerID){
		this.board = board;
		this.value = 0;
		this.max = maxOrMin;
		this.children = new ArrayList<Node>();
		this.parent = parent;
		this.terminal = false;
		this.givenPiece = given;
		this.pickedPiece = null;
		this.root = root;
		this.placementIndex = placement;
		this.playerID = playerID;
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
		if(!isMax() && this.board.checkForWinner(false)){
			this.setValue(100);
		}
		else if(isMax() && this.board.checkForWinner(false)){
			this.setValue(-100);
		}
		else{

			if(isMax()){
				
				if(this.playerID==1){
					boolean forceWin = true;
					for(Piece p : this.board.getRemainingPieces()){
						if(this.board.possibleWin(p) == -1){
							forceWin = false;
						}
					}
					if(forceWin){
						this.setValue(100);
					}
					else{
						this.setValue(this.spesialEval()*5);
					}
				}
				
				else{
					
					this.setValue(0);
				}
			}
			else{
				
				if(this.playerID==1){
					boolean forceLoss = true;
					for(Piece p : this.board.getRemainingPieces()){
						if(this.board.possibleWin(p) == -1){
							forceLoss = false;
						}
					}
					if(forceLoss){
						this.setValue(-100);
					}
					else{
						this.setValue(-this.spesialEval()*5);
					}
				}
				else{
					this.setValue(0);
				}
			}
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
		
		this.setTerminal();
		
		if(depth == 0 || this.isTerminal()){
			
			return this.evaluateNode();
			
		}
		else{
						
			if(this.isMax()){
				
				int value = min;
				
				int size = this.board.getFreePlaces().size();
				
				for (int i = 0; i < size; i++) {
					
					Board newBoard = new Board();
					newBoard.setBoard(this.board.getBoard());
					newBoard.setPieces(this.board.getPieces());
					
					int placementIndex = newBoard.getFreePlaces().get(i);	
					ArrayList<Piece> rem = new ArrayList<Piece>();
					rem.addAll(newBoard.getRemainingPieces());
					newBoard.placePiece(newBoard.getFreePlaces().get(i), this.getGivenPiece());

					for(Piece p: rem){
						
						Node newNode = new Node(newBoard, false, this, p, false, placementIndex, this.playerID);
						newNode.setPickedPiece(p);
						this.getChildren().add(newNode);
						int tempVal = newNode.alphabetaprun(depth-1, value, max);
						if(tempVal > value){
							value = tempVal;
							this.setValue(value);
							
						}
						if(value >= max){
							return value;
						}
					}
				}
				return value;
			}
			else{
				int value = max;
				
				int size = this.board.getFreePlaces().size();

				for (int i = 0; i < size; i++) {
					
					Board newBoard = new Board();
					newBoard.setBoard(this.board.getBoard());
					newBoard.setPieces(this.board.getPieces());
					
					int placementIndex = newBoard.getFreePlaces().get(i);
					
					ArrayList<Piece> rem = new ArrayList<Piece>();
					rem.addAll(newBoard.getRemainingPieces());
					newBoard.placePiece(newBoard.getFreePlaces().get(i), this.getGivenPiece());
					
					for(Piece p: rem){
						Node newNode = new Node(newBoard, true, this, p, false, placementIndex, this.playerID);
						newNode.setPickedPiece(p);
						this.getChildren().add(newNode);
						int tempVal = newNode.alphabetaprun(depth-1, min, value);
						if(tempVal < value){
							value = tempVal;
							this.setValue(value);
							
						}
						if(value <= min){

							return value;
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
	
	public int spesialEval(){
		
		int winningPos = 0;
		for (int i = 0; i < 4; i++) {

			winningPos += checkList(this.getBoard().getRow(i));
			winningPos += checkList(this.getBoard().getColumn(i));
		}
		winningPos+= checkList(this.getBoard().getDiagonalLeft());
		winningPos+= checkList(this.getBoard().getDiagonalRight());
		
		return winningPos;
	}
	
	public int checkList(ArrayList<Piece> list){
		int numberOfWinningPos = 0;
		
		if(list.contains(null)){
			ArrayList<Piece> temp = new ArrayList<Piece>();
			for(Piece p : list){
				if(p != null){
					temp.add(p);
				}
			}
			list = temp;
		}
		if(list.size()==3){
			Piece first = list.remove(0);
			boolean color = true;
			boolean height = true;
			boolean shape = true;
			boolean cons = true;
			for(Piece p : list){
				if(!first.getColor().equals(p.getColor())){
					color = false;
				}
				if(!first.getHeight().equals(p.getHeight())){
					height = false;
				}
				if(!first.getShape().equals(p.getShape())){
					shape = false;
				}
				if(!first.getConsistensy().equals(p.getConsistensy())){
					cons = false;
				}
			}
			
			if(color){
				for(Piece p : this.getBoard().getRemainingPieces()){
					if(first.getColor().equals(p.getColor())){
						numberOfWinningPos++;
					}
				}
			}
			if(shape){
				for(Piece p : this.getBoard().getRemainingPieces()){
					if(first.getShape().equals(p.getShape())){
						numberOfWinningPos++;
					}
				}
			}
			if(height){
				for(Piece p : this.getBoard().getRemainingPieces()){
					if(first.getHeight().equals(p.getHeight())){
						numberOfWinningPos++;
					}
				}
			}
			if(cons){
				for(Piece p : this.getBoard().getRemainingPieces()){
					if(first.getConsistensy().equals(p.getConsistensy())){
						numberOfWinningPos++;
					}
				}
			}
//			System.out.println("color"+color);
//			System.out.println("shape"+shape);
//			System.out.println("con"+cons);
//			System.out.println("height"+height);
//			System.out.println(numberOfWinningPos);
		}
		
		return numberOfWinningPos;
	}
	
}
