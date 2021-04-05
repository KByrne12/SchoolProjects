package pack;

public class Bank {
    int numThreads;
    int numResources;
    int minAvailable = 1;
    int maxAvailable = 10;
    int minNeed = 0;


    int[] available;
    int[] currentAvailable;
    int[] customerResource;
    int[] safeCustomers;
    int[][] maximum;
    int[][] allocation;
    int[][] need;

    public Bank(int m, int n) {
        this.numResources = m;
        this.numThreads = n;

        available = new int[this.numResources];

        currentAvailable = new int[this.numResources];

        safeCustomers = new int[this.numThreads];

        for (int i = 0; i < this.numResources; i++) {
            int allocationOfResource = (int) Math.round(Math.random() *
                    (this.maxAvailable - minAvailable) + minAvailable);
            available[i] = allocationOfResource;
            currentAvailable[i] = allocationOfResource;
        }

        maximum = new int[numThreads][numResources];

        for (int i = 0; i < maximum.length; i++) {
            for (int j = 0; j < maximum[i].length; j++) {

                int maxs = (int) Math.round(Math.random() *
                        (this.available[j] - minNeed) + minNeed);
                maximum[i][j] = maxs%4;

            }
        }

        allocation = new int[numThreads][numResources];

        need = new int[numThreads][numResources];
        
        for(int i = 0; i < numThreads; i++)
        {
        	for(int j = 0; j < numResources; j++)
        	{
        		need[i][j] = maximum[i][j];
        	}
        }

        customerResource = new int[this.numResources];

        getState();

    }

    public void calculateCustomerNeed(int customerNum) {

        for (int i = 0; i < this.numResources; i++) {
            int customerNeed = maximum[customerNum][i] - allocation[customerNum][i];

            need[customerNum][i] = customerNeed = Math.abs(customerNeed);

        }

        displayNeed();

    }


    public synchronized boolean requestResources(int customerNumber, int counter) {


        boolean avail = true;
        boolean last = false;





        if (counter >= 2) {
            last = true;
        }




        //We are over two requests, on the last request for this customer
        if(last) {
            //Check if any of the available resources are less than needed resources
            for(int i = 0; i < this.numResources; i++) {
                if (this.available[i] < need[customerNumber][i]) {
                    avail = false;
                }
            }


            //While an available resource is less than needed, wait
            while (avail == false) {

                try {
                    displayOnCommandLine("\nCustomer " + customerNumber + " must wait\n");
                    wait();
                }

                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //Subtract needed resources from available
            System.out.println("Customer " + customerNumber + " requests resources.");
            for(int i = 0; i < numResources; i++) {
                this.available[i] -= need[customerNumber][i];
                this.allocation[customerNumber][i] += need[customerNumber][i];
                System.out.print(need[customerNumber][i] + " ");
                need[customerNumber][i]-=need[customerNumber][i];

            }
            System.out.println();
            

            
            System.out.println();
            
            return false;

        //End if(last)
        }
        else
        {

        //When not last request, check if available is less than (1/2) needed resource
        for(int i = 0; i < this.numResources; i++) {
            if (this.available[i] < ((need[customerNumber][i]) / 2 )) 
            {
                avail = false;
            }
        }


        //While an available resource is less than needed, wait
        while (avail == false) {

            try {
                displayOnCommandLine("\nCustomer " + customerNumber + " must wait\n");
                wait();
            }

            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Subtract needed resources from available
        System.out.println("Customer " + customerNumber + " requests resources.");
        for(int i = 0; i < numResources; i++) {
            this.available[i] -= need[customerNumber][i]/2;
            this.allocation[customerNumber][i] += need[customerNumber][i]/2;
            System.out.print(need[customerNumber][i]/2 + " ");
            need[customerNumber][i] -= need[customerNumber][i]/2;

        }
        System.out.println();

        



       // displayCustomerRequest(customerNumber);

        displayAllocation();

        return false;
        }
    }


    public synchronized void releaseRecources(int customerNumber) {


        for (int i = 0; i < numResources; i ++) {

            this.available[i] += allocation[customerNumber][i];

            allocation[customerNumber][i] = 0;
        }
        System.out.println();
        System.out.println("Customer " + customerNumber + " has released.");
        System.out.println();

        notifyAll();

    }

    public boolean safeProcess(int customerNumber) {
        boolean safe = false;

        for (int i = 0; i < this.numResources; i++) {
            if (currentAvailable[i] >= need[customerNumber][i])
                safe = true;

            else {
                safe = false;
                break;
            }

        }

        return safe;
    }


    public void runProcess(int customerNumber) {

        if (safeProcess(customerNumber)) {

            for (int j = 0; j < this.numResources; j++) {
                currentAvailable[j] = allocation[customerNumber][j] + currentAvailable[j];

            }
            displayOnCommandLine("Customer " + customerNumber + " request is granted\n");

            displayCurrentlyAvailable();

            displayOnCommandLine("\n");

        }

    }

    private static void displayOnCommandLine(Object o) {

        System.out.print(o);

    }


    public void displayCustomerRequest(int customerNumber) {

        displayOnCommandLine("\n\nCustomer " + customerNumber + " Request: \n");

        for (int b : this.customerResource) {
            displayOnCommandLine(b + " ");
        }

        displayOnCommandLine("\n");


    }

    public void displayAllocation() {
        displayOnCommandLine("\nBank - Allocation: \n");

        for (int j = 0; j < this.allocation.length; j++) {
            for (int k = 0; k < this.allocation[j].length; k++) {
                displayOnCommandLine(this.allocation[j][k] + " ");
            }

            displayOnCommandLine("\n");

        }
    }

    public void displayNeed() {
        displayOnCommandLine("\nBank - Need: \n");

        for (int j = 0; j < this.need.length; j++) {
            for (int k = 0; k < this.need[j].length; k++) {
                displayOnCommandLine(this.need[j][k] + " ");
            }

            displayOnCommandLine("\n");
        }

        displayOnCommandLine("\n");
    }

    public void displayMax() {

        displayOnCommandLine("\nBank - Max:\n");

        for (int i = 0; i < this.maximum.length; i++) {
            for (int j = 0; j < this.maximum[i].length; j++) {
                displayOnCommandLine(this.maximum[i][j] + " ");
            }
            displayOnCommandLine("\n");
        }
    }

    public void displayAvailable() {
        displayOnCommandLine("\nBank - Initial Resources Available:\n");

        for (int i = 0; i < this.available.length; i++) {
            displayOnCommandLine(this.available[i] + " ");
        }

        displayOnCommandLine("\n");
    }

    public void displayCurrentlyAvailable() {
        displayOnCommandLine("\nCurrent Available Work: \n");

        for (int c : this.currentAvailable) {
            displayOnCommandLine(c + " ");
        }

        displayOnCommandLine("\n");
    }

    public void getState() {

        displayAvailable();
        displayMax();
        displayAllocation();
        displayNeed();
    }

}
