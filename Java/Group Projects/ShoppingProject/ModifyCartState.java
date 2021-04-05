package com.company;

import java.io.*;
import java.util.*;

public class ModifyCartState extends WareState{
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private WareContext context;
    private static ModifyCartState instance;
    private ProductList productList;
    private ClientList clientList;

    private static final int ADD = 1;
    private static final int REMOVE = 2;
    private static final int CHANGE = 3;
    private static final int SHOW = 4;
    private static final int EXIT = 0;
    private static final int HELP = 8;

    private ModifyCartState() {
        super();
        productList = ProductList.instance();
        clientList = ClientList.instance();
    }

    public static ModifyCartState instance() {
        if (instance == null) {
            instance = new ModifyCartState();
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


    public void CartDisplay()
    {
        System.out.println("1: Add item to cart");
        System.out.println("2: Remove item from cart");
        System.out.println("3: Change Quantity");
        System.out.println("4: Show Cart");
        System.out.println("0: Logout");

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

    public void DisplayClientCart(Client client)
    {
        client.DisplayCart();
    }

    public void logout()
    {
        (WareContext.instance()).changeState(1);
    }

    public void process() {
        int position = clientList.IDcheck((WareContext.instance()).getUID());
        Client loggedInClient = clientList.get_listed_obj(position);
        int command;
        CartDisplay();
        while ((command = getCommand()) != EXIT){
            switch (command) {
                case ADD: AddItemsToCart(loggedInClient);
                    break;
                case REMOVE: RemoveCartItem(loggedInClient);
                    break;
                case CHANGE: ChangeCartQuantity(loggedInClient);
                    break;
                case SHOW: DisplayClientCart(loggedInClient);
                    break;
                case HELP: CartDisplay();
                    break;
                default:
                    System.out.println("Invalid input, try again");
            }
        }
        logout();
    }
    public void run() {
        process();
    }

}
