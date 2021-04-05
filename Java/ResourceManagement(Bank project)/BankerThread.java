package pack;

public class BankerThread extends Thread {

    Bank bank;

    int counter = 0;
    int customerNum;

    public BankerThread(Bank b1, int customerNum) {
        this.bank = b1;
        this.customerNum = customerNum;
    }

    @Override
    public void run() {




                bank.requestResources(customerNum, counter);
                counter++;
                try {
                	Thread.sleep(250);
                }catch (Exception e) {}
                bank.requestResources(customerNum, counter);
                counter++;
                try {
                	Thread.sleep(250);
                }catch (Exception e) {}
                bank.requestResources(customerNum, counter);

                bank.releaseRecources(customerNum);

               





        }



}
