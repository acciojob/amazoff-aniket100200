package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class OrderRepository
{
    Map<String,Order> orderStuff=new HashMap<>();
    Map<String, List<Order>>orderPartnerMap=new HashMap<>();

    Map<String,DeliveryPartner>deliveryPartnerMap=new HashMap<>();
    public void addOrder(Order order)
    {
        if(order==null)return;
        orderStuff.put(order.getId(),order);

    }

    public String addPartner(String partnerId)
    {
        deliveryPartnerMap.put(partnerId,new DeliveryPartner(partnerId));
        return "New partner added successfully";
    }

    public ResponseEntity<String> addOrderToPartner(String orderId,String partnerId)
    {
        List<Order>orderList=orderPartnerMap.getOrDefault(partnerId,new ArrayList<>());
        orderList.add(orderStuff.get(orderId));

        //I'll have to increase the no. of orders to the partner.
        DeliveryPartner partner=deliveryPartnerMap.getOrDefault(partnerId,new DeliveryPartner(partnerId));
        partner.setNumberOfOrders(partner.getNumberOfOrders()+1);

        orderPartnerMap.put(partnerId,orderList);
        return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
    }

    public Order getOrderById(String orderId)
    {
        return orderStuff.get(orderId);
    }

    public DeliveryPartner getPartnerByPartnerId(String partnerId)
    {
      return deliveryPartnerMap.get(partnerId);

    }
    public Integer getOrderCountByPartnerId(String partnerId)
    {
        return orderPartnerMap.get(partnerId).size();
    }

    public List<Order> getOrdersByPartnerId(String partnerId)
    {
       return orderPartnerMap.get(partnerId);
    }

    public List<Order> getAllOrders()
    {
        List<Order>list=new ArrayList<>();
        for(String key:orderStuff.keySet()){
            list.add(orderStuff.get(key));
        }
        return list;
    }

    public Integer countOfUnAssignedOrders()
    {
        int count=0;
        int totalOrder=orderStuff.size();
        for(String key:deliveryPartnerMap.keySet()){
            count+=deliveryPartnerMap.get(key).getNumberOfOrders();
        }

        return totalOrder - count;
    }

    public  Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId)
    {
        int HH=Integer.parseInt(time.substring(0,2));
        int MM=Integer.parseInt((time.substring(3)));

        int limitTime=HH*60+MM;
        List<Order> orderList=orderPartnerMap.get(partnerId);
        int count=0;
        for(Order order:orderList)
        {
           int deleveryTime=order.getDeliveryTime();
           if(deleveryTime>limitTime)count++;

        }
       Integer integer=count;
        return integer;
    }
    public String  getLastDeliveryTimeByPartnerId(String partnerId)
    {
        List<Order>list=orderPartnerMap.get(partnerId);
        int last=Integer.MIN_VALUE;
        for(Order order:list)
        {
            int time=order.getDeliveryTime();
            if(time>last)last=time;
        }
       int time=last;
        int HH=time/60;
        int MM=time%60;
        if(HH<10 && MM<10)return "0"+HH+":0"+MM;
        else if(HH<10)return "0"+HH+":"+MM;
        else if(MM<10)return ""+HH+":0"+MM;
        return ""+HH+":"+MM;
    }


    public void deletePartnerById(String partnerId)
    {
        if(orderPartnerMap.containsKey(partnerId))
        {
            deliveryPartnerMap.remove(partnerId);
            orderPartnerMap.remove(partnerId);
        }
    }

    public void deleteOrderById(String orderId)
    {

       if(orderStuff.containsKey(orderId))
       {
          Order order= orderStuff.remove(orderId);
         for(String key:orderPartnerMap.keySet())
         {

          if(orderPartnerMap.get(key).contains(order))
          {
              orderPartnerMap.get(key).remove(order);
              String partnerId=key;
              DeliveryPartner partner=deliveryPartnerMap.getOrDefault(partnerId,new DeliveryPartner(partnerId));
              partner.setNumberOfOrders(partner.getNumberOfOrders()-1);
          }
         }
       }

       //my order has beeen remove successfully and remove maked it unassigned...
        //let's go...

    }
}
