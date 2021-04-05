package com.company;

//Contains a class, second class, and then test/main for testing functionality below that

//Code for a supplier currently in developement, SupplierList stores all suppliers
//it contains their ID, name, products, product prices, and set and get functions
//it keep products and product prices in a linked list

import java.util.*;
import java.io.*;
public class Supplier implements Serializable {
    private int ID;
    private String name;
    //for ID generation
    private SupplierIdServer supplierIdServer;
    //linked list with 1 double and 3 strings (price, ID, name, Price _info) in that order per item
    LinkedList<Supply> products = new LinkedList<>();
    //constructor leaves linked list empty
    public Supplier(String name) {
        supplierIdServer = SupplierIdServer.instance();
        this.ID = supplierIdServer.getId();
        this.name = name;
        System.out.println("Id for supplier is: " + get_ID());
    }
    //get for ID, supplier IDs lack set as they should not be changed
    public int get_ID () {
        return ID;
    }
    // get & set for name, it can be both accessed and changed
    public void setName (String name) {
        this.name = name;
    }
    public String getName () {
        return name;
    }
    //takes a Id gets a specific product from the product list
    //sends to edit product
    public void grab_item (int id) {
        for (int i=0; i<products.size(); i++) {
            Supply item = products.get(i);
            if (item.get_ID() == id) {
                edit_product(item);
                i = products.size();
            }
        }
    }
    //lets main create a supply instance without needing supply class, by giving data for the 4 variables
    //needs a double and 3 string
    public void create_item(double price, int ID, String name, String price_info) {
        Supply item = new Supply(price, ID, name, price_info);
        add_product(item);
    }
    public void delete_item (int id) {
        for (int i=0; i<products.size(); i++) {
            Supply item = products.get(i);
            if (item.get_ID() == id) {
                remove_product(item);
                i = products.size();
            }
        }
    }
    //the use_item takes a supply instance, uses a scanner for user input
    //it can add or delete. Later editting was added.
    //unused, legacy method
    public void use_item (Supply item) {
        Scanner myObj = new Scanner(System.in); //create scanner object
        System.out.println("Add[1], Delete[2], or Update [3] the instance in the list?");
        int choice = myObj.nextInt();
        switch (choice) {
            case 1: add_product(item);
                break;
            case 2: remove_product(item);
                break;
            case 3: edit_product(item);
                break;
            default: System.out.println("no action chosen, no action taken");
        }
    }
    //currently public may become private and only useable via other methods, currently public for testing
    //takes a supply instance adds it to the linked list (may be used in multiple methods)
    public void add_product(Supply item) {
        products.add(item);
    }
    //same situation as add_product may become private later but is public for testing purposes and may have further use in future methods
    public void remove_product (Supply item) {
        products.remove(item);
    }
    public void edit_product (Supply item) {
        Scanner myObj = new Scanner(System.in); //create scanner object
        int position = products.indexOf(item);
        System.out.println("Edit: [1] Price, [2] Price information, and [3] Product Name");
        int choice = myObj.nextInt();
        switch (choice) {
            case 1: System.out.println(item.get_price_info() + "\nTo what price?");
                int price = myObj.nextInt();
                item.set_price(price);
                products.set(position, item);
                break;
            case 2: System.out.println(item.get_price() + "\nWhat price_information?");
                String price_info = myObj.nextLine();
                item.set_price_info(price_info);
                products.set(position, item);
                break;
            case 3: System.out.println(item.get_name() + "\nTo what name?");
                String name = myObj.nextLine();
                item.set_name(name);
                products.set(position, item);
                break;
            default: System.out.println("No action chosen, no action take");
        }
    }
    //this prints the whole linked list safely
    //no inputs or outputs
    public void print_list () {
        for (int i = 0; i < products.size(); i++) {
            Supply item = products.get(i);
            item.print();
        }
    }
    //returns a list iterator for the shipment fucntion in user interface
    public Iterator<Supply> shipment () {
        return products.descendingIterator();
    }
    public void print_fields () {
        System.out.println("Id: " + get_ID() + "\nName: " + getName());
    }
}