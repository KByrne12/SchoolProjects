package com.company;

import java.util.*;
import java.io.*;
public class cart {

    LinkedList<Product> shoppingCart = new LinkedList<>();
    List<Integer> quantities = new LinkedList<>();

    public void adder(Product pr, int quantity)
    {
        //pr.setQuantity(quantity);
        quantities.add(quantity);
        shoppingCart.add(pr);
    }

    public void printer()
    {
        for(int i = 0; i < shoppingCart.size(); i++)
        {
            Product pr = shoppingCart.get(i);
            int quant = quantities.get(i);
            System.out.println("Name: " + pr.getName());
            System.out.println("ID: " + pr.getId());
            System.out.println("Quantity: " + quant);
            System.out.println("Price each: " + pr.getPrice());
        }
        System.out.println("Total cost:" + total());
    }

    public int total()
    {
        int total = 0;
        for(int i = 0; i < shoppingCart.size(); i++)
        {
            Product tempP = shoppingCart.get(i);
            int many = quantities.get(i);
            total += (many * tempP.getPrice());
        }
        return total;
    }

    public void emptyCart()
    {
        shoppingCart.clear();
        System.out.println("The client's cart has been emptied.");
    }

    public void RemoveProduct(Product item)
    {
        int position = IDcheck(item.getId());
        Integer quantity = quantities.get(position);
        quantities.remove(quantity);
        shoppingCart.remove(item);

        System.out.println("Product: " + item.getName() + ", has been removed.");
    }

    public int IDcheck (int ID) {
        for (int i=0; i < shoppingCart.size(); i++) {
            Product temp = shoppingCart.get(i);
            if (ID == temp.getId()) {
                System.out.println("ID found");
                return i;
            }
        }
        System.out.println("ERROR: ID is not in database");
        return -1;
    }

    public Product get_listed_obj (int position) {
        return shoppingCart.get(position);
    }
    public void set_listed_obj (int position, Product update) {
        shoppingCart.set(position, update);
    }

}