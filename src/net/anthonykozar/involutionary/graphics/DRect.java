/*	DRect.java

	A rectangle with double-precision planar coordinates.
	
	Usage: Cartesian coordinates are expected instead of screen coordinates,
	therefore top should be greater than bottom.
*/

package net.anthonykozar.involutionary.graphics;

public class DRect {

	public double	top, left, bottom, right;

	public DRect()
	{
		// no init necessary
	}

	public DRect(double top_, double left_, double bottom_, double right_)
	{
		top    = top_;
		left   = top_;
		bottom = bottom_;
		right  = right_;
	}
	
	public double getWidth()
	{
		return right - left;
	}

	public double getHeight()
	{
		return top - bottom;
	}

}
