


public class Piece {
	
	public enum Color{WHITE, BLACK};
	public enum Height{TALL, SHORT};
	public enum Shape{SQUARE, ROUND}
	public enum Consistensy{HOLLOW, SOLID};
	
	private Color color;
	private Height height;
	private Shape shape;
	private Consistensy consistensy;
	
	public Piece(Color c, Height h, Shape s, Consistensy con){
		this.color = c;
		this.height = h;
		this.shape = s;
		this.consistensy = con;
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
		return "Color: "+this.color.toString()+", Heigth: "+this.height.toString()+", Shape: "+this.shape.toString()+", Consistensy: "+this.consistensy.toString();
	}
	
	
	
}
