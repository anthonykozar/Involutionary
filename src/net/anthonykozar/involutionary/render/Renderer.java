package net.anthonykozar.involutionary.render;

import net.anthonykozar.involutionary.graphics.DrawingContext;

public interface Renderer {
	
	/** A Renderer has exactly one target to which it renders. */
	public RenderingTarget getTarget();
	
	/** Render the entire curve. */
	public void render(DrawingContext context);
	
}
