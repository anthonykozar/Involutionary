package net.anthonykozar.involutionary.curves;

import net.anthonykozar.involutionary.graphics.DPoint;
import net.anthonykozar.involutionary.util.MathUtils;
import net.anthonykozar.involutionary.util.RandomUtils;

public class Polytrochoid implements PlaneCurve
{
	final public int	MAXCIRCLES = 10;

	// default parameters for a polytrochoid created with Polytrochoid()
	final private int	 initcircles = 3;
	final private int[]	 initradii = {1, 27, 9};
	final private double initpenpos = 1.0;

	protected double	centerx;
	protected double	centery;
	protected double	drawingradius;				// maximum distance from the center that we can draw
	protected int		numcircles;					// number of circles in use (must be <= MAXCIRCLES)
	protected int[]		iradii;						// integer radii of each circle
	protected double[]	dradii;						// floating-point copy of iradii[]
	protected double[]	pixradii;					// actual radii of each circle in pixels
	protected double[]	angleratios;				// the ratio btw radii of consecutive circles (outer/inner)
	protected double[]	radiiratios;				// the ratio btw radii of consecutive circles (inner/outer)
	protected double[]	radiidiffs;					// the difference btw radii of this & the next circle
	protected double	penratio;					// the ratio penlength/innerradius
	protected double	penlength;					// distance from the center of inner circle to the "pen"
	protected double	revolutions;				// num of revolutions main angle needs to complete the figure
	protected int		pointdensity;				// how many points to draw per revolution

	public Polytrochoid()
	{		
		// allocate space for the circle parameters
		iradii = new int[MAXCIRCLES];
		dradii = new double[MAXCIRCLES];
		pixradii = new double[MAXCIRCLES];
		angleratios = new double[MAXCIRCLES];
		radiiratios = new double[MAXCIRCLES];
		radiidiffs = new double[MAXCIRCLES];
		
		// initialize all parameters
		for (int i = 0; i < MAXCIRCLES; i++)  iradii[i] = 1;
		setOrigin(0.0, 0.0);
		setScale(1.0);
		setDrawingParms(initcircles, initradii, initpenpos);
	}

	public void setOrigin(double x, double y)
	{
		centerx = x;
		centery = y;		
	}

	public void setScale(double scale)
	{
		// do we need this?
		drawingradius = scale;		
	}
	
	public void setDrawingParms(int circles, int[] radii, double penposition)
	{
		// validate arguments
		if (circles < 2) {
			System.err.println("Error in SetDrawingParms(): circles cannot be less than 2 (was " + circles + ")");
			return;
		}
		else if (circles > MAXCIRCLES) {
			System.err.println("Error in SetDrawingParms(): circles cannot be greater than MAXCIRCLES (was " + circles + ")");
			return;
		}
		else numcircles = circles;
		if (radii.length < numcircles) {
			System.err.println("Error in SetDrawingParms(): fewer than " + numcircles + " values in radii[] (has " + radii.length + ")");
			return;			
		}
		
		double maxradii = Double.MIN_VALUE;
		
		// calculate polytrochoid parameters
		for (int i = 0; i < numcircles; i++) {
			iradii[i] = radii[i];
			dradii[i] = (double)iradii[i];
			maxradii = Math.max(dradii[i], maxradii);
			if (i > 0) {
				angleratios[i] = dradii[i-1] / dradii[i];
				radiiratios[i] = dradii[i] / dradii[i-1];
			}
			else {
				angleratios[i] = 1.0;
				radiiratios[i] = 1.0;
			}
		}
		
		// try to scale the figure to fit the maxradii
		double	factor = drawingradius / maxradii;
		for (int i = 0; i < numcircles; i++) {
			pixradii[i] = dradii[i] * factor;
		}
		
		// calculate distances between circle centers
		for (int i = 0; i < numcircles-1; i++) {
			radiidiffs[i] = pixradii[i] - pixradii[i+1];
		}
		
		revolutions = (double)CalculateRevolutions(numcircles, iradii);
		setPenLength(penposition);
	}
	
	public void setPenLength(double penposition)
	{
		// penposition is a scalar for the innermost circle's radius
		penratio = penposition;
		penlength = radiidiffs[numcircles-1] = pixradii[numcircles-1] * penratio;
	}
	
	public void randomizeParms()
	{
		int circles;
		int[] radii;
		double penpos;
		boolean tryagain = false;
		
		circles = numcircles; //RandomUtils.uniformIntInRange(3, 4); // MAXCIRCLES);
		radii = new int[circles];
		do {
			for (int i = 0; i < circles; i++) {
				radii[i] = RandomUtils.uniformIntInRange(1, 40);
			}
			// Try to avoid certain uninteresting or slow combinations:
			if (radii[0] == radii[1] || (circles > 2 && radii[1] == radii[2]) || CalculateRevolutions(circles, radii) > 100)
				  tryagain = true;
			else  tryagain = false;
		}
		while(tryagain);
		
		penpos = RandomUtils.uniformIntInRange(3, 40) * 0.05;
		setDrawingParms(circles, radii, penpos);
	}

	@Override
	public DPoint calculatePoint(double curveparameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void calculatePoints(/* double start, double end, buffer? */)
	{
		final double arcstart = 0.0;							// angle of beginning of arc
		final double arcend = revolutions * 2.0 * Math.PI;		// angle of end of arc
		final double angleincr = 2.0 * Math.PI/pointdensity;	// increment at which to draw points
		double	x, y, innerangle, lastangle;
	
		// calculate coordinates parametrically based on the total angle of rotation
		for (double angle = arcstart; angle <= arcend; angle += angleincr) {
			// calculate coordinates of first inner circle center relative to the origin
			x = radiidiffs[0] * Math.cos(angle);
			y = radiidiffs[0] * Math.sin(angle);
			lastangle = angle;
			for (int i = 1; i < numcircles; i++) {
				// calculate the angle to the next circle center (or the pen) relative to a horizontal line
				innerangle = lastangle - (lastangle * angleratios[i]);
				// calculate coordinates of next circle center (or the pen) by finding the offsets from last center
				x += radiidiffs[i] * Math.cos(innerangle);
				y += radiidiffs[i] * Math.sin(innerangle);				
				lastangle = innerangle;
			}
			// calculate coordinates relative to our "drawing origin"
			x = centerx + x;
			y = centery - y;
		}
		
		// return (x,y);
	}

	/* Calculate the number of revolutions needed to produce a closed curve */
	private int CalculateRevolutions(int circles, int[] origradii)
	{
		int gcd, revs, result;
		int[] radii;
		
		// validate arguments
		if (circles < 2) {
			System.err.println("Error in CalculateRevolutions(): circles cannot be less than 2 (was " + circles + ")");
			return 0;
		}
		if (origradii.length < circles) {
			System.err.println("Error in CalculateRevolutions(): fewer than " + circles + 
					           " values in origradii[] (has " + origradii.length + ")");
			return 0;			
		}
		
		// reduce all radii by any common factors
		radii = (int[]) origradii.clone();
		gcd = MathUtils.GCD(radii[0], radii[1]);
		for (int i = 2; i < circles; i++) {
			gcd = MathUtils.GCD(gcd, radii[i]);
		}
		if (gcd > 1) {
			for (int i = 0; i < circles; i++) {
				radii[i] /= gcd;
			}
		}
		// System.out.println(Arrays.toString(origradii) + "  MathUtils.GCD(radii[]) = " + gcd + "  " + Arrays.toString(radii));
		
		// FIXME: this algorithm is currently only correct for 2-3 wheels (?)
		// the 2-wheel solution is just radii[1] reduced by any common factors 
		// that it shares with MathUtils.GCD(radii[0], radii[1]) which is already done
		// if circles == 2
		if (circles == 2) result = radii[1];
		else {
			// check for degenerate cases
			if (radii[0] == radii[1])  result = 1;			// just a single point
			else if (radii[1] == radii[2])  result = 1;		// a circle
			else {
				// start with what would be the num revs if only 2 wheels
				gcd = MathUtils.GCD(radii[0], radii[1]);
				result = radii[1] / gcd;
				// reduce radii[2] by any common factors that it shares with radii[1]
				gcd = MathUtils.GCD(radii[1], radii[2]);
				revs = radii[2] / gcd;
				// reduce radii[2] further by any common factors that it shares with radii[1]-radii[0]
				gcd = MathUtils.GCD(Math.abs(radii[1]-radii[0]), revs);
				revs = revs / gcd;
				// multiply 2-wheel result by the 3rd wheel factor
				result *= revs;
			}
		}
		
		return result;
	}
	
}
