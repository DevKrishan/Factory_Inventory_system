package utility;

import java.util.Random;

public class MiscUtilityFunctions {

	public static int randomID() {
		Random random = new Random();
		return (random.nextInt(100000));
	}

	public static void clrscr() {
		System.out.print("\n\n\n\n\n\n\n\n\n\n");
	}
}
