import java.util.ArrayList;

import com.sun.corba.se.impl.interceptors.PICurrent;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class Board {
	
	private ArrayList<Piece> board;
	private ArrayList<Piece> pieces;
	

	public Board(){
		this.board = new ArrayList<Piece>();
		for(int i=0; i<16; i++){
			board.add(null);
		}
		this.pieces = new ArrayList<Piece>();
		for(Piece.Color c: Piece.Color.values()){
			for(Piece.Height h: Piece.Height.values()){
				for(Piece.Shape s: Piece.Shape.values()){
					for(Piece.Consistensy con: Piece.Consistensy.values()){
						pieces.add(new Piece(c, h, s, con));
					}
				}
			}
		}
	}
	
	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}

	public boolean checkForWinner(){
		
		for (int i = 0; i < 4; i++) {
			ArrayList<Piece> row = this.getRow(i);
			Piece first = row.remove(0);
			
			boolean color = true;
			boolean height = true;
			boolean shape = true;
			boolean consistensy = true;
			
			for (Piece piece : row) {
				if(!first.getColor().equals(piece.getColor())){
					color = false;
				}
				if(!first.getHeight().equals(piece.getHeight())){
					height = false;
				}
				if(!first.getShape().equals(piece.getShape())){
					shape = false;
				}
				if(!first.getConsistensy().equals(piece.getConsistensy())){
					consistensy = false;
				}
			}			
			if(color || height || shape || consistensy){
				return true;
			}
		}
		
		for (int i = 0; i < 4; i++) {
			ArrayList<Piece> col = this.getColumn(i);
			Piece first = col.remove(0);
			boolean color = true;
			boolean height = true;
			boolean shape = true;
			boolean consistensy = true;
			
			for (Piece piece : col) {
				if(!first.getColor().equals(piece.getColor())){
					color = false;
				}
				if(!first.getHeight().equals(piece.getHeight())){
					height = false;
				}
				if(!first.getShape().equals(piece.getShape())){
					shape = false;
				}
				if(!first.getConsistensy().equals(piece.getConsistensy())){
					consistensy = false;
				}
			}
			
			if(color || height || shape || consistensy){
				return true;
			}
		}
		
		for (int i = 0; i < 2; i++) {
			if(i==0){
				ArrayList<Piece> diaR = this.getDiagonalRight();
				Piece first = diaR.remove(0);
				boolean color = true;
				boolean height = true;
				boolean shape = true;
				boolean consistensy = true;
				
				for (Piece piece : diaR) {
					if(!first.getColor().equals(piece.getColor())){
						color = false;
					}
					if(!first.getHeight().equals(piece.getHeight())){
						height = false;
					}
					if(!first.getShape().equals(piece.getShape())){
						shape = false;
					}
					if(!first.getConsistensy().equals(piece.getConsistensy())){
						consistensy = false;
					}
				}
				
				if(color || height || shape || consistensy){
					return true;
				}
			}
			else{
				ArrayList<Piece> diaL = this.getDiagonalLeft();
				Piece first = diaL.remove(0);
				boolean color = true;
				boolean height = true;
				boolean shape = true;
				boolean consistensy = true;
				
				for (Piece piece : diaL) {
					if(!first.getColor().equals(piece.getColor())){
						color = false;
					}
					if(!first.getHeight().equals(piece.getHeight())){
						height = false;
					}
					if(!first.getShape().equals(piece.getShape())){
						shape = false;
					}
					if(!first.getConsistensy().equals(piece.getConsistensy())){
						consistensy = false;
					}
				}
				
				if(color || height || shape || consistensy){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void clearBoard(){
		board.clear();
		for(int i=0; i<16; i++){
			board.add(null);
		}
	}
	
	public void placePiece(int index, Piece piece){
		if(!this.board.isEmpty()){
			if(this.board.get(index) == null){
				this.board.add(index, piece);
				this.pieces.remove(piece);
			}
			else{
				System.out.println("This place is allready taken!");
			}
		}
	}
	
	public Piece getPiece(int index){
		if(!this.board.isEmpty()){
			if(this.board.get(index)!= null){
				return this.board.get(index);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}

	public ArrayList<Integer> freePlaces(){
		ArrayList<Integer> free = new ArrayList<Integer>();
		for (int i = 0; i < board.size(); i++) {
			if(board.get(i)==null){
				free.add(i);
			}
		}
		return free;
	}

	public ArrayList<Piece> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<Piece> board) {
		this.board = board;
	}
	
	public ArrayList<Piece> getRow(int rowNr){
		ArrayList<Piece> row = new ArrayList<Piece>();
		for (int i = rowNr*4; i < (rowNr*4)+4; i++) {
			row.add(this.board.get(i));
		}
		return row;
	}
	
	public ArrayList<Piece> getColumn(int colNr){
		ArrayList<Piece> col = new ArrayList<Piece>();
		for (int i = colNr; i < colNr*4; i+=4) {
			col.add(board.get(colNr));
		}
		return col;
	}
	
	public ArrayList<Piece> getDiagonalRight(){
		ArrayList<Piece> dia = new ArrayList<>();
		for (int i = 0; i < 16; i+=5) {
			dia.add(board.get(i));
		}
		return dia;
	}
	public ArrayList<Piece> getDiagonalLeft(){
		ArrayList<Piece> dia = new ArrayList<>(); 
		for (int i = 3; i <= 12; i+=3) {
			dia.add(board.get(i));
		}
		return dia;
	}
	
	public ArrayList<Piece> remainingPieces(){
		return pieces;
	}
	
	
	
}
