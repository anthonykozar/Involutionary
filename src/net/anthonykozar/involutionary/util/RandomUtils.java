package net.anthonykozar.involutionary.util;

public class RandomUtils
{
	static public int uniformIntInRange(int low, int high)
	{
		return (low + ( (int)((high-low+1) * Math.random() )));
	}

}
