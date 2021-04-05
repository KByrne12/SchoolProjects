package com.company;

import java.util.*;
import java.text.*;
import java.io.*;

public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private ClientList clientList;
    private ProductList productList;
    private SupplierList supplierList;
    private static final int EXIT = 0;
    private static final int ADD_PRODUCT = 1;
    private static final int ADD_CLIENT = 2;
    private static final int ADD_SUPPLIER = 3;
    private static final int ADD_TO_CART = 4;
    private static final int EDIT_A_FIELD = 5;
    private static final int ADD_ORDER = 6;
    private static final int SHIPMENT = 7;
    private static final int REMOVE_FROM_CART = 8;
    private static final int PAYMENT = 9;
    private static final int CHANGE_QUANTITY = 10;
    private static final int MENU = 11;


    private UserInterface() {
        supplierList = SupplierList.instance();
        productList = ProductList.instance();
        clientList = ClientList.instance();
    }

    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        } else {
            return userInterface;
        }
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
                int value = Integer.parseInt(getToken("Enter a command. [Use command " + MENU + " for a list of commands]"));
                if (value >= EXIT && value <= MENU) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number");
            }
        } while (true);
    }

    public void menu() {
        System.out.println("Enter the number corresponding to the desired command:");
        System.out.println("Exit: " + EXIT);
        System.out.println("Add a Product: " + ADD_PRODUCT);
        System.out.println("Add a Client: " + ADD_CLIENT);
        System.out.println("Add a Supplier: " + ADD_SUPPLIER);
        System.out.println("Add to Cart: " + ADD_TO_CART);
        System.out.println("Edit a Field: " + EDIT_A_FIELD);
        System.out.println("Add a Order: " + ADD_ORDER);
        System.out.println("Recieve a Shipment: " + SHIPMENT);
        System.out.println("Remove from Cart: " + REMOVE_FROM_CART);
        System.out.println("Make a Payment " + PAYMENT);
        System.out.println("Change Quantity in Cart: " + CHANGE_QUANTITY);
        System.out.println("Menu: " + MENU);
    }

    public void addProduct() {
        boolean successful;
        String productName = getToken("Enter the product name: ");
        int productQuantity = Integer.parseInt(getToken("Enter the product quantity: "));
        int productPrice = Integer.parseInt(getToken("Enter the product price: "));
        Product newProduct = new Product(productName, productQuantity, productPrice);
        successful = productList.insertProduct(newProduct);

        if (successful) {
            System.out.println("Product with details (" + newProduct.toString() + ") added successfully");
        } else {
            System.out.println("Issue adding product!");
        }
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

    public void addSupplier() {
        boolean successful;
        String supplierName = getToken("Enter the supplier name:");

        Supplier newSupplier = new Supplier(supplierName);

        successful = supplierList.insertSupplier(newSupplier);
        if (successful) {
            System.out.println("Supplier with name " + newSupplier.getName() + " and ID " + newSupplier.get_ID() + " added successfully");
        } else {
            System.out.println("Issue adding supplier!");
        }
    }

    public void addToCart() {
        int clientID = Integer.parseInt(getToken("Enter ID of client to add to their cart"));
        int position = clientList.IDcheck(clientID);
        Client item = clientList.get_listed_obj(position);
        int productToAddID = Integer.parseInt(getToken("Please enter the product ID you wish to add: "));
        Product productToAdd = productList.search(productToAddID);
        int quant = Integer.parseInt(getToken("Enter the quantity to be added to the cart: "));
        item.AddToCart(productToAdd, quant);
    }

    public void editAField() {
        System.out.println("Would you like to edit");
        System.out.println("1. Product");
        System.out.println("2. Client");
        System.out.println("3. Supplier");
        int choice = Integer.parseInt(getToken("Enter your choice: "));

        switch (choice) {
            case 1: int productID = Integer.parseInt(getToken("Enter ID of product to edit"));
                int position = productList.IDcheck(productID);
                Product product = productList.get_listed_obj(position);
                product = edit_product(product);
                productList.set_listed_obj(position, product);
                break;
            case 2: int clientID = Integer.parseInt(getToken("Enter ID of client to edit"));
                position = clientList.IDcheck(clientID);
                Client client = clientList.get_listed_obj(position);
                client = edit_client(client);
                clientList.set_listed_obj(position, client);
                break;
            case 3: int supplierID = Integer.parseInt(getToken("Enter ID of supplier to edit"));
                position = supplierList.IDcheck(supplierID);
                Supplier supplier = supplierList.get_listed_obj(position);
                supplier = edit_supplier(supplier);
                supplierList.set_listed_obj(position, supplier);
                break;
            default:
                System.out.println("Incorrect choice");
        }
    }
    public Supplier edit_supplier (Supplier item) {
        String userEdit = getToken("Edit NAME or LIST?");
        switch (userEdit) {
            case "NAME": String name = getToken("To what name?");
                item.setName(name);
                break;
            case "LIST": int id = Integer.parseInt(getToken("ID of product to add, delete or edit"));
                int IDcheck = productList.IDcheck(id);
                if (IDcheck == -1) {
                    System.out.println("No product with that ID");
                } else {
                    String choice = getToken("INSERT, REMOVE, or CHANGE?");
                    switch (choice) {
                        case "INSERT": String nameP = getToken("enter the product name");
                            String price_info = getToken("enter the product price information");
                            double price = Double.parseDouble(getToken("enter the product price"));
                            item.create_item(price, id, nameP, price_info);
                            break;
                        case "REMOVE":
                            item.delete_item(id);
                            break;
                        case "CHANGE":
                            item.grab_item(id);
                            break;
                        default: System.out.println("no action chosen, none taken");
                    }
                }
                break;
            default: System.out.println("no action chosen, none taken");
        }
        return item;
    }
    public Client edit_client (Client item) {
        String userEdit = getToken("Edit NAME or ADDRESS");
        switch (userEdit) {
            case "NAME": String name = getToken("To what name?");
                item.setName(name);
                break;
            case "ADDRESS": String address = getToken("To what address?");
                item.setAddress(address);
                break;
            default: System.out.println("no action chosen, none taken");
        }
        return item;
    }
    public Product edit_product (Product item) {
        String userEdit = getToken("Edit NAME, PRICE, OR QUANTITY");
        switch (userEdit) {
            case "NAME": String name = getToken("To what name?");
                item.setName(name);
                break;
            case "PRICE": int price = Integer.parseInt(getToken("To what price"));
                item.setPrice(price);
                break;
            case "QUANTITY": int quantity = Integer.parseInt(getToken("To what quantity"));
                item.setQuantity(quantity);
                break;
            default: System.out.println("no action chosen, none taken");
        }
        return item;
    }
    public void addOrder () {
        int clientID = Integer.parseInt(getToken("Enter ID of client to add order from"));
        int position = clientList.IDcheck(clientID);
        Client item = clientList.get_listed_obj(position);
        clientList.GenerateOrder(item);
    }
    public void shipment () {
        int supplierID = Integer.parseInt(getToken("Enter the ID of the supplier to recieve shipment from"));
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
    public void removeFromCart () {
        int clientID = Integer.parseInt(getToken("Enter ID of client to delete from their cart"));
        int position = clientList.IDcheck(clientID);
        Client item = clientList.get_listed_obj(position);
        int productID = Integer.parseInt(getToken("Please enter the product ID you wish to delete: "));
        item.RemoveCartProduct(productID);
    }
    public void makePayment () {
        int clientID = Integer.parseInt(getToken("Enter ID of client paying invoice"));
        int position = clientList.IDcheck(clientID);
        Client item = clientList.get_listed_obj(position);
        int invoiceID = Integer.parseInt(getToken("Enter ID of invoice to pay"));
        item.PayInvoice(invoiceID);
    }
    public void changeQuantity() {
        int clientID = Integer.parseInt(getToken("Enter ID of client to edit quantity in their cart: "));
        int position = clientList.IDcheck(clientID);
        Client item = clientList.get_listed_obj(position);
        int productID = Integer.parseInt(getToken("Enter the product ID you wish to change the quantity of: "));
        int qty = Integer.parseInt(getToken("Enter the new quantity: "));
        item.ChangeQuantity(productID, qty);
    }
    public void process() {
        int command;
        menu();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case ADD_CLIENT:
                    addClient();
                    break;
                case ADD_SUPPLIER:
                    addSupplier();
                    break;
                case ADD_TO_CART:
                    addToCart();
                    break;
                case EDIT_A_FIELD:
                    editAField();
                    break;
                case ADD_ORDER:
                    addOrder();
                    break;
                case SHIPMENT:
                    shipment();
                    break;
                case REMOVE_FROM_CART:
                    removeFromCart();
                    break;
                case PAYMENT:
                    makePayment();
                    break;
                case CHANGE_QUANTITY:
                    changeQuantity();
                    break;
                case MENU:
                    menu();
                    break;
                default:
                    System.out.println("Not a valid command");
                    command = EXIT;
            }
        }
    }

    public static void main(String[] args) {
        UserInterface.instance().process();
    }
}