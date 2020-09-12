import java.util.HashMap;
import java.util.Random;

public class Player implements Runnable {

	private int id;
	private int matchedNums;
	private SharedData sharedData;
	HashMap<Integer, Integer> card = new HashMap<Integer, Integer>();
	Thread t;
	
	public Player (SharedData sd, int id)
	{
		this.id = id;
		sharedData = sd;
		
		Random rand = new Random();
		
		for(int i = 0 ; i < 10 ; i++)
		{
			int cur = rand.nextInt(51);
			if(card.containsKey(cur)){
				card.put(cur, card.get(cur) + 1);
			}
			else{
				card.put(cur, 1);
			}
		}
		
		t = new Thread(this);
		t.start();
	}
	
	@Override @SuppressWarnings("static-access") 
	public void run() {
		
		synchronized(sharedData.lock)
		{
			while(!sharedData.gameOver)
			{
				while(!sharedData.isNumDispByMod || sharedData.pChance[id]) {
					try {
						sharedData.lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(!sharedData.gameOver)
				{
					
					System.out.println("Player " + (id+1) + "'s Card => " + card);
					int temp = sharedData.dispList.get(sharedData.dispList.size() - 1);
					if(card.containsKey(temp))
					{
						this.matchedNums++;
						
						if(card.get(temp) > 1){
							card.put(temp, card.get(temp) - 1);
						}
						else{
							card.remove(temp);
						}
					}
					
					sharedData.pChance[id] = true;
					
					if(this.matchedNums == 3)
					{
						sharedData.pWin[id] = true;
					}
					
					sharedData.lock.notifyAll();
				}	
			}
		}
	}
}