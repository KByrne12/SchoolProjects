package com.company;

class Supply {
    private double price;
    private int ID;
    private String name, price_info;
    //constructor takes the data gives and slots it in
    public Supply(double price, int ID, String name, String price_info) {
        this.price = price;
        this.ID = ID;
        this.name = name;
        this.price_info = price_info;
    }
    public String get_price_info () {
        return price_info;
    }
    public int get_ID () {
        return ID;
    }
    public String get_name () {
        return name;
    }
    public double get_price () {
        return price;
    }
    public void set_price (double price) {
        this.price = price;
    }
    public void set_price_info (String price_info) {
        this.price_info = price_info;
    }
    public void set_name (String name) {
        this.name = name;
    }
    //method for printing name and ID
    public int print_name_id () {
        System.out.println(ID + " " + name);
        return ID;
    }
    //method for printing the data
    public void print () {
        System.out.println("Price: $" + price + " Price info: " + price_info + "\nID: " + ID + " Name: " + name);
    }
}