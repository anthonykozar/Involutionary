package net.anthonykozar.involutionary.curves;

import net.anthonykozar.involutionary.graphics.DPoint;

public interface PlaneCurve {

	DPoint calculatePoint(double curveparameter);

	void calculatePoints(/* double start, double end, buffer? */);

}