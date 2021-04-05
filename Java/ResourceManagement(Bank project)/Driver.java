package pack;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        int lowerBound = 1;
        int upperBound = 10;
        int resourceCount = 1;
        int threadCount = -1;

        if (args.length >= 2) {
            boolean inputValid = true;

            try {
                resourceCount = Integer.parseInt(args[0]);
                threadCount = Integer.parseInt(args[1]);


            } catch (NumberFormatException e) {
                displayOnCommandLine("Input Could Not Be Converted To Int./n");
                inputValid = false;
            }

            String recourceCountInvalidString = String.format(
                    "Invalid Resource Count", resourceCount,
                    lowerBound, upperBound);
            String threadCountInvalidString = String.format(
                    "Invalid Thread Count", threadCount,
                    lowerBound, upperBound);

            if (resourceCount < lowerBound || resourceCount > upperBound) {
                displayOnCommandLine(recourceCountInvalidString);
                inputValid = false;
            }

            if (threadCount < lowerBound || threadCount > upperBound) {
                displayOnCommandLine(threadCountInvalidString);
                inputValid = false;
            }

            if (!inputValid) {
                displayOnCommandLine("Invalid Parameters \n");
                System.exit(0);
            }

        } else if (args.length == 0) {

            Scanner commandlineScanner = new Scanner(System.in);

            String recourceCountRequestString = String.format("Enter Resource Count (%s-%s): ",
                    lowerBound, upperBound);
            String threadCountRequestString = String.format("Enter Thread Count (%s-%s): ", lowerBound,
                    upperBound);

            while (resourceCount < lowerBound || resourceCount > upperBound) {
                displayOnCommandLine(recourceCountRequestString);
                resourceCount = commandlineScanner.nextInt();
            }

            displayOnCommandLine("\n");

            while (threadCount < lowerBound || threadCount > upperBound) {
                displayOnCommandLine(threadCountRequestString);
                threadCount = commandlineScanner.nextInt();
            }
            commandlineScanner.close();

        } else {
            displayOnCommandLine("Command Line Error\n");
            System.exit(0);
        }


        runBankersAlgorithm(5, 5); //resourceCount Is Set to 5 Thread and 5 Resources HERE by defualt, change needed *****************************

    }

    private static void displayOnCommandLine(Object o) {
        System.out.print(o);
    }

    private static void runBankersAlgorithm(int resourceCount, int threadCount) {
        String displayInputsString = String.format
                ("Running...",
                        resourceCount, threadCount);
        displayOnCommandLine(displayInputsString);

        Bank bank = new Bank(resourceCount, threadCount);

        BankerThread[] customers = new BankerThread[threadCount];

        for (int p = 0; p < customers.length; p++) {
            customers[p] = new BankerThread(bank, p);
        }

        for (BankerThread p : customers) {
            p.start();
        }



        for (BankerThread z : customers) {
            try {
                z.join();
            }

            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        bank.getState();
    }


}
