package net.anthonykozar.involutionary.curves;

import net.anthonykozar.involutionary.util.DPoint;

public class Circle implements PlaneCurve
{

	protected double	radius;				// circle radius
	protected int		pointdensity;		// how many points to draw per revolution

	public Circle() {
		radius = 1.0;
	}

	public void setRadius(double newradius) {
		radius = newradius;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public DPoint calculatePoint(double curveparameter)
	{
		DPoint point = new DPoint();

		// curveparameter is the angle of the circle point normalized to [0.0,1.0]
		double angle = curveparameter * 2.0 * Math.PI;
		point.x = radius * Math.cos(angle);
		point.y = radius * Math.sin(angle);
		
		return point;
	}
	
	@Override
	public void calculatePoints(/* double start, double end, buffer? */)
	{
		final double arcstart = 0.0;							// angle of beginning of arc
		final double arcend = 2.0 * Math.PI;					// angle of end of arc
		final double angleincr = 2.0 * Math.PI/pointdensity;	// increment at which to draw points
		double	x, y;

		// calculate coordinates parametrically based on the total angle of rotation
		for (double angle = arcstart; angle <= arcend; angle += angleincr) {
			// calculate coordinates of circle points relative to the origin
			x = radius * Math.cos(angle);
			y = radius * Math.sin(angle);
		}
		
		// return (x,y);
	}

}
