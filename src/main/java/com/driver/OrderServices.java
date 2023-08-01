package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServices
{
    @Autowired
    private OrderRepository orderRepository=new OrderRepository();
    public void addOrder(Order order)
    {
         orderRepository.addOrder(order);
}

    public ResponseEntity<String>addPartner(String partnerId){
        return orderRepository.addPartner(partnerId);
    }

    public ResponseEntity<String>addOrderToPartner(String orderId,String partnerId){
        return orderRepository.addOrderToPartner(orderId,partnerId);
    }
    public Order getOrderById(String orderId){
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerByPartnerId(String partnerId)
    {
        return orderRepository.getPartnerByPartnerId(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId)
    {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId)
    {
       List<Order>list=orderRepository.getOrdersByPartnerId(partnerId);
       List<String>ans=new ArrayList<>();
       for(Order order:list){
           ans.add(order.getId());
       }
       return ans;
    }
    public List<String> getAllOrders(){
        List<Order>orders=orderRepository.getAllOrders();
        List<String>list=new ArrayList<>();
        for(Order order:orders){
            list.add(order.getId());
        }
        return list;
    }

    public Integer getCountOfUnassignedOrders()
    {
        return orderRepository.countOfUnAssignedOrders();
    }
    public  Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public String  getLastDeliveryTimeByPartnerId(String partnerId)
    {
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId)
    {
       orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId){
        orderRepository.deleteOrderById(orderId);
    }
}
