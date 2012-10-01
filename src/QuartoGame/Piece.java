package QuartoGame;



public class Piece {
	
	public enum Color{WHITE, BLACK};
	public enum Height{TALL, SHORT};
	public enum Shape{SQUARE, ROUND}
	public enum Consistensy{HOLLOW, SOLID};
	
	private Color color;
	private Height height;
	private Shape shape;
	private Consistensy consistensy;
	private String id;
	
	public Piece(Color c, Height h, Shape s, Consistensy con){
		this.color = c;
		this.height = h;
		this.shape = s;
		this.consistensy = con;
		if(this.color.equals(Color.WHITE)){
			this.id="w";
		}
		else {
			this.id="b";
		}
		if(this.height.equals(Height.TALL)){
			this.id=this.id.toUpperCase();
		}
		if(this.shape.equals(Shape.ROUND)){
			this.id = "("+this.id+")";
		}
		if(this.consistensy.equals(Consistensy.HOLLOW)){
			this.id = this.id+"*";
		}
		if(id.length()==1){
			this.id = " "+this.id+"  ";
		}
		else if(id.length()==2){
			this.id = " "+this.id+" ";
		}
		else if(id.length()==3){
			this.id = this.id+" ";
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Height getHeight() {
		return height;
	}

	public void setHeight(Height height) {
		this.height = height;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Consistensy getConsistensy() {
		return consistensy;
	}

	public void setConsistensy(Consistensy consistensy) {
		this.consistensy = consistensy;
	}
	
	public String toString(){
		
		return this.id;
	}
	
	
	
	
	
}
