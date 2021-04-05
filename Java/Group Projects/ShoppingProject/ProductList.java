package com.company;

import java.util.*;
import java.lang.*;
import java.io.*;

public class ProductList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Product> products = new LinkedList<Product>();
    private static ProductList productList;

    private ProductList() {}

    public static ProductList instance() {
        if (productList == null) {
            return (productList = new ProductList());
        }
        else {
            return productList;
        }
    }

    public boolean insertProduct(Product product) {
        products.add(product);
        return true;
    }

    public Iterator<Product> getProducts() {
        return products.iterator();
    }

    public Product search(int productID) {
        for (Iterator iterator = products.iterator(); iterator.hasNext(); ) {
            Product product = (Product) iterator.next();
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(productList);
        }
        catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (productList != null) {
                return;
            }
            else {
                input.defaultReadObject();
                if (productList == null) {
                    productList = (ProductList) input.readObject();
                }
                else {
                    input.readObject();
                }
            }
        }
        catch(IOException ioe) {
            System.out.println("in ProductList readObject \n" + ioe);
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return products.toString();
    }
    public int IDcheck (int ID) {
        for (int i=0; i < products.size(); i++) {
            Product temp = products.get(i);
            if (ID == temp.getId()) {
                System.out.println("ID found");
                return i;
            }
        }
        System.out.println("ERROR: ID is not in database");
        return -1;
    }
    public Product get_listed_obj (int position) {
        return products.get(position);
    }
    public void set_listed_obj (int position, Product update) {
        products.set(position, update);
    }
}