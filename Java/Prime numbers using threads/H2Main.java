package StarterPackage;


public class H2Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalcThread CT1 = new CalcThread(Integer.parseInt(args[0]));
		
		CT1.start();
		CT1.clearWait();
		
	}


}



class CalcThread extends Thread{
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
			for (int y = 2 ; y < x; y++) {
			
			if(x % y == 0) {
				prime = false;
				break;
			}


		}
			if (prime)
				System.out.println(x + " is prime");
	}


		wait = true;
		
	}

}
