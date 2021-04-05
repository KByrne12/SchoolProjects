
package com.company;
import java.util.*;

public class InvoiceList {

    private List<Invoice> invoices = new LinkedList<Invoice>();




    public Iterator<Invoice> getInvoices() {
        return invoices.iterator();
    }

    public int NumInvoices()
    {
        return invoices.size();
    }

    public int NumOutstandingInvoices()
    {
        int total = 0;
        for (int i = 0; i < invoices.size(); i++)
        {
            Invoice temp = invoices.get(i);
            if (temp.paid == false)
            {
                total++;
            }
        }
        return total;
    }


    public void AddInvoice(cart userCart, int total)
    {
        Invoice newInvoice = new Invoice(userCart,total);
        invoices.add(newInvoice);
        userCart.emptyCart();
    }

    public int IDcheck (int ID) {
        for (int i=0; i < invoices.size(); i++) {
            Invoice temp = invoices.get(i);
            if (ID == temp.getId()) {
                System.out.println("ID found");
                return i;
            }
        }
        System.out.println("ERROR: ID is not in database");
        return -1;
    }


    public Invoice get_listed_obj (int position) {
        return invoices.get(position);
    }
    public void set_listed_obj (int position, Invoice update) {
        invoices.set(position, update);
    }


}