package net.anthonykozar.involutionary.desktop.render;

import net.anthonykozar.involutionary.curves.PlaneCurve;
import net.anthonykozar.involutionary.desktop.view.AbstractSwingView;
import net.anthonykozar.involutionary.graphics.*;
import net.anthonykozar.involutionary.render.Renderer;
import net.anthonykozar.involutionary.render.RenderingTarget;

public class AWTPointRenderer implements Renderer
{
	protected double	centerx;
	protected double	centery;
	protected double	drawingradius;				// maximum distance from the center that we can draw
	protected int		pointdensity;				// how many points to draw per revolution
	
	protected PlaneCurve		curve;
	protected AbstractSwingView	rendertarget;
	
	public AWTPointRenderer(AbstractSwingView target)
	{
		rendertarget = target;
		target.addRenderer(this);
	}
	
	public AWTPointRenderer(AbstractSwingView target, PlaneCurve curve_)
	{
		rendertarget = target;
		target.addRenderer(this);
		curve = curve_;
	}
	
	/**	Sets the drawing center (origin), scale, and point density.
		Call this after the AWTPointRenderer is added to a container or resized!
	 */
	public void autoConfigure()
	{
		setOrigin();
		setScale();
		pointdensity = 100000;
	}
	
	public void setOrigin()
	{
		// use the center of the component as the origin point for drawing
		centerx = rendertarget.getWidth()  * 0.5;
		centery = rendertarget.getHeight() * 0.5;
	}
	
	public void setScale()
	{
		// find a drawing radius that will fit
		drawingradius = (Math.min(rendertarget.getWidth(), rendertarget.getHeight()) * 0.25) - 30.0;		
	}
	
	/* Methods implementing the Renderer interface */
	
	@Override
	public RenderingTarget getTarget()
	{
		return rendertarget;
	}
	
	@Override
	public void render(DrawingContext gfxcontext) {
		final double start = 0.0;						// render from the curve's beginning
		final double end = 1.0;							// to its end
		final double parmincr = 1.0/pointdensity;		// increment at which to draw points
		double x,y;
		DPoint point;
		
		if (gfxcontext == null) {
			System.err.println("Error in AWTPointRenderer.render(): gfxcontext == null");
			return;
		}
		
		gfxcontext.setColor(Color.blue);
		
		if (curve != null) {
			// ask the curve to calculate coordinates for points at regular intervals of its parameter
			for (double curveparm = start; curveparm <= end; curveparm += parmincr) {
				point = curve.calculatePoint(curveparm);
				// translate coordinates relative to our drawing origin & scale
				x = centerx + drawingradius*point.x;
				y = centery - drawingradius*point.y;		// invert Y coords for screen drawing!
				gfxcontext.drawPoint(x, y);
			}
		}
	}
	
}
