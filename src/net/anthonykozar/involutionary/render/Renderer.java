package net.anthonykozar.involutionary.render;

public interface Renderer {
	
	/** A Renderer has exactly one target to which it renders. */
	public RenderingTarget getTarget();
	
	/** Render the entire curve. */
	public void render(/* parameters ?? */);
	
}
