package com.company;

import java.util.*;
import java.io.*;

public class ClientUI extends WareState {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private WareContext context;
    private static ClientUI instance;

    private ClientList clientList;
    private ProductList productList;
    private SupplierList supplierList;
    private WaitlistList waitlists;
    private static final int EXIT = 0;
    private static final int DETAILS = 1;
    private static final int PRODUCT_SHOW= 2;
    private static final int TRANSACTION = 3;
    private static final int WAIT = 4;
    private static final int CART = 5;
    private static final int HELP = 8;

    private ClientUI() {
        super();
        supplierList = SupplierList.instance();
        productList = ProductList.instance();
        clientList = ClientList.instance();
        waitlists = WaitlistList.instance();
    }

    public static ClientUI instance() {
        if (instance == null) {
            instance = new ClientUI();
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
    public void ClientUIDisplay ()
    {
        System.out.println("1: Show Client Details");
        System.out.println("2: Show Products");
        System.out.println("3: Show Transaction History");
        System.out.println("4: Show waitlist");
        System.out.println("5: Modify Cart");
        System.out.println("0: Logout");
    }


    public void ClientProcess () {
        int position = clientList.IDcheck((WareContext.instance()).getUID());
        if (position == -1 && (WareContext.instance()).getUID() == 1) {
            System.out.println("Adding dummy client as no client currently exist");
            Client dummy = new Client("Joe Schmoe", "123 Nowheres Ville");
            clientList.insertMember(dummy);
            position = 0;
        } else if (position == -1) {
            System.out.println("Invalid ID, logging you out, try 1");
            logout();
        }
        Client loggedInClient = clientList.get_listed_obj(position);
        int clientCommand = 1;
        ClientUIDisplay();
        while ((clientCommand = getCommand()) != 0)
        {
            switch(clientCommand) {
                case DETAILS:
                    ClientInfo(loggedInClient);
                    break;
                case PRODUCT_SHOW:
                    ShowProducts();
                    break;
                case TRANSACTION:
                    ShowTransactionHistory(loggedInClient);
                    break;
                case WAIT:
                    ShowWaitlist(loggedInClient);
                    break;
                case CART:
                    (WareContext.instance()).changeState(4);
                    break;
                case HELP:
                    ClientUIDisplay();
                    break;
                default:
                    System.out.println("Invalid command, try again");
            }

        }

        logout();

    }


    public void ClientInfo (Client client) {
        System.out.println("ID: " + client.getId());
        System.out.println("Name: " + client.getName());
        System.out.println("Address: " + client.getAddress());
    }


    public void ShowProducts() {
        Iterator<Product> P_Iterator = productList.getProducts();
        System.out.println("Printing Supplier List");
        while (P_Iterator.hasNext()) {
            Product item = P_Iterator.next();
            System.out.println("Name: " + item.getName());
            System.out.println("ID: " + item.getId());
            System.out.println("Price: " + item.getPrice());
            System.out.println("------------------------------");
        }
    }





    public void ShowTransactionHistory(Client client) {
        client.DisplayInvoices();
    }

    public void ShowWaitlist(Client client) {
        clientList.PrintWaitList(client);
    }

    public void AddItemsToCart(Client client) {
        int productToAddID = Integer.parseInt(getToken("Please enter the product ID you wish to add: "));
        Product productToAdd = productList.search(productToAddID);
        int quant = Integer.parseInt(getToken("Enter the quantity to be added to the cart: "));
        client.AddToCart(productToAdd, quant);

    }

    public void ChangeCartQuantity(Client client) {
        int productID = Integer.parseInt(getToken("Enter the product ID you wish to change the quantity of: "));
        int qty = Integer.parseInt(getToken("Enter the new quantity: "));
        client.ChangeQuantity(productID, qty);
    }

    public void RemoveCartItem(Client client) {
        int productID = Integer.parseInt(getToken("Please enter the product ID you wish to delete: "));
        client.RemoveCartProduct(productID);
    }

    public void logout() {

        (WareContext.instance()).changeState(0);
    }


    public void run() {
        ClientProcess();
    }

}