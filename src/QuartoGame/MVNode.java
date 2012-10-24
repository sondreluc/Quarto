package QuartoGame;

import java.util.ArrayList;

public class MVNode {

	private Board board;
	private Piece givenPiece;
	private Piece pickedPiece;
	private int placementIndex;
	private int value;
	private boolean max;
	private ArrayList<MVNode> children;
	private MVNode parent;
	private boolean terminal;
	private boolean root;
	private int playerID;
	
	public MVNode(Board board, boolean maxOrMin, MVNode parent, Piece given, boolean root, int placement, int playerID){
		this.board = board;
		this.value = 0;
		this.max = maxOrMin;
		this.children = new ArrayList<MVNode>();
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

	public ArrayList<MVNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<MVNode> children) {
		this.children = children;
	}

	public MVNode getParent() {
		return parent;
	}

	public void setParent(MVNode parent) {
		this.parent = parent;
	}
	
	
	public int evaluateNode(){
		if(!isMax() && this.board.checkForWinner(false)){
			this.setValue(900);
		}
		else if(isMax() && this.board.checkForWinner(false)){
			this.setValue(-900);
			//System.out.println("skjerm");
		}
		else {
			if(!isMax()){
			//System.out.println("test");
			//System.out.println(this.board.printBoard());
			int score = -doMoreReseartch();
		
				this.setValue(score);
	        
			//this.setValue((int)Math.floor(score));
			//System.out.println(this.board.printBoard());
			
			}
			else if (isMax()){
				int score = doMoreReseartch();
			       // System.out.println(score + " scored");
					this.setValue(score);
			}
		}
		return this.value;
	
	}

	

	public void setRoot(boolean root) {
		this.root = root;
	}
	
	public int abPrune(int depth, int min, int max){
		
		this.setTerminalNode();
		//depth = isWinner(depth);
		if(depth == 0 || this.isTerminalNode()){
			
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
						
						MVNode newNode = new MVNode(newBoard, false, this, p, false, placementIndex, this.playerID);
						newNode.setPickedPiece(p);
						this.getChildren().add(newNode);
						int tempVal = newNode.abPrune(depth-1, value, max);
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
						MVNode newNode = new MVNode(newBoard, true, this, p, false, placementIndex, this.playerID);
						newNode.setPickedPiece(p);
						this.getChildren().add(newNode);
						int tempVal = newNode.abPrune(depth-1, min, value);
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
	
	
	public boolean isTerminalNode() {
		return this.terminal;
	}

	public void setTerminalNode() {
		if(this.board.checkForWinner(false) || this.board.getFreePlaces().size()==0){
			this.terminal = true;
		}
	}
	public int isWinner (int depth) {
		if(this.board.checkForWinner(false) && this.board.getFreePlaces().size()!=0){
			//System.out.println("nice");
			return depth+1;
		}
		return depth;
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
	public int doMoreReseartch () {
		Board testBoard = new Board();
		testBoard.setBoard(this.board.getBoard());
		testBoard.setPieces(this.board.getPieces());
		int score = 0;
		score += doCheck(testBoard , 1);
		score += doCheck(testBoard, 2);
		/*
		for (int i = 0; i < 4; i++) {
			ArrayList<Piece> row = testBoard.getRow(i);
			
			int count = 0;
			ArrayList<Piece> temp = new ArrayList<>();
			temp.addAll(row);
			for ( Piece r: row){
				if(r != null){
					count ++;
				}
				else {
					temp.remove(r);
				}
			}
			
			if(count== 2){
				if((temp.get(0).getColor() == temp.get(1).getColor()) ){
					score +=100;
					//System.out.println("jippi: " + count + " " + i);
				}
				 
				 if((temp.get(0).getConsistensy() == temp.get(1).getConsistensy()) ){
					score +=100;
					//System.out.println("jippi3");
				}
				 if((temp.get(0).getHeight() == temp.get(1).getHeight()) ){
						score +=100;
						//System.out.println("jippi4");
					}
				 if((temp.get(0).getShape() == temp.get(1).getShape()) ){
						score +=100;
						//System.out.println("jippi5");
					}
				
			}
			
			
		}
		*/
		//System.out.println("Teller " + score);
		return score;
		
		
		
	}
	public int doCheck(Board list, int type){
		int score = 0;
	
		for (int i = 0; i < 4; i++) {
			ArrayList<Piece> row = new ArrayList<>();
			if(type == 1){
			 row = list.getRow(i);
			}
			else{
				row = list.getColumn(i);
			}
			
			int count = 0;
			ArrayList<Piece> temp = new ArrayList<>();
			temp.addAll(row);
			for ( Piece r: row){
				if(r != null){
					count ++;
				}
				else {
					temp.remove(r);
				}
			}
			
			if(count== 2){
				if((temp.get(0).getColor() == temp.get(1).getColor()) ){
					score +=25;
					//System.out.println("jippi: " + count + " " + i);
				if(count == 3){
					if(temp.get(0).getColor() == temp.get(2).getColor()){
						score +=50;
					}
				}
				
				
				}
				 
				 if((temp.get(0).getConsistensy() == temp.get(1).getConsistensy()) ){
					score +=100;
					if(count == 3){
						if(temp.get(0).getConsistensy() == temp.get(2).getConsistensy()){
							score +=50;
						}
					}
					//System.out.println("jippi3");
				}
				 if((temp.get(0).getHeight() == temp.get(1).getHeight()) ){
						score +=25;
						if(count == 3){
							if(temp.get(0).getHeight() == temp.get(2).getHeight()){
								score +=50;
							}
						}
						//System.out.println("jippi4");
					}
				 if((temp.get(0).getShape() == temp.get(1).getShape()) ){
						score +=25;
						if(count == 3){
							if(temp.get(0).getShape() == temp.get(2).getShape()){
								score +=50;
							}
						}
						//System.out.println("jippi5");
					}
				
			}
		}
		return score;
	}
}
