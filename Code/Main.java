import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Welcome to the Game.");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the no. of players: ");
		int N = 5; //default
		try {
			N = Integer.parseInt(br.readLine());
		}
		catch (Exception e){
			System.err.println(e);
		}
		finally{
			br.close();
		}
		
		SharedData game  = new SharedData(N);
		Moderator modr  = Moderator.getInstance();
		
		Thread modrThread  = new Thread(modr);
	
		modrThread.start();
		for(int i = 0 ; i < N ; i++)
		{
			new Player(game, i);
		}
		
		//modrThread.join();
	}

}
