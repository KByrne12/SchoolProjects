
public class Manager {

	public static final int MAX_RESOURCES = 5;
	private int availableResources = MAX_RESOURCES;
	
	
	
	public int decreaseCount(int count) {	
		try {
			Thread.sleep(1000);
		}catch (Exception e) {}
		System.out.println(availableResources);
		if(availableResources < count)
		{
			return -1;
		}
		else
		{
			availableResources -= count;
			System.out.println(availableResources);
			return 0;
		}
	}
	
	public void increaseCount(int count)
	{
		availableResources += count;
	}
	
	
public static void main(String[] args) {
		
		// TODO Auto-generated method stub

		
		Manager mana1 = new Manager();
		
		threadTest t1 = new threadTest(1,4,mana1);
		threadTest t2 = new threadTest(2,3,mana1);

		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		}catch (Exception e) {}
		
	}
	
}

class threadTest extends Thread
{
	int id;
	int count;
	Manager mana;
	threadTest(int id,int count,Manager mana)
	{
		this.id = id;
		this.count = count;
		this.mana = mana;
	}
	public void run()
	{
		System.out.println("Thread " + this.id + "running");
		mana.decreaseCount(count);
		try {
			Thread.sleep(1000);
		}catch (Exception e) {}
		mana.increaseCount(count);
		
	}
}


