package net.anthonykozar.involutionary.desktop.view;

import java.awt.*;
import javax.swing.JComponent;

import net.anthonykozar.involutionary.desktop.render.AWTPointRenderer;
import net.anthonykozar.involutionary.render.Renderer;
import net.anthonykozar.involutionary.render.RenderingTarget;

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
			// set the Graphics context for the renderer and render
			if (renderer instanceof AWTPointRenderer) {
				((AWTPointRenderer)renderer).setGraphics(g);
			}
			renderer.render();
		}
	}

}
