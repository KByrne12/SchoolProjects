
package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public class ClientQuery extends WareState{
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private WareContext context;
    private static ClientQuery instance;

    private ClientList clientList;

    private static final int EXIT = 0;
    private static final int ALL = 1;
    private static final int OUTSTANDING = 2;
    private static final int NO_TRANSACTION = 3;
    private static final int HELP = 8;



    private ClientQuery() {
        super();
        clientList = ClientList.instance();
    }

    public static ClientQuery instance() {
        if (instance == null) {
            instance = new ClientQuery();
        }
        return instance;

    }

    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }
    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command: 8 for help"));
                if (value >= EXIT ) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }
    public void CQDisplay () {
        System.out.println("1: Show All Clients");
        System.out.println("2: Show Clients With Outstanding Balance");
        System.out.println("3: Show Clients With No Transactions");
        System.out.println("0: Logout");
    }

    public void DisplayAllClients()
    {
        Iterator<Client> C_Iterator = clientList.getClients();
        System.out.println("Printing Client List");
        while (C_Iterator.hasNext()) {
            Client item = C_Iterator.next();
            System.out.println("ID: " + item.getId() + " Name: " + item.getName() + " Address: " + item.getAddress());
        }
    }

    public void DisplayOutstandingInvoiceClients()
    {
        Iterator<Client> C_Iterator = clientList.getClients();
        System.out.println("Printing Client List");
        while (C_Iterator.hasNext()) {
            Client item = C_Iterator.next();
            if (item.NumOutstandingInvoices() > 0)
            System.out.println("ID: " + item.getId() + " Name: " + item.getName() + " Address: " + item.getAddress());
        }
    }

    public void DisplayNoTransactionClients()
    {
        Iterator<Client> C_Iterator = clientList.getClients();
        System.out.println("Printing Client List");
        while (C_Iterator.hasNext()) {
            Client item = C_Iterator.next();
            if (item.NumInvoices() == 0)
            System.out.println("ID: " + item.getId() + " Name: " + item.getName() + " Address: " + item.getAddress());
        }
    }

    public void logout()
    {
        (WareContext.instance()).changeState(2);
    }

    public void CQProcess()
    {
        int command = 1;
        CQDisplay();
        while ((command = getCommand()) != EXIT){
            switch (command) {
                case ALL: DisplayAllClients();
                    break;
                case OUTSTANDING: DisplayOutstandingInvoiceClients();
                    break;
                case NO_TRANSACTION: DisplayNoTransactionClients();
                    break;
                case HELP: CQDisplay();
                    break;
                default:
                    System.out.println("Invalid input, try again");
        }
    }
    logout();
}
    public void run() {
        CQProcess();
    }
}
