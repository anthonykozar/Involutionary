package net.anthonykozar.involutionary.desktop.view;

import java.awt.*;
import net.anthonykozar.involutionary.desktop.render.AWTDrawingContext;
import net.anthonykozar.involutionary.render.Renderer;

public class BasicCurveView extends AbstractSwingView
{
	protected Renderer	renderer;
	
	public BasicCurveView()
	{
	
	}
	
	/**	This class supports only a single renderer */
	public void addRenderer(Renderer renderer_)
	{
		renderer = renderer_;
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		// clear the window with background color
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if (renderer != null) {
			// wrap Graphics context for the renderer and render
			renderer.render(new AWTDrawingContext(g));
		}
	}

}
