package com.company;

import java.io.*;

public class WaitList implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;
    private Client client;
    private int quantity;

    public WaitList(Client client, Product product, int quantity){
        this.client   = client;
        this.product  = product;
        this.quantity = quantity;
    }

    public Product getProduct(){
        return product;
    }

    public Client getClient(){
        return client;
    }

    public int getQuantity(){
        return quantity;
    }

    public void updateQuantity(int newQuantity){
        this.quantity = newQuantity;
    }
}