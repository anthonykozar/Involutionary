package net.anthonykozar.involutionary.util;

public class MathUtils
{
	/*	Euclidean algorithm for finding the greatest common divisor.
		Code by Matt <http://stackoverflow.com/users/447191/matt>
		Copied from <http://stackoverflow.com/questions/4009198/java-get-greatest-common-divisor>
	 */
	static public int GCD(int a, int b)
	{
	   if (b==0) return a;
	   return GCD(b,a%b);
	}

}
