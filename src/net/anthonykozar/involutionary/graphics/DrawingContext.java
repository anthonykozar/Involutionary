package net.anthonykozar.involutionary.graphics;

public interface DrawingContext {

	public void drawPoint(double x, double y);
	
	public void drawPoint(DPoint point);
	
	public void drawLine(double x1, double y1, double x2, double y2);

	public void drawLine(DPoint point1, DPoint point2);
	
	public void setColor(Color color);
	
	public Color getColor();
	
}
