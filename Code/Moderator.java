import java.util.*;

public class Moderator implements Runnable {
	
	private static SharedData sharedData;
	
	private Moderator(SharedData sd){
		sharedData = sd;
	}
	
	private static Moderator m = new Moderator(sharedData);
	
	public static Moderator getInstance()
	{
		return m;
	}
	
	@Override @SuppressWarnings("static-access")
	public void run() {
		
		synchronized(Moderator.sharedData.lock)
		{
			while( !checkWin() && sharedData.rounds > 0 )
			{
				reset();
				addRandom();
				
				System.out.println("Numbers Announced: " + sharedData.dispList);
				sharedData.rounds -= 1;
				
				sharedData.isNumDispByMod = true;
				
				try{Thread.sleep(60000);} catch(Exception e) {e.printStackTrace();};
				
				sharedData.lock.notifyAll();
				
				while( !checkChance() )
				{
					try {
						sharedData.lock.wait(); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				//Winning Logic
				if(checkWin())
				{
					for(int i = 0 ; i < sharedData.pWin.length ; i++)
					{
						if(sharedData.pWin[i] == true)
						{
							System.out.print("Player " + (i+1) + " won! ");
						}
					}
					sharedData.gameOver = true;
				}
				else if(sharedData.rounds > 0)
				{
					System.out.println("Nobody won at the end of this round. \n");
				}
				else
				{
					System.out.println("Nobody won in the end. \n");
				}
				
				sharedData.lock.notifyAll();	
			}				
		}
	}
	
	public boolean checkWin(){
		boolean x = SharedData.pWin[0];
		for(int i = 1 ; i < SharedData.pWin.length ; i++)
		{
			x = x || SharedData.pWin[i];
		}
		return x;
	}
	
	public boolean checkChance(){
		boolean x = SharedData.pChance[0];
		for(int i = 1 ; i < SharedData.pChance.length ; i++)
		{
			x = x && SharedData.pChance[i];
		}
		return x;
	}
	
	public void reset(){
		SharedData.isNumDispByMod = false;
		for(int i = 0 ; i < SharedData.pChance.length ; i++)
		{
			SharedData.pChance[i] = false;
		}
	}
	
	public void addRandom(){
		Random rand = new Random();
		int r = rand.nextInt(51);
		SharedData.dispList.add(r);
	}
}
