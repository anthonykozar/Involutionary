/*	AWTDrawingContext.java
	
	Wrapper class for an AWT Graphics object that supplies our 
	DrawingContext interface.
 */

package net.anthonykozar.involutionary.desktop.render;

import java.awt.Graphics;

import net.anthonykozar.involutionary.graphics.Color;
import net.anthonykozar.involutionary.graphics.DPoint;
import net.anthonykozar.involutionary.graphics.DrawingContext;

public class AWTDrawingContext implements DrawingContext
{
	protected Graphics	gfxcontext;
	protected boolean	skipduplicates = true;
	protected boolean	drawnextpoint = true;		// if true, forces the next point to be drawn
	private int			lastx, lasty;
	
	public AWTDrawingContext(Graphics g)
	{
		gfxcontext = g;
	}
	
	/** Configure whether to draw consecutive duplicate points with the same integer coordinates.
	 	Passing enable=true will prevent the drawing of duplicate points.  The default value
	 	is true.
	 */
	public void setPointDrawingOptimization(boolean enable)
	{
		skipduplicates = enable;
	}
	
	@Override
	public void drawPoint(double x, double y)
	{
		int ix = (int)Math.round(x);
		int iy = (int)Math.round(y);
		
		if (ix == lastx && iy == lasty)	{
			if (!skipduplicates || drawnextpoint) {
				// we have to use drawLine() to draw a single point
				gfxcontext.drawLine(ix, iy, ix, iy);
			}
		}
		else {
			// we have to use drawLine() to draw a single point
			gfxcontext.drawLine(ix, iy, ix, iy);
			lastx = ix;
			lasty = iy;
		}
		drawnextpoint = false;
	}

	@Override
	public void drawPoint(DPoint point)
	{
		drawPoint(point.x, point.y);
	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2)
	{
		gfxcontext.drawLine((int)Math.round(x1), (int)Math.round(y1), 
							(int)Math.round(x2), (int)Math.round(y2));
	}

	@Override
	public void drawLine(DPoint point1, DPoint point2)
	{
		drawLine(point1.x, point1.y, point2.x, point2.y);
	}

	@Override
	public void setColor(Color color)
	{
		java.awt.Color awtcolor = new java.awt.Color(color.getRGBAInt(), true);
		gfxcontext.setColor(awtcolor);
		drawnextpoint = true;
	}

	@Override
	public Color getColor()
	{
		java.awt.Color awtcolor = gfxcontext.getColor();
		Color c = new Color(awtcolor.getRed(), awtcolor.getGreen(), awtcolor.getBlue(), awtcolor.getAlpha());
		return c;
	}

}
