package com.company;

import java.util.*;
import java.text.*;
import java.io.*;

public class ClerkState extends WareState {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private WareContext context;
    private static ClerkState instance;
    private ClientList clientList;
    private ProductList productList;
    private SupplierList supplierList;
    //functions
    private static final int EXIT = 0;
    private static final int ADD_CLIENT = 1;
    private static final int SHOW_CLIENT_LIST = 2;
    private static final int SHOW_PRODUCT_LIST = 3;
    private static final int SHOW_INVOICE_LIST = 4;
    private static final int SHOW_PRODUCT_WAITLIST = 5;
    private static final int SHIPMENT = 6;
    private static final int CLIENT_MENU = 7;
    private static final int HELP = 8;
    private static final int CHECKOUT = 9;

    private ClerkState() {
        super();
        supplierList = SupplierList.instance();
        productList = ProductList.instance();
        clientList = ClientList.instance();
        //context = WareContext.instance();
    }

    public static ClerkState instance() {
        if (instance == null) {
            instance = new ClerkState();
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
                int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }
    public void addClient() {
        boolean successful;
        String clientName = getToken("Enter the client's name: ");
        String clientAddress = getToken("Enter the client's address: ");

        Client newClient = new Client(clientName, clientAddress); //DOESNT WANT TO CREATE THE CLIENT OR ADD TO LIST
        successful = clientList.insertMember(newClient);

        if (successful) {
            System.out.println("Client with details (" + newClient.toString() + ") added successfully");
        } else {
            System.out.println("Issue adding client!");
        }

    }
    public void clientLister () {
        (WareContext.instance()).changeState(5);
    }
    public void productLister () {
        Iterator<Product> P_Iterator = productList.getProducts();
        System.out.println("Printing Product List");
        while (P_Iterator.hasNext()) {
            Product item = P_Iterator.next();
            System.out.println("ID: " + item.getId() + " Price: " + item.getPrice() + " Quantity: " + item.getQuantity());
        }
    }
    public void shipment () {
        int supplierID = Integer.parseInt(getToken("Enter the ID of the supplier to receive shipment from"));
        int position1 = supplierList.IDcheck(supplierID);
        Supplier shipper = supplierList.get_listed_obj(position1);
        Iterator<Supply> shipment = shipper.shipment();
        while (shipment.hasNext()) {
            Supply item = shipment.next();
            int productID = item.print_name_id();
            int quantity = Integer.parseInt(getToken("Enter the quantity received of the product (0 if none)"));
            int position2 = productList.IDcheck(productID);
            Product prod = productList.get_listed_obj(position2);
            quantity = quantity + prod.getQuantity();
            prod.setQuantity(quantity);
        }
    }
    public void invoiceLister () {
        int clientID = Integer.parseInt(getToken("Enter the ID of a client, to get invoices"));
        int position = clientList.IDcheck(clientID);
        if (position == -1) {
            System.out.println("Invalid ID, potentially no clients exist, add a client before trying to get invoices");
        } else {
            Client item = clientList.get_listed_obj(position);
            item.DisplayInvoices();
        }
    }
    public void productWait () {
        System.out.println("Not implemented");

    }
    private void salesMenu(){
        int UID = Integer.parseInt(getToken("Enter the ID of the client to login as: "));
        (WareContext.instance()).setUID(UID);
        (WareContext.instance()).changeState(1); //go to clerk state
    }
    private void Checkout()
    {
        int ID = Integer.parseInt(getToken("What is the ID of the client wishing to checkout?"));
        int position = clientList.IDcheck(ID);
        Client client = clientList.get_listed_obj(position);
        client.newInvoice();
    }

    public void logout(){
        (WareContext.instance()).changeState(0); // exit
    }
    private void help () {
        System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(ADD_CLIENT+ " to add a client");
        System.out.println(SHOW_CLIENT_LIST + " to show client list");
        System.out.println(SHOW_PRODUCT_LIST+ " to product list");
        System.out.println(SHOW_INVOICE_LIST + " to  display invoice list");
        System.out.println(SHOW_PRODUCT_WAITLIST + " to  display product waitlist");
        System.out.println(SHIPMENT + " receive shipment");
        System.out.println(CLIENT_MENU + " to  switch to the Client menu");
        System.out.println(HELP + " for help");
        System.out.println(CHECKOUT + " To checkout a client");
    }
    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT){
            switch (command) {
                case ADD_CLIENT: addClient();
                    break;
                case SHOW_CLIENT_LIST: clientLister();
                    break;
                case SHOW_PRODUCT_LIST: productLister();
                    break;
                case SHOW_INVOICE_LIST: invoiceLister();
                    break;
                case SHOW_PRODUCT_WAITLIST: productWait();
                    break;
                case SHIPMENT: shipment();
                    break;
                case CHECKOUT: Checkout();
                    break;
                case CLIENT_MENU: salesMenu();
                    break;
                case HELP: help();
                    break;
            }
        }

        logout();
    }
    public void run() {
        process();
    }
}