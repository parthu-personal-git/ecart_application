package com.shopping.ecartbackend.controller;

import com.shopping.ecartbackend.common.ApiResponse;
import com.shopping.ecartbackend.domain.OrderModel;
import com.shopping.ecartbackend.model.Order;
import com.shopping.ecartbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> addOrder(@RequestBody OrderModel orderModel){
        Order order = orderService.addOrderObject(orderModel);
        return new ResponseEntity<>(new ApiResponse(true,"order has been created"), HttpStatus.CREATED);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") int id){
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orderList = orderService.getAllOrders();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateOrder(@PathVariable("id") int id, @RequestBody OrderModel orderModel){
        Order order = orderService.updateOrderById(id,orderModel);
        return new ResponseEntity<>(new ApiResponse(true,"order has been updated"), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAnOrder(@PathVariable("id") int id){
        orderService.deleteAnOrderById(id);
        return new ResponseEntity<>(new ApiResponse(true,"An order has been deleted"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<ApiResponse> deleteAllOrder(){
        orderService.deleteAllOrders();
        return new ResponseEntity<>(new ApiResponse(true,"All order have been deleted"), HttpStatus.OK);
    }


}
