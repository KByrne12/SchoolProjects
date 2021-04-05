package com.company;

import java.util.*;

public class WaitlistList {

    private static WaitlistList waitlistList;
    public LinkedList<WaitList> waitListFull = new LinkedList<WaitList>();

    public static WaitlistList instance() {
        if (waitlistList == null) {
            return (waitlistList = new WaitlistList());
        }
        else {
            return waitlistList;
        }
    }

    public Iterator <WaitList> getWaitList()
    {
        return waitListFull.iterator();
    }

    public void PrintWaitList()
    {
        for (int i = 0; i < waitListFull.size(); i++)
        {
            System.out.println(waitListFull.get(i));
        }
    }

    public void PrintWaitList(Client client)
    {
        for (int i = 0; i < waitListFull.size(); i++)
        {
            WaitList temp = waitListFull.get(i);
            if (client == temp.getClient())
                System.out.println(waitListFull.get(i));
        }
    }


    public void AddWaitList(Client client, Product product, int quantity)
    {
        WaitList temp = new WaitList(client,product,quantity);
        waitListFull.add(temp);
    }

    public void DisplayWaitList()
    {
        System.out.println("In progress");
       // for (int i = 0; i < waitListFull.size(); )
    }

    public WaitList get_listed_obj (int position) {
        return waitListFull.get(position);
    }
    public void set_listed_obj (int position, WaitList update) {
        waitListFull.set(position, update);
    }


}
