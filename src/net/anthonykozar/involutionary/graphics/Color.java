/*	Color.java

	A simple encapsulation of an RGBA color with a similar interface to
	the AWT Color class.  Intended to make Involutionary's rendering code
	more portable to environments where AWT isn't available.
 */

package net.anthonykozar.involutionary.graphics;

public class Color {
	
	/* Pre-defined colors */
	static final public Color	white = new Color(255, 255, 255);
	static final public Color	ltgray = new Color(192, 192, 192);
	static final public Color	gray = new Color(128, 128, 128);
	static final public Color	dkgray = new Color(64, 64, 64);
	static final public Color	black = new Color(0, 0, 0);

	static final public Color	red = new Color(255, 0, 0);
	static final public Color	orange = new Color(255, 128, 0);
	static final public Color	yellow = new Color(255, 255, 0);
	static final public Color	green = new Color(0, 128, 0);
	static final public Color	blue = new Color(0, 0, 255);
	static final public Color	purple = new Color(128, 0, 128);
	static final public Color	magenta = new Color(255, 0, 255);
	static final public Color	cyan = new Color(0, 255, 255);
	
	static final public Color	screengreen = new Color(0, 255, 0);

	
	// 8-bit color components in the range 0-255
	protected int	_red;
	protected int	_green;
	protected int	_blue;
	protected int	_alpha;
	
	// 32-bit combination of the above
	protected int	_rgba;
	
	/* Static methods */
	
	static public Color getHSBColor(float h, float s, float b)
	{
		// TODO Implement this method
		return new Color(0, 0, 0);
	}
	static public float[] RGBtoHSB(int r, int g, int b, float[] hsbvalues)
	{
		// TODO Implement this method
		return hsbvalues;		
	}
	
	/* Constructors */
	
	/** Create an opaque RGB color. Component values should be in the range 0.0-1.0. */
	public Color(float r, float g, float b)
	{
		_red   = ClipToRange((int)(r * 255));
		_green = ClipToRange((int)(g * 255));
		_blue  = ClipToRange((int)(b * 255));
		_alpha = 255;
		UpdateRGBA();
	}
	
	/** Create an RGB color with the given transparency. Component values should be in the range 0.0-1.0. */
	public Color(float r, float g, float b, float alpha)
	{
		_red   = ClipToRange((int)(r * 255));
		_green = ClipToRange((int)(g * 255));
		_blue  = ClipToRange((int)(b * 255));
		_alpha = ClipToRange((int)(alpha * 255));
		UpdateRGBA();
	}
	
	/** Create an opaque RGB color. Component values should be in the range 0-255. */
	public Color(int r, int g, int b)
	{
		_red   = ClipToRange(r);
		_green = ClipToRange(g);
		_blue  = ClipToRange(b);
		_alpha = 255;
		UpdateRGBA();
	}
	
	/** Create an RGB color with the given transparency. Component values should be in the range 0-255. */
	public Color(int r, int g, int b, int alpha)
	{
		_red   = ClipToRange(r);
		_green = ClipToRange(g);
		_blue  = ClipToRange(b);
		_alpha = ClipToRange(alpha);
		UpdateRGBA();		
	}

	/* Non-static public methods */
	
	public int getAlpha()
	{
		return _alpha;
	}

	public int getRed()
	{
		return _red;
	}

	public int getGreen()
	{
		return _green;
	}

	public int getBlue()
	{
		return _blue;
	}
	
	public int getRGBAInt()
	{
		return _rgba;
	}
	
	/* Non-public methods */
	
	protected int ClipToRange(int val) {
		if (val < 0)  val = 0;
		else if (val > 255) val = 255;
		return val;
	}
	
	protected void UpdateRGBA() {
		_rgba = (_alpha << 24) | (_red << 16) | (_green << 8) | _blue;
	}
	
}
