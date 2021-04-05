package StarterPackage;



public class CalcThread extends Thread{
	private int val;
	private boolean wait = true;
	private boolean prime;

	
	CalcThread (int val){
		this.val = val;
	}
	
	public void clearWait() {
		wait = false;
	}
	

	public void run() {
		while (wait) {
			Thread.yield();
		}

		for(int x = this.val; x > 0; x--) {
			prime = true;
			for (int y = 2 ; y < val; y++) {
				
			
			if(x % y == 0) {
				prime = false;
				break;
				}
			}
			if (prime)
			System.out.println(x + "is prime");
		}
		
	}
}

