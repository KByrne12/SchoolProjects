package com.company;

import java.util.*;
import java.lang.*;
import java.io.*;

public class SupplierList implements Serializable {
    private LinkedList<Supplier> suppliers = new LinkedList<Supplier>();
    private static SupplierList supplierList;

    private SupplierList() {}

    public static SupplierList instance() {
        if (supplierList == null) {
            return (supplierList = new SupplierList());
        }
        else {
            return supplierList;
        }
    }

    public boolean insertSupplier(Supplier supplier) {
        suppliers.add(supplier);
        return true;
    }

    public Iterator<Supplier> getSuppliers() {
        return suppliers.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(supplierList);
        }
        catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (supplierList != null) {
                return;
            }
            else {
                input.defaultReadObject();
                if (supplierList == null) {
                    supplierList = (SupplierList) input.readObject();
                }
                else {
                    input.readObject();
                }
            }
        }
        catch(IOException ioe) {
            System.out.println("in SupplierList readObject \n" + ioe);
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return suppliers.toString();
    }

    //adding a function for going through the linked list finding a specfic ID
    //takes a int, returns a int (both are to be supplier IDs)
    public int IDcheck (int ID) {
        for (int i=0; i < suppliers.size(); i++) {
            Supplier temp = suppliers.get(i);
            if (ID == temp.get_ID()) {
                System.out.println("ID found");
                return i;
            }
        }
        System.out.println("ERROR: ID is not in database");
        return -1;
    }
    public Supplier get_listed_obj (int position) {
        return suppliers.get(position);
    }
    public void set_listed_obj (int position, Supplier update) {
        suppliers.set(position, update);
    }
}