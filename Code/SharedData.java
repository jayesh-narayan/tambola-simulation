import java.util.ArrayList;

public class SharedData {

	public static Object lock = new Object();
	public static ArrayList<Integer> dispList = new ArrayList<Integer>();
	public static int rounds = 10;

	public static boolean gameOver = false;
	public static boolean isNumDispByMod = false;
	public static boolean[] pWin;
	public static boolean[] pChance;
	
	public SharedData(int n)
	{
		pWin = new boolean[n];
		pChance = new boolean[n];
	}
	
}
