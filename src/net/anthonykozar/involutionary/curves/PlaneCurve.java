package net.anthonykozar.involutionary.curves;

import net.anthonykozar.involutionary.graphics.DPoint;

public interface PlaneCurve {

	public DPoint calculatePoint(double curveparameter);

	public void calculatePoints(/* double start, double end, buffer? */);

}
